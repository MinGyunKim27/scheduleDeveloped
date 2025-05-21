package org.example.scheduledeveloped.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.scheduledeveloped.dto.TodoResponseDto;
import org.example.scheduledeveloped.entity.Todo;
import org.example.scheduledeveloped.entity.User;
import org.example.scheduledeveloped.repository.TodoRepository;
import org.example.scheduledeveloped.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;


    @Transactional
    public TodoResponseDto save(String title, String contents, String userName){

        log.info("조회 시도 userName: {}", userName);

        User user = userRepository.findMemberByUserNameOrElseThrow(userName);

        Todo todo = new Todo(title,contents,user);

        todoRepository.save(todo);

        return new TodoResponseDto(todo.getId(),todo.getTitle(),todo.getContents(),userName);
    }


    public List<TodoResponseDto> findTodosByOptionalConditions(String title, String contents) {
        List<Todo> todos;

        if (title != null && !title.isBlank() && contents != null && !contents.isBlank()) {
            todos = todoRepository.findByContentsContainingAndTitle(contents, title);
        } else if (title != null && !title.isBlank()) {
            todos = todoRepository.findByTitle(title);
        } else if (contents != null && !contents.isBlank()) {
            todos = todoRepository.findByContentsContaining(contents);
        } else {
            todos = todoRepository.findAll();
        }

        return todos.stream().map(TodoResponseDto::toDto).toList();
    }

    public TodoResponseDto findTodoById(Long id) {

        Todo byId = todoRepository.findTodoByIdOrElseThrow(id);
        return TodoResponseDto.toDto(byId);
    }

    public TodoResponseDto updateTodo(Long id, String title, String contents) {
        Todo byId = todoRepository.findTodoByIdOrElseThrow(id);
        byId.updateTodo(title,contents);
        return TodoResponseDto.toDto(byId);
    }

    public void deleteTodo(Long id) {
        Todo byId = todoRepository.findTodoByIdOrElseThrow(id);
        todoRepository.delete(byId);
    }
}
