package org.example.scheduledeveloped.dto.authDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * 회원가입 요청을 처리하기 위한 DTO입니다.
 * 사용자 이름, 이메일, 비밀번호를 포함하며,
 * 각 필드에 대해 유효성 검사가 적용되어 있습니다.
 */
@Getter
public class SignUpRequestDto {

    /**
     * 사용자 이름 (null 불가)
     */
    @NotNull(message = "사용자 이름은 필수 입력 항목입니다.")
    private final String userName;

    /**
     * 사용자 이메일 (정규표현식 기반 형식 검사)
     * 이메일은 영어로 입력해야 하며, 일반적인 이메일 형식을 따라야 합니다.
     */
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "올바른 이메일 형식이 아닙니다. email은 영어로 입력해주세요"
    )
    private final String email;

    /**
     * 사용자 비밀번호 (최소 8자 이상)
     */
    @Size(min = 8, message = "최소 8자 이상 입력해주세요!")
    private final String password;

    /**
     * 생성자
     *
     * @param userName 사용자 이름
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     */
    public SignUpRequestDto(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
