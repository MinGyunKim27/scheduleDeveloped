package org.example.scheduledeveloped.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.dto.commentDto.CommentRequestDto;
import org.example.scheduledeveloped.dto.commentDto.CommentResponseDto;
import org.example.scheduledeveloped.dto.userDto.UserResponseDto;
import org.example.scheduledeveloped.entity.Comment;
import org.example.scheduledeveloped.entity.Todo;
import org.example.scheduledeveloped.entity.User;
import org.example.scheduledeveloped.exception.AccessDeniedException;
import org.example.scheduledeveloped.exception.CommentNotFoundException;
import org.example.scheduledeveloped.exception.TodoNotFoundException;
import org.example.scheduledeveloped.exception.UserNotFoundException;
import org.example.scheduledeveloped.repository.CommentRepository;
import org.example.scheduledeveloped.repository.TodoRepository;
import org.example.scheduledeveloped.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Comment 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    /**
     * 특정 Todo에 대한 댓글을 생성합니다.
     *
     * @param todoId 댓글을 작성할 대상 Todo의 ID
     * @param dto 댓글 생성 요청 DTO (내용 포함)
     * @param userResponseDto 현재 로그인한 사용자 정보
     * @return 생성된 댓글에 대한 응답 DTO
     * @throws TodoNotFoundException Todo가 존재하지 않을 경우
     * @throws UserNotFoundException 사용자 정보가 없을 경우
     */
    @Transactional
    public CommentResponseDto createComment(
            Long todoId,
            CommentRequestDto dto,
            UserResponseDto userResponseDto) {

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("해당 Todo를 찾을 수 없습니다."));

        Long userId = userResponseDto.getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        Comment comment = new Comment(dto.getContents(), todo, user);
        commentRepository.save(comment);

        return CommentResponseDto.toDto(comment);
    }

    /**
     * 특정 Todo에 연결된 모든 댓글을 조회합니다.
     *
     * @param todoId 댓글을 조회할 Todo의 ID
     * @return 해당 Todo의 댓글 리스트 (응답 DTO 형식)
     */
    public List<CommentResponseDto> findCommentByTodoId(Long todoId) {
        return commentRepository.findAllByTodo_Id(todoId)
                .stream()
                .map(CommentResponseDto::toDto)
                .toList();
    }

    /**
     * 댓글 내용을 수정합니다. 댓글 작성자만 수정할 수 있습니다.
     *
     * @param id 수정할 댓글 ID
     * @param requestDto 수정할 내용이 담긴 DTO
     * @param userResponseDto 현재 로그인한 사용자 정보
     * @return 수정된 댓글에 대한 응답 DTO
     * @throws CommentNotFoundException 댓글이 존재하지 않을 경우
     * @throws AccessDeniedException 사용자가 댓글 작성자가 아닐 경우
     */
    public CommentResponseDto updateComments(Long id, CommentRequestDto requestDto, UserResponseDto userResponseDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("해당 Comment를 찾을 수 없습니다."));

        validateCommentOwner(comment, userResponseDto.getId());
        comment.updateComment(requestDto.getContents());

        return CommentResponseDto.toDto(comment);
    }

    /**
     * 댓글을 삭제합니다. 댓글 작성자만 삭제할 수 있습니다.
     *
     * @param id 삭제할 댓글 ID
     * @param userResponseDto 현재 로그인한 사용자 정보
     * @return 성공 시 1L 반환
     * @throws CommentNotFoundException 댓글이 존재하지 않을 경우
     * @throws AccessDeniedException 사용자가 댓글 작성자가 아닐 경우
     */
    public Long deleteComment(Long id, UserResponseDto userResponseDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("해당 Comment를 찾을 수 없습니다."));

        validateCommentOwner(comment, userResponseDto.getId());
        commentRepository.deleteById(id);

        return 1L;
    }

    /**
     * 댓글 작성자인지 확인합니다.
     *
     * @param comment 확인할 댓글
     * @param userId 로그인한 사용자 ID
     * @throws AccessDeniedException 사용자가 댓글 작성자가 아닐 경우
     */
    private void validateCommentOwner(Comment comment, Long userId) {
        if (!comment.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("해당 댓글을 수정할 권한이 없습니다.");
        }
    }
}
