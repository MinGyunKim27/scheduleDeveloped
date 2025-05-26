package org.example.scheduledeveloped.dto.todoDto;

import lombok.Getter;
import org.example.scheduledeveloped.entity.Todo;

/**
 * 클라이언트에 전달할 Todo 응답 DTO입니다.
 * 할 일(Todo) 항목의 기본 정보와 작성자의 이름을 포함합니다.
 */
@Getter
public class TodoResponseDto {

    /**
     * Todo의 고유 ID
     */
    private final Long id;

    /**
     * Todo 제목
     */
    private final String title;

    /**
     * Todo 내용
     */
    private final String contents;

    /**
     * Todo 작성자의 사용자 이름
     */
    private final String userName;

    /**
     * TodoResponseDto 생성자
     *
     * @param id       Todo ID
     * @param title    제목
     * @param contents 내용
     * @param userName 작성자 이름
     */
    public TodoResponseDto(Long id, String title, String contents, String userName) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.userName = userName;
    }

    /**
     * Todo 엔티티를 TodoResponseDto로 변환합니다.
     * 작성자 정보가 없을 경우 "알 수 없음"으로 처리됩니다.
     *
     * @param todo 변환할 Todo 엔티티
     * @return TodoResponseDto 객체
     */
    public static TodoResponseDto toDto(Todo todo) {
        String userName = todo.getUser() != null ? todo.getUser().getUserName() : "알 수 없음";
        return new TodoResponseDto(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                userName
        );
    }
}
