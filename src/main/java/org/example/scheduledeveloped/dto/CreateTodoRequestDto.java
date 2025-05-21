package org.example.scheduledeveloped.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateTodoRequestDto {

    private String title;

    private String contents;

    private String userName;

    public CreateTodoRequestDto(String title, String contents, String userName) {
        this.title = title;
        this.contents = contents;
        this.userName = userName;
    }
}
