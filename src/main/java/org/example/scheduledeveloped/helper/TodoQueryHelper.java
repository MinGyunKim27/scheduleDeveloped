package org.example.scheduledeveloped.helper;

import org.example.scheduledeveloped.entity.Todo;
import org.example.scheduledeveloped.repository.TodoRepository;

import java.util.List;


/**
 * TodoService 에서 제목과 내용을 입력할때 존재하는지 여부에 따라 분기를 나누기 위한 Class
 */
public class TodoQueryHelper {
    public static List<Todo> filterTodos(String title, String contents, TodoRepository repo) {
        if (title != null && !title.isBlank() && contents != null && !contents.isBlank()) {
            return repo.findByContentsContainingAndTitleContaining(contents, title);
        } else if (title != null && !title.isBlank()) {
            return repo.findByTitleContaining(title);
        } else if (contents != null && !contents.isBlank()) {
            return repo.findByContentsContaining(contents);
        } else {
            return repo.findAll();
        }
    }
}
