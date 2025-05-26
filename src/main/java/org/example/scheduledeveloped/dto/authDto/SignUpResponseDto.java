package org.example.scheduledeveloped.dto.authDto;

import lombok.Getter;

/**
 * 회원가입 성공 시 클라이언트에 반환되는 응답 DTO입니다.
 * 사용자 고유 ID, 이메일, 사용자 이름을 포함하며,
 * 민감한 정보(비밀번호 등)는 포함되지 않습니다.
 */
@Getter
public class SignUpResponseDto {

    /**
     * 회원가입된 사용자의 고유 ID
     */
    private final Long id;

    /**
     * 회원가입된 사용자의 이메일
     */
    private final String email;

    /**
     * 회원가입된 사용자의 이름
     */
    private final String userName;

    /**
     * 생성자
     *
     * @param id        사용자 ID
     * @param username  사용자 이름
     * @param email     사용자 이메일
     */
    public SignUpResponseDto(Long id, String username, String email) {
        this.id = id;
        this.email = email;
        this.userName = username;
    }
}
