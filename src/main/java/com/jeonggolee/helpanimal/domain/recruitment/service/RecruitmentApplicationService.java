package com.jeonggolee.helpanimal.domain.recruitment.service;

import com.jeonggolee.helpanimal.common.exception.RecruitmentApplicationNotFoundException;
import com.jeonggolee.helpanimal.common.exception.RecruitmentNotFoundException;
import com.jeonggolee.helpanimal.common.exception.UserNotFoundException;
import com.jeonggolee.helpanimal.domain.recruitment.dto.request.RecruitmentApplicationRequestDto;
import com.jeonggolee.helpanimal.domain.recruitment.dto.response.RecruitmentApplicationDetailDto;
import com.jeonggolee.helpanimal.domain.recruitment.dto.response.RecruitmentApplicationSearchDto;
import com.jeonggolee.helpanimal.domain.recruitment.entity.Recruitment;
import com.jeonggolee.helpanimal.domain.recruitment.entity.RecruitmentApplication;
import com.jeonggolee.helpanimal.domain.recruitment.enums.RecruitmentApplicationStatus;
import com.jeonggolee.helpanimal.domain.recruitment.repository.RecruitmentApplicationRepository;
import com.jeonggolee.helpanimal.domain.recruitment.repository.RecruitmentRepository;
import com.jeonggolee.helpanimal.domain.user.entity.User;
import com.jeonggolee.helpanimal.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecruitmentApplicationService {
    private final RecruitmentApplicationRepository recruitmentApplicationRepository;
    private final UserRepository userRepository;
    private final RecruitmentRepository recruitmentRepository;

    @Transactional
    public Long requestRecruitment(RecruitmentApplicationRequestDto dto) {
        /**
         * 1. 유저 조회
         * 2. 공고 조회
         * 3. 기존 접수내역 삭제처리
         */
        User requestUser = findUser(dto.getEmail());
        Recruitment recruitment = findRecruitment(dto.getRecruitmentId());
        deleteRecruitmentApplicationHistory(recruitment.getId(), requestUser.getUserId());
        return recruitmentApplicationRepository.save(RecruitmentApplication.builder()
                .recruitment(recruitment)
                .user(requestUser)
                .comment(dto.getComment())
                .status(RecruitmentApplicationStatus.REQUEST)
                .build())
                .getId();
    }

    @Transactional
    public void deleteRecruitmentApplication(Long id) {
        RecruitmentApplication recruitmentApplication = recruitmentApplicationRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(
                () -> new RecruitmentApplicationNotFoundException("해당 신청내역이 존재하지 않습니다."));
        recruitmentApplication.delete();
    }

    public RecruitmentApplicationSearchDto findRecruitmentApplicationByRecruitment(Pageable pageable, Long id) {
        Page<RecruitmentApplicationDetailDto> resultWithPaging = recruitmentApplicationRepository
                .findRecruitmentApplicationByRecruitment(pageable, id);

        List<RecruitmentApplicationDetailDto> data = resultWithPaging
                .map(result -> RecruitmentApplicationDetailDto
                        .builder()
                        .id(result.getId())
                        .recruitmentId(result.getRecruitmentId())
                        .email(result.getEmail())
                        .comment(result.getComment())
                        .status(result.getStatus())
                        .build())
                .toList();

        return RecruitmentApplicationSearchDto
                .builder()
                .numberOfElements(resultWithPaging.getNumberOfElements())
                .totalElements(resultWithPaging.getTotalElements())
                .data(data)
                .build();
    }

    public RecruitmentApplicationSearchDto findRecruitmentApplicationByUser(Pageable pageable, Long userId) {
        Page<RecruitmentApplicationDetailDto> resultWithPaging = recruitmentApplicationRepository
                .findRecruitmentApplicationByUserId(pageable, userId);

        List<RecruitmentApplicationDetailDto> data = resultWithPaging
                .map(result -> RecruitmentApplicationDetailDto
                        .builder()
                        .id(result.getId())
                        .recruitmentId(result.getRecruitmentId())
                        .email(result.getEmail())
                        .comment(result.getComment())
                        .status(result.getStatus())
                        .build())
                .toList();

        return RecruitmentApplicationSearchDto
                .builder()
                .numberOfElements(resultWithPaging.getNumberOfElements())
                .totalElements(resultWithPaging.getTotalElements())
                .data(data)
                .build();
    }

    private User findUser(String email) {
        return userRepository.findByEmailAndDeletedAtNull(email).orElseThrow(
                () -> new UserNotFoundException("존재하지 않는 회원입니다."));
    }

    private Recruitment findRecruitment(Long recruitmentId) {
        return recruitmentRepository.findByIdAndDeletedAtIsNull(recruitmentId).orElseThrow(
                () -> new RecruitmentNotFoundException("해당 공고가 존재하지 않습니다."));
    }

    @Transactional
    public void deleteRecruitmentApplicationHistory(Long rid, Long userId) {
        List<RecruitmentApplication> history = recruitmentApplicationRepository.findByHistory(rid, userId);
        history.forEach(entities -> entities.delete());
    }


    public RecruitmentApplicationDetailDto findById(Long id) {
        RecruitmentApplication recruitmentApplication = recruitmentApplicationRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(
                ()-> new RecruitmentApplicationNotFoundException("해당 신청내역이 존재하지 않습니다."));
        return RecruitmentApplicationDetailDto.builder()
                .id(recruitmentApplication.getId())
                .recruitmentId(recruitmentApplication.getRecruitment().getId())
                .recruitmentName(recruitmentApplication.getRecruitment().getName())
                .email(recruitmentApplication.getUser().getEmail())
                .comment(recruitmentApplication.getComment())
                .status(recruitmentApplication.getStatus())
                .build();
    }
}
