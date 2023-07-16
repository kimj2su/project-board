package com.jisu.projectboard.dto.request;

import com.jisu.projectboard.dto.ArticleCommentDto;
import com.jisu.projectboard.dto.UserAccountDto;

public record ArticleCommentRequest(
        Long articleId, String content
) {
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
