package org.example.scheduledeveloped.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.dto.commentDto.CommentRequestDto;
import org.example.scheduledeveloped.dto.commentDto.CommentResponseDto;
import org.example.scheduledeveloped.dto.userDto.SessionUserResponseDto;
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

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDto createComment(Long todoId, CommentRequestDto dto, SessionUserResponseDto sessionUserResponseDto) {


        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new TodoNotFoundException("해당 Todo를 찾을 수 없습니다."));

        Long userId = sessionUserResponseDto.getId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        Comment comment = new Comment(dto.getContents(), todo, user);
        commentRepository.save(comment);

        return CommentResponseDto.toDto(comment);
    }

    public List<CommentResponseDto> findCommentByTodoId(Long todoId) {

        return commentRepository.findAllByTodo_Id(todoId)
                .stream()
                .map(CommentResponseDto::toDto)
                .toList();
    }

    public CommentResponseDto updateComments(
            Long id,
            CommentRequestDto requestDto,
            SessionUserResponseDto sessionUserResponseDto) {

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CommentNotFoundException("해당 Comment를 찾을 수 없습니다.")
        );
        validateCommentOwner(comment,sessionUserResponseDto.getId());
        comment.updateComment(requestDto.getContents());

        return CommentResponseDto.toDto(comment);
    }

    private void validateCommentOwner(Comment comment, Long userId) {
        if (!comment.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("해당 할 일을 수정할 권한이 없습니다.");
        }
    }

    public Long deleteComment(Long id, SessionUserResponseDto sessionUserResponseDto) {

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CommentNotFoundException("해당 Comment를 찾을 수 없습니다.")
        );
        validateCommentOwner(comment,sessionUserResponseDto.getId());
        commentRepository.deleteById(id);

        return 1L;
    }
}
