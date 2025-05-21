package org.example.scheduledeveloped.dto;

import lombok.Getter;

@Getter
public class UpdateUserRequestDto {

    private final String userName;

    private final String password;

    public UpdateUserRequestDto(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
