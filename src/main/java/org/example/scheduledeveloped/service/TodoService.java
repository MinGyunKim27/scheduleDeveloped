package org.example.scheduledeveloped.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.scheduledeveloped.dto.todoDto.CreateTodoRequestDto;
import org.example.scheduledeveloped.dto.todoDto.TodoResponseDto;
import org.example.scheduledeveloped.dto.userDto.UserResponseDto;
import org.example.scheduledeveloped.entity.Todo;
import org.example.scheduledeveloped.entity.User;
import org.example.scheduledeveloped.exception.AccessDeniedException;
import org.example.scheduledeveloped.exception.UserNotFoundException;
import org.example.scheduledeveloped.helper.TodoQueryHelper;
import org.example.scheduledeveloped.repository.TodoRepository;
import org.example.scheduledeveloped.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * 새로운 Todo를 생성한다.
     *
     * @param dto 생성할 Todo 정보 (제목, 내용)
     * @param userResponseDto 현재 로그인한 사용자 정보
     * @return 생성된 Todo 정보를 담은 응답 DTO
     * @throws UserNotFoundException 세션 사용자 정보를 찾을 수 없을 때 발생
     */
    @Transactional
    public TodoResponseDto createTodo(CreateTodoRequestDto dto, UserResponseDto userResponseDto) {
        Long userId = userResponseDto.getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("세션 사용자 정보를 찾을 수 없습니다."));

        Todo todo = new Todo(dto.getTitle(), dto.getContents(), user);
        todoRepository.save(todo);

        return TodoResponseDto.toDto(todo);
    }

    /**
     * 제목 또는 내용 조건에 따라 Todo 리스트를 조회한다.
     * 조건이 없으면 전체 Todo를 조회한다.
     *
     * @param title 제목 검색 조건 (nullable)
     * @param contents 내용 검색 조건 (nullable)
     * @return 조건에 맞는 Todo 목록의 응답 DTO 리스트
     */
    public List<TodoResponseDto> findTodosByOptionalConditions(String title, String contents) {
        List<Todo> todos = TodoQueryHelper.filterTodos(title, contents, todoRepository);
        return todos.stream().map(TodoResponseDto::toDto).toList();
    }

    /**
     * 특정 ID에 해당하는 Todo를 조회한다.
     *
     * @param id 조회할 Todo ID
     * @return 해당 Todo의 응답 DTO
     */
    public TodoResponseDto findTodoById(Long id) {
        Todo byId = getTodoOrThrow(id);
        return TodoResponseDto.toDto(byId);
    }

    /**
     * Todo를 수정한다. 수정은 본인만 가능하다.
     *
     * @param id 수정할 Todo ID
     * @param title 새로운 제목
     * @param contents 새로운 내용
     * @param userResponseDto 현재 로그인한 사용자 정보
     * @return 수정된 Todo의 응답 DTO
     * @throws AccessDeniedException 다른 사용자의 Todo를 수정하려 할 때 발생
     */
    @Transactional
    public TodoResponseDto updateTodo(Long id, String title, String contents, UserResponseDto userResponseDto) {
        Todo byId = getTodoOrThrow(id);
        validateTodoOwner(byId, userResponseDto.getId());
        byId.updateTodo(title, contents);
        return TodoResponseDto.toDto(byId);
    }

    /**
     * Todo를 삭제한다. 삭제는 본인만 가능하다.
     *
     * @param id 삭제할 Todo ID
     * @param userResponseDto 현재 로그인한 사용자 정보
     * @throws AccessDeniedException 다른 사용자의 Todo를 삭제하려 할 때 발생
     */
    public void deleteTodo(Long id,  UserResponseDto userResponseDto) {
        Todo byId = getTodoOrThrow(id);
        validateTodoOwner(byId, userResponseDto.getId());
        todoRepository.delete(byId);
    }

    /**
     * ID로 Todo를 조회하고, 존재하지 않으면 예외를 던진다.
     *
     * @param id 조회할 Todo ID
     * @return 조회된 Todo 엔티티
     */
    private Todo getTodoOrThrow(Long id) {
        return todoRepository.findTodoByIdOrElseThrow(id);
    }

    /**
     * Todo의 작성자가 현재 사용자와 일치하는지 검증한다.
     *
     * @param todo 검증 대상 Todo
     * @param userId 현재 사용자 ID
     * @throws AccessDeniedException 사용자 권한이 없을 경우 발생
     */
    private void validateTodoOwner(Todo todo, Long userId) throws AccessDeniedException {
        if (!todo.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("해당 할 일을 수정할 권한이 없습니다.");
        }
    }

    // TodoService.java
    public Page<TodoResponseDto> findTodosPaged(Pageable pageable) {
        return todoRepository.findAll(pageable)
                .map(TodoResponseDto::toDto); // Page<Todo> → Page<TodoResponseDto>
    }

}

