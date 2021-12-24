package com.jeonggolee.helpanimal.domain.recruitment.repository;

import com.jeonggolee.helpanimal.domain.recruitment.entity.RecruitmentApplicationDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class RecruitApplicationDetailRepositoryTest {
    @Autowired
    private RecruitmentApplicationDetailRepository recruitmentApplicationDetailRepository;

    private static final String comment = "TEST";

    private RecruitmentApplicationDetail initEntity() {
        return RecruitmentApplicationDetail.builder()
                .comment(comment)
                .build();
    }

    @Test
    @DisplayName("공고신청내역 등록")
    void 공고신청내역등록() {
        //given
        RecruitmentApplicationDetail recruitmentApplicationDetail = initEntity();

        //when
        Long id = recruitmentApplicationDetailRepository.save(recruitmentApplicationDetail).getId();

        //then
        assertThat(recruitmentApplicationDetailRepository.findById(id).isPresent()).isTrue();
    }

    @Test
    @DisplayName("공고신청내역 ID로 조회")
    void 공고신청내역ID로_조회() {
        //given
        RecruitmentApplicationDetail recruitmentApplicationDetail = initEntity();
        Long id = recruitmentApplicationDetailRepository.save(recruitmentApplicationDetail).getId();

        //when
        Optional<RecruitmentApplicationDetail> findRecruitmentApplicationDetail = recruitmentApplicationDetailRepository.findById(id);

        //then
        assertThat(findRecruitmentApplicationDetail.isPresent()).isTrue();
    }

    @Test
    @DisplayName("공고신청내역 ID로 조회 실패")
    void 공고신청내역ID로_조회_실패() {
        //given
        Long id = 1000L;

        //when
        Optional<RecruitmentApplicationDetail> recruitmentApplicationDetail = recruitmentApplicationDetailRepository.findById(id);

        //then
        assertThat(recruitmentApplicationDetail.isPresent()).isFalse();
    }

    @Test
    @DisplayName("공고신청내역 수정")
    void 공고신청내역수정() {
        //given
        RecruitmentApplicationDetail recruitmentApplicationDetail = initEntity();
        recruitmentApplicationDetailRepository.save(recruitmentApplicationDetail);

        //when
        recruitmentApplicationDetail.updateComment("수정");
        RecruitmentApplicationDetail updateRecruitmentApplicationDetail = recruitmentApplicationDetailRepository.save(recruitmentApplicationDetail);

        //then
        assertThat(updateRecruitmentApplicationDetail.getComment()).isEqualTo(updateRecruitmentApplicationDetail.getComment());
    }

    @Test
    @DisplayName("공고신청내역 삭제")
    void 공고신청내역삭제() {
        //given
        RecruitmentApplicationDetail recruitmentApplicationDetail = initEntity();
        Long id = recruitmentApplicationDetailRepository.save(recruitmentApplicationDetail).getId();

        //when
        recruitmentApplicationDetailRepository.delete(recruitmentApplicationDetail);

        //then
        Optional<RecruitmentApplicationDetail> deleteRecruitmentApplicationDetail = recruitmentApplicationDetailRepository.findById(id);
        assertThat(deleteRecruitmentApplicationDetail.isPresent()).isFalse();
    }
}
