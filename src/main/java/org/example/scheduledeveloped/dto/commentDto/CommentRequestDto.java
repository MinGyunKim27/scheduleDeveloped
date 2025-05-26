package org.example.scheduledeveloped.dto.commentDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


/**
 *
 */
@Getter
public class CommentRequestDto {

    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String contents;
}
