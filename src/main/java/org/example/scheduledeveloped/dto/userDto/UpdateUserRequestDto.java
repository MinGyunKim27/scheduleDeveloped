package org.example.scheduledeveloped.dto.userDto;

import lombok.Getter;


/**
 *
 */
@Getter
public class UpdateUserRequestDto {

    private final String userName;

    private final String oldPassword;

    private final String newPassword;

    public UpdateUserRequestDto(String userName,String oldPassword, String newPassword) {
        this.userName = userName;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
