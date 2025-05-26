package org.example.scheduledeveloped.dto.userDto;

import lombok.Getter;
import org.example.scheduledeveloped.entity.User;

/**
 * 클라이언트에 전달할 사용자 정보 응답 DTO 클래스입니다.
 * 사용자 ID, 이메일, 사용자명을 포함합니다.
 */
@Getter
public class UserResponseDto {

    /**
     * 사용자 고유 ID.
     */
    private final Long id;

    /**
     * 사용자 이메일.
     */
    private final String email;

    /**
     * 사용자 이름.
     */
    private final String userName;

    /**
     * UserResponseDto 생성자.
     *
     * @param id 사용자 ID
     * @param username 사용자 이름
     * @param email 사용자 이메일
     */
    public UserResponseDto(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.userName = username;
    }

    /**
     * User 엔티티를 기반으로 UserResponseDto로 변환합니다.
     *
     * @param user User 엔티티 객체
     * @return 변환된 UserResponseDto
     */
    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(user.getId(), user.getUserName(), user.getEmail());
    }
}
