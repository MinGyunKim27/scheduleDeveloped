package org.example.scheduledeveloped.dto.todoDto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateTodoRequestDto {

    @Size(max = 10,message = "10글자 이하로 입력해 주세요!")
    private String title;

    @Size(min = 10)
    private String contents;


    public CreateTodoRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
