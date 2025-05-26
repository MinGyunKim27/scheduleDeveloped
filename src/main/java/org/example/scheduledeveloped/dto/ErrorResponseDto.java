package org.example.scheduledeveloped.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 예외 발생 시 클라이언트에게 반환할 에러 응답 DTO입니다.
 * HTTP 상태 코드, 메시지, 발생 시각, 상세 필드 에러 정보를 포함합니다.
 */
@Getter
public class ErrorResponseDto {

    /**
     * HTTP 상태 코드 (예: 400, 403, 404, 500 등)
     */
    private final int status;

    /**
     * 예외 메시지 또는 에러에 대한 간단한 설명
     */
    private final String message;

    /**
     * 에러 발생 시각 (서버 기준)
     */
    private final LocalDateTime timestamp;

    /**
     * 필드별 상세 에러 메시지 (예: {"email": "이메일 형식이 올바르지 않습니다."})
     * 단순 에러일 경우 null 가능
     */
    private final Map<String, String> errors;

    /**
     * 에러 응답 객체 생성자
     *
     * @param status  HTTP 상태 코드
     * @param message 에러 메시지
     * @param errors  필드 오류 정보 (nullable)
     */
    public ErrorResponseDto(int status, String message, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
        this.timestamp = LocalDateTime.now(); // 응답 생성 시각 자동 설정
    }

}
