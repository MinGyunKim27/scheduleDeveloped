package org.example.scheduledeveloped.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.Common.Const;
import org.example.scheduledeveloped.dto.commentDto.CommentRequestDto;
import org.example.scheduledeveloped.dto.commentDto.CommentResponseDto;
import org.example.scheduledeveloped.dto.userDto.SessionUserResponseDto;
import org.example.scheduledeveloped.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos/{todoId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestBody CommentRequestDto dto,
            @PathVariable Long todoId,
            HttpSession session
            ){

        SessionUserResponseDto sessionUserResponseDto = (SessionUserResponseDto) session.getAttribute(Const.LOGIN_USER);
        CommentResponseDto response = commentService.createComment(todoId, dto, sessionUserResponseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findComments(
            @PathVariable Long todoId
    ){
        List<CommentResponseDto> dtos = commentService.findCommentByTodoId(todoId);

        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long id,
            @RequestBody CommentRequestDto requestDto,
            HttpSession session
    ){
        SessionUserResponseDto sessionUserResponseDto = (SessionUserResponseDto) session.getAttribute(Const.LOGIN_USER);
        CommentResponseDto responseDto = commentService.updateComments(id,requestDto,sessionUserResponseDto);

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id,
            HttpSession session
    ){
        SessionUserResponseDto sessionUserResponseDto = (SessionUserResponseDto) session.getAttribute(Const.LOGIN_USER);
        Long d = commentService.deleteComment(id,sessionUserResponseDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
