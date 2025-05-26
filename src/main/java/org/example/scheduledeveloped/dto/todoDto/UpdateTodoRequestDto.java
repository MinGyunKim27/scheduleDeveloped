package org.example.scheduledeveloped.dto.todoDto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Todo 수정 요청을 처리하는 DTO 클래스입니다.
 * 제목과 내용을 수정할 수 있으며, 유효성 검사가 포함되어 있습니다.
 */
@Getter
@Setter
@NoArgsConstructor
public class UpdateTodoRequestDto {

    /**
     * 수정할 Todo 제목 (최대 10자).
     */
    @Size(max = 10, message = "10글자 이하로 입력해 주세요!")
    private String title;

    /**
     * 수정할 Todo 내용 (최소 10자 이상).
     */
    @Size(min = 10, message = "내용은 최소 10자 이상 입력해야 합니다.")
    private String contents;

    /**
     * Todo 수정 요청 DTO 생성자.
     *
     * @param title 수정할 제목
     * @param contents 수정할 내용
     */
    public UpdateTodoRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
