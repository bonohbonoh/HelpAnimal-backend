package com.jeonggolee.helpanimal.domain.recruitment.service;

import com.jeonggolee.helpanimal.domain.recruitment.dto.request.RecruitmentApplicationRequestDto;
import com.jeonggolee.helpanimal.domain.recruitment.dto.response.RecruitmentApplicationDetailDto;
import com.jeonggolee.helpanimal.domain.recruitment.dto.response.RecruitmentApplicationSearchDto;
import com.jeonggolee.helpanimal.domain.recruitment.entity.Recruitment;
import com.jeonggolee.helpanimal.domain.recruitment.entity.RecruitmentApplication;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentApplicationStatus;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentMethod;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentType;
import com.jeonggolee.helpanimal.domain.recruitment.repository.RecruitmentApplicationRepository;
import com.jeonggolee.helpanimal.domain.recruitment.repository.RecruitmentRepository;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
public class RecruitmentApplicationServiceTest {
    @Autowired
    private RecruitmentApplicationService recruitmentApplicationService;
    @Autowired
    private RecruitmentApplicationRepository recruitmentApplicationRepository;
    @Autowired
    private RecruitmentRepository recruitmentRepository;
    @Autowired
    private UserRepository userRepository;

    private RecruitmentApplication initRecruitmentApplication(Recruitment recruitment, User user, String comment) {
        return RecruitmentApplication.builder()
                .recruitment(recruitment)
                .user(user)
                .comment(comment)
                .status(RecruitmentApplicationStatus.REQUEST)
                .build();
    }

    private User initUser(String email) {
        return User.builder()
                .email(email)
                .password("PASSWORD")
                .name("NAME")
                .nickname("NICKNAME")
                .profileImage("IMAGE")
                .role(Role.GUEST)
                .build();
    }

    private Recruitment initRecruitment(User user, String name) {
        return Recruitment.builder()
                .name(name)
                .recruitmentType(RecruitmentType.FREE)
                .content("CONTENT")
                .participant(1)
                .user(user)
                .imageUrl("TEST")
                .recruitmentMethod(RecruitmentMethod.FIRST_COME)
                .build();
    }

    @Test
    void 공고신청() {
        //given
        User user = initUser("EMAIL");
        Recruitment recruitment = initRecruitment(user, "NAME");

        userRepository.save(user);
        recruitmentRepository.save(recruitment);

        RecruitmentApplicationRequestDto dto = RecruitmentApplicationRequestDto.builder()
                .email("EMAIL")
                .recruitmentId(recruitment.getId())
                .comment("TEST")
                .build();
        //when
        recruitmentApplicationService.requestRecruitment(dto);
        //then
        List<RecruitmentApplication> results = recruitmentApplicationRepository.findAll();
        assertThat(results.size() > 0).isTrue();
    }

    @Test
    void 신청내역_삭제() {
        //given
        User user = initUser("EMAIL");
        Recruitment recruitment = initRecruitment(user, "NAME");
        RecruitmentApplication recruitmentApplication = initRecruitmentApplication(recruitment, user, "TEST");

        userRepository.save(user);
        recruitmentRepository.save(recruitment);
        recruitmentApplicationRepository.save(recruitmentApplication);

        //when
        recruitmentApplicationService.deleteRecruitmentApplication(recruitmentApplication.getId());

        //then
        Optional<RecruitmentApplication> result = recruitmentApplicationRepository
                .findByIdAndUserAndDeletedAtIsNull(recruitmentApplication.getId(), user);
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    void 해당공고_신청내역_조회() {
        //given
        User user = initUser("EMAIL");
        Recruitment recruitment = initRecruitment(user, "NAME");

        userRepository.save(user);
        recruitmentRepository.save(recruitment);

        for (int i = 0; i < 12; i++) {
            RecruitmentApplication recruitmentApplication = initRecruitmentApplication(recruitment, user, "TEST" + i);
            recruitmentApplicationRepository.save(recruitmentApplication);

        }

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createDate").descending());
        //when
        RecruitmentApplicationSearchDto dto = recruitmentApplicationService.findRecruitmentApplicationByRecruitment(pageable, recruitment.getId());
        //then
        assertThat(dto.getNumberOfElements()).isEqualTo(10);
        assertThat(dto.getTotalElements()).isEqualTo(12L);
    }

    @Test
    void 해당유저_신청내역_조회() {
        //given
        User user = initUser("EMAIL");
        List<Recruitment> recruitments = new ArrayList<>();
        userRepository.save(user);
        for (int i = 0; i < 12; i++) {
            Recruitment recruitment = initRecruitment(user, "NAME" + i);
            recruitmentRepository.save(recruitment);
            recruitments.add(recruitment);
        }
        for (Recruitment recruitment : recruitments) {
            RecruitmentApplication recruitmentApplication = initRecruitmentApplication(recruitment, user, "TEST");
            recruitmentApplicationRepository.save(recruitmentApplication);
        }

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createDate").descending());
        //when
        RecruitmentApplicationSearchDto dto = recruitmentApplicationService.findRecruitmentApplicationByUser(pageable, user.getUserId());
        //then
        assertThat(dto.getNumberOfElements()).isEqualTo(10);
        assertThat(dto.getTotalElements()).isEqualTo(12L);
    }

    @Test
    void 참여신청_상세조회() {
        //given
        User user = initUser("EMAIL");
        Recruitment recruitment = initRecruitment(user, "NAME");

        userRepository.save(user);
        recruitmentRepository.save(recruitment);

        RecruitmentApplication recruitmentApplication = initRecruitmentApplication(recruitment, user,"COMMENT");
        Long requestId = recruitmentApplicationRepository.save(recruitmentApplication).getId();

        //when
        RecruitmentApplicationDetailDto result = recruitmentApplicationService.findById(requestId);

        //then
        assertThat(result.getRecruitmentName()).isEqualTo(recruitment.getName());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getComment()).isEqualTo("COMMENT");

    }
}
