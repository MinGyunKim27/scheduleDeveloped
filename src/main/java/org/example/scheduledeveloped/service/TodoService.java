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


/**
 * Todo 관련 비즈니스 로직을 처리하는 서비스 클래스.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;


    /**
     *Todo 를 생성하는 메서드.
     * @param dto Todo 생성을 위한 정보
     * @param sessionUserDto 세션의 사용자 정보
     * @return Todo 응답 정보
     */
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


    /**
     *
     * @param title
     * @param contents
     * @return
     */
    public List<TodoResponseDto> findTodosByOptionalConditions(String title, String contents) {
        List<Todo> todos = TodoQueryHelper.filterTodos(title, contents, todoRepository);
        return todos.stream().map(TodoResponseDto::toDto).toList();
    }


    /**
     *
     * @param id
     * @return
     */
    public TodoResponseDto findTodoById(Long id) {
        Todo byId = getTodoOrThrow(id);
        return TodoResponseDto.toDto(byId);
    }


    /**
     *
     * @param id
     * @param title
     * @param contents
     * @param sessionUserDto
     * @return
     */
    @Transactional
    public TodoResponseDto updateTodo(Long id, String title, String contents, SessionUserResponseDto sessionUserDto){
        Todo byId = getTodoOrThrow(id);
        validateTodoOwner(byId,sessionUserDto.getId());
        byId.updateTodo(title,contents);
        return TodoResponseDto.toDto(byId);
    }


    /**
     *
     * @param id
     * @param sessionUserDto
     */
    public void deleteTodo(Long id, SessionUserResponseDto sessionUserDto) {

        Todo byId = getTodoOrThrow(id);
        validateTodoOwner(byId,sessionUserDto.getId());
        todoRepository.delete(byId);
    }


    /**
     *
     * @param id
     * @return
     */
    private Todo getTodoOrThrow(Long id) {
        return todoRepository.findTodoByIdOrElseThrow(id);
    }

    /**
     *
     * @param todo
     * @param userId
     * @throws AccessDeniedException
     */
    private void validateTodoOwner(Todo todo, Long userId) throws AccessDeniedException {
        if (!todo.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("해당 할 일을 수정할 권한이 없습니다.");
        }
    }



}
