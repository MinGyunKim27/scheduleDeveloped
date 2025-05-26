package org.example.scheduledeveloped.dto.commentDto;

import lombok.Getter;
import org.example.scheduledeveloped.entity.Comment;

/**
 *
 */
@Getter
public class CommentResponseDto {
    private final Long id;

    private final String contents;

    private final String userName;

    public CommentResponseDto(Long id, String contents, String userName) {
        this.id = id;
        this.contents = contents;
        this.userName = userName;
    }

    public static CommentResponseDto toDto(Comment comment) {
        String userName = comment.getUser() != null ? comment.getUser().getUserName() : "알 수 없음";
        return new CommentResponseDto(
                comment.getId(),
                comment.getContents(),
                userName
        );
    }
}
