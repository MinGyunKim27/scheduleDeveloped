package org.example.scheduledeveloped.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateTodoRequestDto {

    private String title;

    private String contents;


    public UpdateTodoRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}