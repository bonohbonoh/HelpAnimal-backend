package com.jeonggolee.helpanimal.domain.recruitment.repository;

import com.jeonggolee.helpanimal.domain.recruitment.entity.Recruitment;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentMethod;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class RecruitmentRepositoryTest {

    @Autowired
    RecruitmentRepository recruitmentRepository;

    private static final String name = "테스트 공고명";
    private static final RecruitmentType recruitmentType = RecruitmentType.CREW;
    private static final String content = "테스트 본문";
    private static final int participant = 11;
    private static final String imageUrl = "test";
    private static final RecruitmentMethod recruitmentMethod = RecruitmentMethod.FIRST_COME;


    private Recruitment initEntity() {
        return Recruitment.builder()
                .name(name)
                .recruitmentType(recruitmentType)
                .content(content)
                .participant(participant)
                .imageUrl(imageUrl)
                .recruitmentMethod(recruitmentMethod)
                .build();
    }

    @Test
    @DisplayName("공고 등록")
    void 공고등록() {
        //given
        Recruitment recruitment = initEntity();

        //when
        Long id = recruitmentRepository.save(recruitment).getId();

        //then
        assertThat(recruitmentRepository.findById(id).isPresent()).isTrue();
    }

    @Test
    @DisplayName("공고 ID로 조회")
    void 공고ID로_조회() {
        //given
        Recruitment recruitment = initEntity();
        Long id = recruitmentRepository.save(recruitment).getId();

        //when
        Optional<Recruitment> findRecruitment = recruitmentRepository.findById(id);

        //then
        assertThat(findRecruitment.isPresent()).isTrue();
    }

    @Test
    @DisplayName("공고 ID로 조회 실패")
    void 공고ID로_조회_실패() {
        //given
        Long id = 1L;

        //when
        Optional<Recruitment> recruitment = recruitmentRepository.findById(id);

        //then
        assertThat(recruitment.isPresent()).isFalse();
    }

    @Test
    @DisplayName("공고명 조회")
    void 공고명으로_조회() {
        //given
        Recruitment recruitment = initEntity();
        recruitmentRepository.save(recruitment);

        //when
        Optional<Recruitment> findRecruitment = recruitmentRepository.findByName(name);

        //then
        assertThat(findRecruitment.isPresent()).isTrue();
    }

    @Test
    @DisplayName("공고명으로 조회 실패")
    void 공고명으로_조회_실패() {
        //given

        //when
        Optional<Recruitment> recruitment = recruitmentRepository.findByName("테스트실패");

        //then
        assertThat(recruitment.isPresent()).isFalse();
    }

    @Test
    @DisplayName("공고 수정")
    void 공고수정() {
        //given
        Recruitment recruitment = initEntity();
        recruitmentRepository.save(recruitment);

        //when
        recruitment.updateRecruitmentName("수정");
        Recruitment updateRecruitment = recruitmentRepository.save(recruitment);

        //then
        assertThat(updateRecruitment.getName()).isEqualTo(recruitment.getName());
    }

    @Test
    @DisplayName("공고 삭제")
    void 공고삭제() {
        //given
        Recruitment recruitment = initEntity();
        Long id = recruitmentRepository.save(recruitment).getId();

        //when
        recruitmentRepository.delete(recruitment);

        //then
        Optional<Recruitment> deleteRecruitment = recruitmentRepository.findById(id);
        assertThat(deleteRecruitment.isPresent()).isFalse();
    }

}
