package org.example.scheduledeveloped.dto.commentDto;

import lombok.Getter;
import org.example.scheduledeveloped.entity.Comment;

/**
 * 클라이언트에게 전달할 댓글 응답 DTO 클래스입니다.
 * 댓글 ID, 내용, 작성자 이름 정보를 포함합니다.
 */
@Getter
public class CommentResponseDto {

    /**
     * 댓글 ID.
     */
    private final Long id;

    /**
     * 댓글 내용.
     */
    private final String contents;

    /**
     * 댓글 작성자 이름.
     */
    private final String userName;

    /**
     * 댓글 응답 DTO 생성자.
     *
     * @param id 댓글 ID
     * @param contents 댓글 내용
     * @param userName 작성자 이름
     */
    public CommentResponseDto(Long id, String contents, String userName) {
        this.id = id;
        this.contents = contents;
        this.userName = userName;
    }

    /**
     * Comment 엔티티를 CommentResponseDto로 변환합니다.
     * 작성자 정보가 없을 경우 "알 수 없음"으로 처리됩니다.
     *
     * @param comment 변환할 댓글 엔티티
     * @return 변환된 응답 DTO
     */
    public static CommentResponseDto toDto(Comment comment) {
        String userName = comment.getUser() != null ? comment.getUser().getUserName() : "알 수 없음";
        return new CommentResponseDto(
                comment.getId(),
                comment.getContents(),
                userName
        );
    }
}
