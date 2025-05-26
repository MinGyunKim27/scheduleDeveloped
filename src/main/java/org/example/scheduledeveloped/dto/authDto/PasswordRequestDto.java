package org.example.scheduledeveloped.dto.authDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 비밀번호 관련 요청을 처리하는 DTO 클래스입니다.
 * 주로 비밀번호 확인 또는 수정 시 사용됩니다.
 */
@Getter
@Setter
@AllArgsConstructor
public class PasswordRequestDto {

    /**
     * 사용자 비밀번호.
     */
    private final String password;
}
