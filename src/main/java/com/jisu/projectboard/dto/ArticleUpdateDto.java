package com.jisu.projectboard.dto;

import lombok.Data;


@Data
public class ArticleUpdateDto {

    private final String title;
    private final String content;
    private final String hashtag;

    public static ArticleUpdateDto of(String title, String content, String hashtag) {
        return new ArticleUpdateDto(title, content, hashtag);
    }
}
