package com.jeonggolee.helpanimal.domain.recruitment.controller;

import com.jeonggolee.helpanimal.domain.recruitment.dto.request.RecruitmentApplicationRequestDto;
import com.jeonggolee.helpanimal.domain.recruitment.dto.response.RecruitmentApplicationDetailDto;
import com.jeonggolee.helpanimal.domain.recruitment.dto.response.RecruitmentApplicationSearchDto;
import com.jeonggolee.helpanimal.domain.recruitment.service.RecruitmentApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RecruitmentApplicationController {
    private final RecruitmentApplicationService recruitmentApplicationService;

    /**
     * 참여신청 등록
     */
    @PostMapping("/api/v1/recruitment/apply")
    public ResponseEntity<Long> requestRecruitment(@RequestBody RecruitmentApplicationRequestDto dto) {
        return new ResponseEntity<Long>(recruitmentApplicationService.requestRecruitment(dto), HttpStatus.CREATED);
    }

    /**
     * 공고로 참여신청 목록 조회(페이징)
     */
    @GetMapping("/api/v1/recruitment/apply/recruitment/{recruitmentId}")
    public ResponseEntity<RecruitmentApplicationSearchDto> findByRecruitmentId(@PathVariable("recruitmentId") Long recruitmentId, Pageable pageable) {
        return new ResponseEntity<RecruitmentApplicationSearchDto>(
                recruitmentApplicationService.findRecruitmentApplicationByRecruitment(pageable, recruitmentId)
                , HttpStatus.OK);
    }

    /**
     * 해당 유저의 참여신청 목록 조회(페이징)
     */
    @GetMapping("/api/v1/recruitment/apply/user/{userId}")
    public ResponseEntity<RecruitmentApplicationSearchDto> findByUserId(@PathVariable("userId") Long userId, Pageable pageable) {
        return new ResponseEntity<RecruitmentApplicationSearchDto>(
                recruitmentApplicationService.findRecruitmentApplicationByUser(pageable, userId)
                , HttpStatus.OK);
    }

    /**
     * 참여신청 삭제
     */
    @DeleteMapping("/api/v1/recruitment/apply/{id}")
    public ResponseEntity deleteRecruitmentApplication(@PathVariable("id") Long id) {
        recruitmentApplicationService.deleteRecruitmentApplication(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 참여신청 내역 상세조회
     */
    @GetMapping("/api/v1/recruitment/apply/{id}")
    public ResponseEntity<RecruitmentApplicationDetailDto> getApplicationById(@PathVariable("id") Long id) {
        return new ResponseEntity<RecruitmentApplicationDetailDto>(recruitmentApplicationService.findById(id), HttpStatus.OK);
    }
}
