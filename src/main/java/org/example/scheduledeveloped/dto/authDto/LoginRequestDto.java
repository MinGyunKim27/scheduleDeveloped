package org.example.scheduledeveloped.dto.authDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 로그인 요청 정보를 담는 DTO 클래스입니다.
 * 이메일과 비밀번호를 포함하며, 유효성 검사 조건이 적용되어 있습니다.
 */
@Getter
@Setter
@AllArgsConstructor
public class LoginRequestDto {

    /**
     * 사용자 이메일.
     * - 비어 있을 수 없으며(@NotBlank), 이메일 형식이어야 함(@Email).
     */
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private final String email;

    /**
     * 사용자 비밀번호.
     * - 비어 있을 수 없으며(@NotBlank), 최소 8자 이상이어야 함(@Size).
     */
    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private final String password;
}
