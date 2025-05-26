package org.example.scheduledeveloped.dto.userDto;

import lombok.Getter;
import org.example.scheduledeveloped.entity.User;


@Getter
public class SessionUserDto {

    private final Long id;

    private final String email;

    private final String userName;

    public SessionUserDto(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.userName = username;
    }

    public static SessionUserDto toDto(User user){
        return new SessionUserDto(user.getId(), user.getEmail(), user.getUserName());
    }
}
