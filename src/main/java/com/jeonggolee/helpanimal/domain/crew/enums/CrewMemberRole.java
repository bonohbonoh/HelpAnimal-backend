package com.jeonggolee.helpanimal.domain.crew.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CrewMemberRole {
    /**
     * 회원: 게시판, 일정 열람
     * 정회원: 회원 권한 + 게시판, 일정 생성 + 본인의 게시판 및 일정에 대해 수정 및 삭제
     * 부마스터: 정회원 권한 + 공지 작성 + 모든 게시판 및 일정에 대해 수정 및 삭제 + 회원 및 정회원 모집 및 등급 관리
     * 마스터: 부마스터 권한 + 크루 관리 + 맴버 권한 설정 + 회원 추방
     */

    MASTER(5, "마스터"),
    SUB_MASTER(4, "부마스터"),
    OFFICIAL_MEMBER(1, "정회원"),
    MEMBER(0, "회원");

    private final Integer level;
    private final String value;

}
