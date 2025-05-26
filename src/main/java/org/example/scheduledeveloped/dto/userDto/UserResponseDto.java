package org.example.scheduledeveloped.dto.userDto;

import lombok.Getter;
import org.example.scheduledeveloped.entity.User;


/**
 *
 */
@Getter
public class UserResponseDto {
    private final Long id;

    private final String email;

    private final String userName;

    public UserResponseDto(Long id, String username, String email) {
        this.id = id;
        this.email = email;
        this.userName = username;
    }

    public static UserResponseDto toDto(User user){
        return new UserResponseDto(user.getId(),  user.getUserName(), user.getEmail());
    }
}