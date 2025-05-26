package org.example.scheduledeveloped.dto.todoDto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 할 일(Todo) 생성 요청을 처리하기 위한 DTO입니다.
 * 제목과 내용에 대해 유효성 검사를 적용하며, 클라이언트로부터 전달받은 데이터를 담습니다.
 */
@Getter
@Setter
@NoArgsConstructor
public class CreateTodoRequestDto {

    /**
     * Todo 제목
     * 최대 10자까지 허용되며, 이를 초과하면 검증 오류가 발생합니다.
     */
    @Size(max = 10, message = "10글자 이하로 입력해 주세요!")
    private String title;

    /**
     * Todo 내용
     * 최소 10자 이상이어야 하며, 짧으면 검증 오류가 발생합니다.
     */
    @Size(min = 10, message = "10자 이상 입력해 주세요!")
    private String contents;

    /**
     * 생성자 (모든 필드를 초기화)
     *
     * @param title   Todo 제목
     * @param contents Todo 내용
     */
    public CreateTodoRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
