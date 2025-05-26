package org.example.scheduledeveloped.dto.userDto;

import lombok.Getter;
import org.example.scheduledeveloped.entity.User;

/*
비밀번호를 사용해도 되는 것인가?
 */
@Getter
public class SessionUserResponseDto {
    private final Long id;

    private final String email;

    private final String userName;

    private final String password;

    public SessionUserResponseDto(Long id,  String email,String username, String password) {
        this.id = id;
        this.email = email;
        this.userName = username;
        this.password = password;
    }

    public static SessionUserResponseDto toDto(User user){
        return new SessionUserResponseDto(user.getId(), user.getEmail(), user.getUserName(),user.getPassword());
    }
}