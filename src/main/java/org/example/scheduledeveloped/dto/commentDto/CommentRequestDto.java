package org.example.scheduledeveloped.dto.commentDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * 댓글 생성 또는 수정 요청을 처리하는 DTO 클래스입니다.
 * 댓글 내용 입력을 요구하며, 비어 있을 수 없습니다.
 */
@Getter
public class CommentRequestDto {

    /**
     * 댓글 내용 (비어 있으면 안 됨).
     */
    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String contents;
}
