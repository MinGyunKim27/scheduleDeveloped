package org.example.scheduledeveloped.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.Common.Const;
import org.example.scheduledeveloped.dto.todoDto.CreateTodoRequestDto;
import org.example.scheduledeveloped.dto.todoDto.TodoResponseDto;
import org.example.scheduledeveloped.dto.todoDto.UpdateTodoRequestDto;
import org.example.scheduledeveloped.dto.userDto.UserResponseDto;
import org.example.scheduledeveloped.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Todo 관련 HTTP 요청을 처리하는 컨트롤러입니다.
 * 할 일 생성, 조회, 수정, 삭제 기능을 제공합니다.
 */
@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    /**
     * 새로운 Todo를 생성합니다.
     *
     * @param requestDto 생성할 Todo 정보 (제목, 내용)
     * @param session 현재 로그인한 사용자의 세션
     * @return 생성된 Todo 정보
     */
    @PostMapping
    public ResponseEntity<TodoResponseDto> postTodo(
            @Validated @RequestBody CreateTodoRequestDto requestDto,
            HttpSession session) {
        UserResponseDto dto = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);
        TodoResponseDto responseDto = todoService.createTodo(requestDto, dto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 제목 또는 내용 조건으로 Todo 목록을 조회합니다.
     * 조건이 없으면 전체 목록을 반환합니다.
     *
     * @param title 제목 필터 (선택)
     * @param contents 내용 필터 (선택)
     * @return 조건에 일치하는 Todo 목록
     */
    @GetMapping
    public ResponseEntity<?> findTodos(
            @Validated @RequestParam(required = false) String title,
            @Validated @RequestParam(required = false) String contents) {

        List<TodoResponseDto> todoResponseDto = todoService.findTodosByOptionalConditions(title, contents);
        return ResponseEntity.ok(todoResponseDto);
    }

    /**
     * 특정 ID의 Todo를 조회합니다.
     *
     * @param id 조회할 Todo ID
     * @return 해당 Todo 정보
     */
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findTodoById(
            @Validated @PathVariable Long id) {

        TodoResponseDto todoResponseDto = todoService.findTodoById(id);
        return new ResponseEntity<>(todoResponseDto, HttpStatus.OK);
    }

    /**
     * 특정 ID의 Todo를 수정합니다.
     * 해당 Todo는 본인만 수정할 수 있습니다.
     *
     * @param id 수정할 Todo ID
     * @param requestDto 수정할 내용 (제목, 내용)
     * @param session 현재 로그인한 사용자의 세션
     * @return 수정된 Todo 정보
     */
    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(
            @Validated @PathVariable Long id,
            @Validated @RequestBody UpdateTodoRequestDto requestDto,
            HttpSession session) {

        UserResponseDto dto = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);
        TodoResponseDto todoResponseDto = todoService.updateTodo(id, requestDto.getTitle(), requestDto.getContents(), dto);

        return new ResponseEntity<>(todoResponseDto, HttpStatus.OK);
    }

    /**
     * 특정 ID의 Todo를 삭제합니다.
     * 해당 Todo는 본인만 삭제할 수 있습니다.
     *
     * @param id 삭제할 Todo ID
     * @param session 현재 로그인한 사용자의 세션
     * @return 성공 시 HTTP 200 반환
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @Validated @PathVariable Long id,
            HttpSession session) {

        UserResponseDto dto = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);
        todoService.deleteTodo(id, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
