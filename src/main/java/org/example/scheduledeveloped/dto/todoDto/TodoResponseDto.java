package org.example.scheduledeveloped.dto.todoDto;

import lombok.Getter;
import org.example.scheduledeveloped.entity.Todo;


/**
 *
 */
@Getter
public class TodoResponseDto {
    private final Long id;
    private final String title;
    private final String contents;
    private final String userName; // ✅ 작성자 이름 추가

    public TodoResponseDto(Long id, String title, String contents, String userName) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.userName = userName;
    }

    /**
     *
     * @param todo
     * @return
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
