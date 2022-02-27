package com.jeonggolee.helpanimal.domain.recruitment.repository;

import com.jeonggolee.helpanimal.domain.recruitment.dto.response.RecruitmentApplicationDetailDto;
import com.jeonggolee.helpanimal.domain.recruitment.entity.RecruitmentApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class RecruitApplicationDetailRepositoryTest {
    @Autowired
    private RecruitmentApplicationRepository recruitmentApplicationRepository;

    private static final String comment = "TEST";

    private RecruitmentApplication initEntity() {
        return RecruitmentApplication.builder()
                .comment(comment)
                .build();
    }

    @Test
    @DisplayName("공고신청내역 등록")
    void 공고신청내역등록() {
        //given
        RecruitmentApplication recruitmentApplication = initEntity();

        //when
        Long id = recruitmentApplicationRepository.save(recruitmentApplication).getId();

        //then
        assertThat(recruitmentApplicationRepository.findById(id).isPresent()).isTrue();
    }

    @Test
    @DisplayName("공고신청내역 ID로 조회")
    void 공고신청내역ID로_조회() {
        //given
        RecruitmentApplication recruitmentApplication = initEntity();
        Long id = recruitmentApplicationRepository.save(recruitmentApplication).getId();

        //when
        Optional<RecruitmentApplication> findRecruitmentApplicationDetail = recruitmentApplicationRepository.findById(id);

        //then
        assertThat(findRecruitmentApplicationDetail.isPresent()).isTrue();
    }

    @Test
    @DisplayName("공고신청내역 ID로 조회 실패")
    void 공고신청내역ID로_조회_실패() {
        //given
        Long id = 1000L;

        //when
        Optional<RecruitmentApplication> recruitmentApplicationDetail = recruitmentApplicationRepository.findById(id);

        //then
        assertThat(recruitmentApplicationDetail.isPresent()).isFalse();
    }

    @Test
    @DisplayName("공고신청내역 수정")
    void 공고신청내역수정() {
        //given
        RecruitmentApplication recruitmentApplication = initEntity();
        recruitmentApplicationRepository.save(recruitmentApplication);

        //when
        recruitmentApplication.updateComment("수정");
        RecruitmentApplication updateRecruitmentApplication = recruitmentApplicationRepository.save(recruitmentApplication);

        //then
        assertThat(updateRecruitmentApplication.getComment()).isEqualTo(updateRecruitmentApplication.getComment());
    }

    @Test
    @DisplayName("공고신청내역 삭제")
    void 공고신청내역삭제() {
        //given
        RecruitmentApplication recruitmentApplication = initEntity();
        Long id = recruitmentApplicationRepository.save(recruitmentApplication).getId();

        //when
        recruitmentApplicationRepository.delete(recruitmentApplication);

        //then
        Optional<RecruitmentApplication> deleteRecruitmentApplicationDetail = recruitmentApplicationRepository.findById(id);
        assertThat(deleteRecruitmentApplicationDetail.isPresent()).isFalse();
    }

    @Test
    @DisplayName("공고내역_공고로_조회_페이징")
    void 공고내역_공고로_조회_페이징() {
        //given

        Pageable pageable = PageRequest.of(0, 10);
        //when
        Page<RecruitmentApplicationDetailDto> pages = recruitmentApplicationRepository
                .findRecruitmentApplicationByRecruitment(pageable, 1L);
        //then
        assertThat(pages.getNumberOfElements()).isEqualTo(10);
        assertThat(pages.getTotalElements()).isEqualTo(12L);
    }

    @Test
    @DisplayName("공고내역_유저로_조회_페이징")
    void 공고내역_유저로_조회_페이징() {
        //given

        Pageable pageable = PageRequest.of(0, 10);
        //when
        Page<RecruitmentApplicationDetailDto> pages = recruitmentApplicationRepository
                .findRecruitmentApplicationByUserId(pageable, 1L);
        //then
        assertThat(pages.getNumberOfElements()).isEqualTo(10);
        assertThat(pages.getTotalElements()).isEqualTo(12L);
    }
}
