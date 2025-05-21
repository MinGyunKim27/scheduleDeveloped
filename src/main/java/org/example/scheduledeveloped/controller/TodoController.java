package org.example.scheduledeveloped.controller;

import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.dto.CreateTodoRequestDto;
import org.example.scheduledeveloped.dto.TodoResponseDto;
import org.example.scheduledeveloped.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
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
    public ResponseEntity<List<TodoResponseDto>> findAll(){
        List<TodoResponseDto> all = todoService.findAll();

        return new ResponseEntity<>(all,HttpStatus.OK);
    }
}
