package com.jisu.projectboard.dto.request;

import com.jisu.projectboard.dto.ArticleCommentDto;
import com.jisu.projectboard.dto.UserAccountDto;
import lombok.Data;

@Data
public class ArticleCommentRequest {
    private final Long articleId;
    private final String content;

    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(
                articleId,
                userAccountDto,
                content
        );
    }
}
