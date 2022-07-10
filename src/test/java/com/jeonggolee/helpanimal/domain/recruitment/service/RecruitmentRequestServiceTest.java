package com.jeonggolee.helpanimal.domain.recruitment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.jeonggolee.helpanimal.domain.recruitment.dto.request.RecruitmentApplicationRequestDto;
import com.jeonggolee.helpanimal.domain.recruitment.dto.response.RecruitmentApplicationDetailDto;
import com.jeonggolee.helpanimal.domain.recruitment.dto.response.RecruitmentApplicationSearchDto;
import com.jeonggolee.helpanimal.domain.recruitment.entity.Recruitment;
import com.jeonggolee.helpanimal.domain.recruitment.entity.RecruitmentRequest;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentApplicationStatus;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentMethod;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentType;
import com.jeonggolee.helpanimal.domain.recruitment.repository.RecruitmentRepository;
import com.jeonggolee.helpanimal.domain.recruitment.repository.RecruitmentRequestRepository;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
public class RecruitmentRequestServiceTest {

    @Autowired
    private RecruitmentRequestService recruitmentRequestService;
    @Autowired
    private RecruitmentRequestRepository recruitmentRequestRepository;
    @Autowired
    private RecruitmentRepository recruitmentRepository;
    @Autowired
    private UserRepository userRepository;

    private RecruitmentRequest initRecruitmentRequest(Recruitment recruitment, User user,
        String comment) {
        return RecruitmentRequest.builder()
            .recruitment(recruitment)
            .user(user)
            .comment(comment)
            .status(RecruitmentApplicationStatus.REQUEST)
            .build();
    }

    private User initUser(String email, String nickname) {
        return User.builder()
            .email(email)
            .password("PASSWORD")
            .name("NAME")
            .nickname(nickname)
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
        User user = initUser("EMAIL", "nick");
        Recruitment recruitment = initRecruitment(user, "NAME");

        userRepository.save(user);
        recruitmentRepository.save(recruitment);

        RecruitmentApplicationRequestDto dto = RecruitmentApplicationRequestDto.builder()
            .email("EMAIL")
            .recruitmentId(recruitment.getId())
            .comment("TEST")
            .build();
        //when
        recruitmentRequestService.requestRecruitment(dto);
        //then
        List<RecruitmentRequest> results = recruitmentRequestRepository.findAll();
        assertThat(results.size() > 0).isTrue();
    }

    @Test
    void 신청내역_삭제() {
        //given
        User user = initUser("EMAIL", "nick");
        Recruitment recruitment = initRecruitment(user, "NAME");
        RecruitmentRequest recruitmentRequest = initRecruitmentRequest(recruitment, user, "TEST");

        userRepository.save(user);
        recruitmentRepository.save(recruitment);
        recruitmentRequestRepository.save(recruitmentRequest);

        //when
        recruitmentRequestService.deleteRecruitmentApplication(recruitmentRequest.getId());

        //then
        Optional<RecruitmentRequest> result = recruitmentRequestRepository
            .findByIdAndUserAndDeletedAtIsNull(recruitmentRequest.getId(), user);
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    void 해당공고_신청내역_조회() {
        //given
        User user = initUser("EMAIL", "nick");
        Recruitment recruitment = initRecruitment(user, "NAME");

        userRepository.save(user);
        recruitmentRepository.save(recruitment);

        for (int i = 0; i < 12; i++) {
            RecruitmentRequest recruitmentRequest = initRecruitmentRequest(recruitment, user,
                "TEST" + i);
            recruitmentRequestRepository.save(recruitmentRequest);

        }

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createDate").descending());
        //when
        RecruitmentApplicationSearchDto dto = recruitmentRequestService.findRecruitmentApplicationByRecruitment(
            pageable, recruitment.getId());
        //then
        assertThat(dto.getNumberOfElements()).isEqualTo(10);
        assertThat(dto.getTotalElements()).isEqualTo(12L);
    }

    @Test
    void 해당유저_신청내역_조회() {
        //given
        User user = initUser("EMAIL", "nick");
        List<Recruitment> recruitments = new ArrayList<>();
        userRepository.save(user);
        for (int i = 0; i < 12; i++) {
            Recruitment recruitment = initRecruitment(user, "NAME" + i);
            recruitmentRepository.save(recruitment);
            recruitments.add(recruitment);
        }
        for (Recruitment recruitment : recruitments) {
            RecruitmentRequest recruitmentRequest = initRecruitmentRequest(recruitment, user,
                "TEST");
            recruitmentRequestRepository.save(recruitmentRequest);
        }

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createDate").descending());
        //when
        RecruitmentApplicationSearchDto dto = recruitmentRequestService.findRecruitmentApplicationByUser(
            pageable, user.getUserId());
        //then
        assertThat(dto.getNumberOfElements()).isEqualTo(10);
        assertThat(dto.getTotalElements()).isEqualTo(12L);
    }

    @Test
    void 참여신청_상세조회() {
        //given
        User user = initUser("EMAIL", "nick");
        Recruitment recruitment = initRecruitment(user, "NAME");

        userRepository.save(user);
        recruitmentRepository.save(recruitment);

        RecruitmentRequest recruitmentRequest = initRecruitmentRequest(recruitment, user,
            "COMMENT");
        Long requestId = recruitmentRequestRepository.save(recruitmentRequest).getId();

        //when
        RecruitmentApplicationDetailDto result = recruitmentRequestService.findById(requestId);

        //then
        assertThat(result.getRecruitmentName()).isEqualTo(recruitment.getName());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        assertThat(result.getComment()).isEqualTo("COMMENT");
    }

    @Test
    void 참가신청_인원이_다_찼을땐_오류발생() {
        //given
        User user = initUser("EMAIL", "nick");
        User requestUser = initUser("EMAIL1", "nick1");
        User exceptionUser = initUser("EMAIL2", "nick2");
        Recruitment recruitment = initRecruitment(user, "NAME");

        userRepository.saveAll(List.of(user, requestUser, exceptionUser));
        recruitmentRepository.save(recruitment);

        RecruitmentRequest recruitmentRequest = initRecruitmentRequest(recruitment, requestUser,
            "TEST");
        recruitmentRequestRepository.save(recruitmentRequest);

        //when
        RecruitmentApplicationRequestDto dto = RecruitmentApplicationRequestDto.builder()
            .email("EMAIL2")
            .recruitmentId(recruitment.getId())
            .comment("TEST")
            .build();
        IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> recruitmentRequestService.requestRecruitment(dto));

        //then
        assertThat(exception.getMessage()).isEqualTo("인원이 다 찼습니다.");
    }
}
