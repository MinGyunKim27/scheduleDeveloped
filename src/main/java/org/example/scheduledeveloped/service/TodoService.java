package org.example.scheduledeveloped.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.scheduledeveloped.dto.todoDto.CreateTodoRequestDto;
import org.example.scheduledeveloped.dto.userDto.SessionUserResponseDto;
import org.example.scheduledeveloped.dto.todoDto.TodoResponseDto;
import org.example.scheduledeveloped.entity.Todo;
import org.example.scheduledeveloped.entity.User;
import org.example.scheduledeveloped.exception.AccessDeniedException;
import org.example.scheduledeveloped.exception.UserNotFoundException;
import org.example.scheduledeveloped.helper.TodoQueryHelper;
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
    public TodoResponseDto createTodo(CreateTodoRequestDto dto, SessionUserResponseDto sessionUserDto) {
        ;
        Long userId = sessionUserDto.getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("세션 사용자 정보를 찾을 수 없습니다."));

        Todo todo = new Todo(dto.getTitle(), dto.getContents(), user);
        todoRepository.save(todo);

        return TodoResponseDto.toDto(todo);
    }

    public List<TodoResponseDto> findTodosByOptionalConditions(String title, String contents) {
        List<Todo> todos = TodoQueryHelper.filterTodos(title, contents, todoRepository);
        return todos.stream().map(TodoResponseDto::toDto).toList();
    }

    public TodoResponseDto findTodoById(Long id) {
        Todo byId = getTodoOrThrow(id);
        return TodoResponseDto.toDto(byId);
    }

    @Transactional
    public TodoResponseDto updateTodo(Long id, String title, String contents, SessionUserResponseDto sessionUserDto){
        Todo byId = getTodoOrThrow(id);
        validateTodoOwner(byId,sessionUserDto.getId());
        byId.updateTodo(title,contents);
        return TodoResponseDto.toDto(byId);
    }

    public void deleteTodo(Long id, SessionUserResponseDto sessionUserDto) {

        Todo byId = getTodoOrThrow(id);
        validateTodoOwner(byId,sessionUserDto.getId());
        todoRepository.delete(byId);
    }

    private Todo getTodoOrThrow(Long id) {
        return todoRepository.findTodoByIdOrElseThrow(id);
    }

    private void validateTodoOwner(Todo todo, Long userId) throws AccessDeniedException {
        if (!todo.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("해당 할 일을 수정할 권한이 없습니다.");
        }
    }



}
