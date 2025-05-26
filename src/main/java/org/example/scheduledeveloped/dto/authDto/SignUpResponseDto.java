package org.example.scheduledeveloped.dto.authDto;


import lombok.Getter;


/**
 *
 */
@Getter
public class SignUpResponseDto {

    private final Long id;

    private final String email;

    private final String userName;


    public SignUpResponseDto(Long id, String username, String email) {
        this.id = id;
        this.email = email;
        this.userName = username;
    }
}
