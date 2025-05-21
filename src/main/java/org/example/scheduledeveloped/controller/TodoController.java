package org.example.scheduledeveloped.controller;

import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.dto.CreateTodoRequestDto;
import org.example.scheduledeveloped.dto.TodoResponseDto;
import org.example.scheduledeveloped.dto.UpdateTodoRequestDto;
import org.example.scheduledeveloped.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDto> postTodo(@RequestBody CreateTodoRequestDto requestDto){
        TodoResponseDto responseDto =
                todoService.save(
                        requestDto.getTitle(),
                        requestDto.getContents(),
                        requestDto.getUserName()
                );

        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findTodos(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String contents) {

        List<TodoResponseDto> todoResponseDto = todoService.findTodosByOptionalConditions(title, contents);
        return ResponseEntity.ok(todoResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> findTodoById(
            @PathVariable Long id
    ){
        TodoResponseDto todoResponseDto = todoService.findTodoById(id);

        return new ResponseEntity<>(todoResponseDto,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(
            @PathVariable Long id,
            @RequestBody UpdateTodoRequestDto requestDto){
        TodoResponseDto todoResponseDto = todoService.updateTodo(id,requestDto.getTitle(),requestDto.getContents());

        return new ResponseEntity<>(todoResponseDto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long id
    ){
        todoService.deleteTodo(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
