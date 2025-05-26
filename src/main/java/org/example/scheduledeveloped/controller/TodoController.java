package org.example.scheduledeveloped.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.Common.Const;
import org.example.scheduledeveloped.dto.todoDto.CreateTodoRequestDto;
import org.example.scheduledeveloped.dto.userDto.SessionUserResponseDto;
import org.example.scheduledeveloped.dto.todoDto.TodoResponseDto;
import org.example.scheduledeveloped.dto.todoDto.UpdateTodoRequestDto;
import org.example.scheduledeveloped.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDto> postTodo(
            @Validated @RequestBody CreateTodoRequestDto requestDto,
            HttpSession session){
        SessionUserResponseDto dto = (SessionUserResponseDto) session.getAttribute(Const.LOGIN_USER);
        TodoResponseDto responseDto =
                todoService.createTodo(
                        requestDto,
                        dto
                );

        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findTodos(
            @Validated @RequestParam(required = false) String title,
            @Validated @RequestParam(required = false) String contents) {

        List<TodoResponseDto> todoResponseDto = todoService.findTodosByOptionalConditions(title, contents);
        return ResponseEntity.ok(todoResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findTodoById(
            @Validated @PathVariable Long id
    ){
        TodoResponseDto todoResponseDto = todoService.findTodoById(id);

        return new ResponseEntity<>(todoResponseDto,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(
            @Validated @PathVariable Long id,
            @Validated @RequestBody UpdateTodoRequestDto requestDto,
            HttpSession session){
        SessionUserResponseDto dto = (SessionUserResponseDto) session.getAttribute(Const.LOGIN_USER);
        TodoResponseDto todoResponseDto = todoService.updateTodo(id,requestDto.getTitle(),requestDto.getContents(),dto);

        return new ResponseEntity<>(todoResponseDto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @Validated @PathVariable Long id,
            HttpSession session
    ){
        SessionUserResponseDto dto = (SessionUserResponseDto) session.getAttribute(Const.LOGIN_USER);
        todoService.deleteTodo(id,dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
