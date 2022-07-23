package com.jisu.projectboard.dto.response;

import com.jisu.projectboard.dto.ArticleCommentDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleCommentResponse {

    private final Long id;
    private final String content;
    private final LocalDateTime createdAt;
    private final String email;
    private final String nickname;
    private final String userId;


    public static ArticleCommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname, String userId) {
        return new ArticleCommentResponse(id, content, createdAt, email, nickname, userId);
    }

    public static ArticleCommentResponse from(ArticleCommentDto dto) {

        String nickname = dto.getUserAccountDto().getNickname();

        if (nickname == null || nickname.isBlank()) {
            nickname = dto.getUserAccountDto().getUserId();
        }

        return new ArticleCommentResponse(
                dto.getId(),
                dto.getContent(),
                dto.getCreatedAt(),
                dto.getUserAccountDto().getEmail(),
                nickname,
                dto.getUserAccountDto().getUserId()
        );
    }
}
