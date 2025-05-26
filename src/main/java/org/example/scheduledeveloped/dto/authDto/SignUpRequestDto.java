package org.example.scheduledeveloped.dto.authDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;


/**
 *
 */
@Getter
public class SignUpRequestDto {

    @NotNull
    private final String userName;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "올바른 이메일 형식이 아닙니다. email은 영어로 입력해주세요")
    private final String email;

    @Size(min = 8
    ,message = "최소 8자 이상 입력해주세요!")
    private final String password;

    public SignUpRequestDto(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
