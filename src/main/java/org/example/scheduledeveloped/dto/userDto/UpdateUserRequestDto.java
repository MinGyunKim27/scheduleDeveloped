package org.example.scheduledeveloped.dto.userDto;

import lombok.Getter;

/**
 * 사용자 정보(이름, 비밀번호) 수정을 위한 요청 DTO 클래스입니다.
 * 사용자 이름 변경 또는 비밀번호 변경 요청 시 사용됩니다.
 */
@Getter
public class UpdateUserRequestDto {

    /**
     * 수정할 사용자 이름.
     */
    private final String userName;

    /**
     * 기존 비밀번호 (본인 인증용).
     */
    private final String oldPassword;

    /**
     * 새 비밀번호.
     */
    private final String newPassword;

    /**
     * 사용자 수정 요청 DTO 생성자.
     *
     * @param userName 수정할 사용자 이름
     * @param oldPassword 기존 비밀번호
     * @param newPassword 새 비밀번호
     */
    public UpdateUserRequestDto(String userName, String oldPassword, String newPassword) {
        this.userName = userName;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
