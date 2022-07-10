package com.jeonggolee.helpanimal.domain.recruitment.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.jeonggolee.helpanimal.domain.recruitment.dto.request.RecruitmentRegistDto;
import com.jeonggolee.helpanimal.domain.recruitment.dto.request.RecruitmentUpdateDto;
import com.jeonggolee.helpanimal.domain.recruitment.dto.response.RecruitmentDetailDto;
import com.jeonggolee.helpanimal.domain.recruitment.dto.response.RecruitmentListDto;
import com.jeonggolee.helpanimal.domain.recruitment.entity.Animal;
import com.jeonggolee.helpanimal.domain.recruitment.entity.Recruitment;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentMethod;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentType;
import com.jeonggolee.helpanimal.domain.recruitment.repository.AnimalRepository;
import com.jeonggolee.helpanimal.domain.recruitment.repository.RecruitmentRepository;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import com.jeonggolee.helpanimal.domain.user.util.Role;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
public class RecruitmentServiceTest {

    @Autowired
    private RecruitmentService recruitmentService;

    @Autowired
    private RecruitmentRepository recruitmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnimalRepository animalRepository;


    private final String NAME = "테스트공고";
    private final String EMAIL = "test1@naver.com";
    private final String CONTENT = "본문";
    private final String ANIMAL = "DOG";
    private final int PARTICIPANT = 10;
    private final RecruitmentMethod RECRUITMENT_METHOD = RecruitmentMethod.CHOICE;
    private final RecruitmentType RECRUITMENT_TYPE = RecruitmentType.FREE;

    private User initUser() {
        User user = User.builder()
            .name("test")
            .email(EMAIL)
            .password("pass")
            .nickname("닉네임")
            .profileImage("image")
            .role(Role.GUEST)
            .build();
        return userRepository.save(user);
    }

    private void initAnimal(User user) {
        animalRepository.save(Animal.builder()
            .name(ANIMAL)
            .user(user)
            .build()
        );
    }

    @Test
    @DisplayName("공고등록 테스트")
    public void 공고등록() {
        //given
        User user = initUser();
        initAnimal(user);

        RecruitmentRegistDto dto = RecruitmentRegistDto.builder()
            .name(NAME)
            .email(EMAIL)
            .content(CONTENT)
            .animal(ANIMAL)
            .participant(PARTICIPANT)
            .recruitmentMethod(RECRUITMENT_METHOD)
            .recruitmentType(RECRUITMENT_TYPE)
            .build();

        //when
        recruitmentService.save(dto);

        //then
        Optional<Recruitment> recruitment = recruitmentRepository.findByName(NAME);
        assertThat(recruitment.isPresent()).isTrue();
    }

    @Test
    @DisplayName("공고전체조회 첫페이지 조회 테스트")
    public void 공고전체조회_페이징() {
        //given
        User user = initUser();
        initAnimal(user);
        Pageable pageable = PageRequest.of(0, 10);

        for (int i = 0; i < 12; i++) {
            RecruitmentRegistDto dto = RecruitmentRegistDto.builder()
                .name(NAME + i)
                .email(EMAIL)
                .content(CONTENT)
                .animal(ANIMAL)
                .participant(PARTICIPANT)
                .recruitmentType(RECRUITMENT_TYPE)
                .recruitmentMethod(RECRUITMENT_METHOD)
                .build();

            recruitmentService.save(dto);

        }
        //when
        Page<RecruitmentListDto> list = recruitmentService.getRecriutmentListWithPaging(pageable);
        //then
        assertThat(list.getNumberOfElements()).isEqualTo(10L);
    }

    @Test
    @DisplayName("공고 상세조회 테스트")
    public void 공고_상세조회() {
        //given
        User user = initUser();
        initAnimal(user);

        RecruitmentRegistDto dto = RecruitmentRegistDto.builder()
            .name(NAME)
            .email(EMAIL)
            .content(CONTENT)
            .animal(ANIMAL)
            .participant(PARTICIPANT)
            .recruitmentMethod(RECRUITMENT_METHOD)
            .recruitmentType(RECRUITMENT_TYPE)
            .build();

        Long id = recruitmentService.save(dto);

        //when
        RecruitmentDetailDto detailDto = recruitmentService.getRecruitmentDetail(id);

        //then

        assertThat(detailDto.getName()).isEqualTo(NAME);
        assertThat(detailDto.getContent()).isEqualTo(CONTENT);
        assertThat(detailDto.getAnimalType()).isEqualTo(ANIMAL);
        assertThat(detailDto.getParticipant()).isEqualTo(PARTICIPANT);
        assertThat(detailDto.getRecruitmentMethod()).isEqualTo(RECRUITMENT_METHOD);
        assertThat(detailDto.getRecruitmentType()).isEqualTo(RECRUITMENT_TYPE);
    }

    @Test
    @DisplayName("공고 수정 테스트")
    @WithMockUser(username = "test1@naver.com", roles = {"USER"})
    public void 공고수정() throws Exception {
        //given
        User user = initUser();
        initAnimal(user);

        RecruitmentRegistDto dto = RecruitmentRegistDto.builder()
            .name(NAME)
            .email(EMAIL)
            .content(CONTENT)
            .animal(ANIMAL)
            .participant(PARTICIPANT)
            .recruitmentMethod(RECRUITMENT_METHOD)
            .recruitmentType(RECRUITMENT_TYPE)
            .build();

        Long id = recruitmentService.save(dto);

        //when
        RecruitmentUpdateDto updateDto = RecruitmentUpdateDto.builder()
            .name("UPDATE_NAME")
            .content("UPDATE_CONTENT")
            .participant(1)
            .imageUrl("UPDATE_IMAGE")
            .build();

        recruitmentService.updateRecruitment(id, updateDto);
        RecruitmentDetailDto detailDto = recruitmentService.getRecruitmentDetail(id);

        //then
        assertThat(detailDto.getName()).isEqualTo(updateDto.getName());
        assertThat(detailDto.getContent()).isEqualTo(updateDto.getContent());
        assertThat(detailDto.getParticipant()).isEqualTo(updateDto.getParticipant());
        assertThat(detailDto.getImageUrl()).isEqualTo(updateDto.getImageUrl());
    }

    @Test
    @DisplayName("공고 삭제 테스트")
    @WithMockUser(username = "test1@naver.com", roles = {"USER"})
    public void 공고삭제() throws Exception {
        //given
        User user = initUser();
        initAnimal(user);

        RecruitmentRegistDto dto = RecruitmentRegistDto.builder()
            .name(NAME)
            .email(EMAIL)
            .content(CONTENT)
            .animal(ANIMAL)
            .participant(PARTICIPANT)
            .recruitmentMethod(RECRUITMENT_METHOD)
            .recruitmentType(RECRUITMENT_TYPE)
            .build();

        Long id = recruitmentService.save(dto);

        //when
        recruitmentService.deleteRecruitment(id);
        Optional<Recruitment> recruitment = recruitmentRepository.findByIdAndDeletedAtIsNull(id);

        //then
        assertThat(recruitment.isPresent()).isFalse();
    }
}
