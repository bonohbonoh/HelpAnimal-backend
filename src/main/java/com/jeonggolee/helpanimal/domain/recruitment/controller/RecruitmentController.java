package com.jeonggolee.helpanimal.domain.recruitment.controller;

import com.jeonggolee.helpanimal.common.response.ResponseDto;
import com.jeonggolee.helpanimal.domain.recruitment.dto.request.RecruitmentRegistDto;
import com.jeonggolee.helpanimal.domain.recruitment.dto.request.RecruitmentUpdateDto;
import com.jeonggolee.helpanimal.domain.recruitment.service.RecruitmentService;
import com.jeonggolee.helpanimal.domain.user.dto.CustomUserDetails;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    /**
     * 공고 등록
     */
    @PostMapping("/api/v1/recruitment")
    public ResponseEntity<?> registRecruitment(
        @RequestBody @Valid RecruitmentRegistDto registDto, @AuthenticationPrincipal
        CustomUserDetails customUserDetails) throws Exception {
        return new ResponseEntity<>(ResponseDto.ok(recruitmentService.save(registDto)),
            HttpStatus.CREATED);
    }

    /**
     * 공고 전체조회(페이징)
     */
    @GetMapping("/api/v1/recruitment")
    public ResponseEntity<?> getAllRecruitmentList(
        Pageable pageable) throws Exception {
        return new ResponseEntity<>(
            ResponseDto.ok(recruitmentService.getRecriutmentListWithPaging(pageable)),
            HttpStatus.OK);
    }

    /**
     * 공고 수정
     */
    @PutMapping("/api/v1/recruitment/{id}")
    public ResponseEntity<?> updateRecruitment(
        @PathVariable("id") Long id,
        @RequestBody @Valid RecruitmentUpdateDto updateDto) throws Exception {
        recruitmentService.updateRecruitment(id, updateDto);
        return new ResponseEntity<>(ResponseDto.ok(), HttpStatus.OK);
    }

    /**
     * 공고 상세조회
     */
    @GetMapping("/api/v1/recruitment/{id}")
    public ResponseEntity<?> getRecruitmentDetail(
        @PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(
            ResponseDto.ok(recruitmentService.getRecruitmentDetail(id)), HttpStatus.OK);
    }

    /**
     * 공고 삭제
     */
    @DeleteMapping("/api/v1/recruitment/{id}")
    public ResponseEntity<?> deleteRecruitment(@PathVariable("id") @Valid Long id)
        throws Exception {
        recruitmentService.deleteRecruitment(id);
        return new ResponseEntity<>(ResponseDto.ok(), HttpStatus.OK);
    }

}
