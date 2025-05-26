package org.example.scheduledeveloped.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.Common.Const;
import org.example.scheduledeveloped.dto.commentDto.CommentRequestDto;
import org.example.scheduledeveloped.dto.commentDto.CommentResponseDto;
import org.example.scheduledeveloped.dto.userDto.UserResponseDto;
import org.example.scheduledeveloped.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 댓글(Comment) 관련 요청을 처리하는 컨트롤러입니다.
 * 특정 Todo에 대한 댓글 생성, 조회, 수정, 삭제 기능을 제공합니다.
 */
@RestController
@RequestMapping("/api/todos/{todoId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 특정 Todo에 댓글을 작성합니다.
     *
     * @param dto 댓글 내용 요청 DTO
     * @param todoId 댓글이 달릴 Todo의 ID
     * @param session 현재 로그인한 사용자 세션
     * @return 생성된 댓글 정보를 담은 응답 DTO (HTTP 201 Created)
     */
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestBody CommentRequestDto dto,
            @PathVariable Long todoId,
            HttpSession session
    ) {
        UserResponseDto userResponseDto = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);
        CommentResponseDto response = commentService.createComment(todoId, dto, userResponseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 특정 Todo에 달린 모든 댓글을 조회합니다.
     *
     * @param todoId 댓글을 조회할 Todo ID
     * @return 해당 Todo에 달린 댓글 목록
     */
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findComments(
            @PathVariable Long todoId
    ) {
        List<CommentResponseDto> dtos = commentService.findCommentByTodoId(todoId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    /**
     * 댓글을 수정합니다. 댓글 작성자만 수정할 수 있습니다.
     *
     * @param id 수정할 댓글 ID
     * @param requestDto 수정할 댓글 내용 DTO
     * @param session 현재 로그인한 사용자 세션
     * @return 수정된 댓글 응답 DTO
     */
    @PatchMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long id,
            @RequestBody CommentRequestDto requestDto,
            HttpSession session
    ) {
        UserResponseDto userResponseDto = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);
        CommentResponseDto responseDto = commentService.updateComments(id, requestDto, userResponseDto);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * 댓글을 삭제합니다. 댓글 작성자만 삭제할 수 있습니다.
     *
     * @param id 삭제할 댓글 ID
     * @param session 현재 로그인한 사용자 세션
     * @return HTTP 200 OK 응답
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id,
            HttpSession session
    ) {
        UserResponseDto userResponseDto = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);
        commentService.deleteComment(id, userResponseDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
