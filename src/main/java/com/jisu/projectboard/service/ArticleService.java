package com.jisu.projectboard.service;

import com.jisu.projectboard.domain.Article;
import com.jisu.projectboard.domain.type.SearchType;
import com.jisu.projectboard.dto.ArticleDto;
import com.jisu.projectboard.dto.ArticleWithCommentsDto;
import com.jisu.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private Page<ArticleDto> map;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        switch (searchType) {
            case TITLE:
                map = articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from); break;
            case CONTENT:
                map = articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from); break;
            case ID:
                map = articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from); break;
            case NICKNAME:
                map = articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from); break;
            case HASHTAG:
                map = articleRepository.findByHashtag("#" + searchKeyword, pageable).map(ArticleDto::from); break;

        }

        return map;
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public void saveArticle(ArticleDto dto) {
        articleRepository.save(dto.toEntity());

    }

    public void updateArticle(ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.getId());
            if (dto.getTitle() != null) {
                article.setTitle(dto.getTitle());
            }
            if (dto.getContent() != null) {
                article.setContent(dto.getContent());
            }
            article.setHashtag(dto.getHashtag());
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다. -dto: {}", dto);
        }
    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);
    }

    public long getArticleCount() {
        return articleRepository.count();
    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        if (hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }

        return articleRepository.findByHashtag(hashtag, pageable).map(ArticleDto::from);

    }

    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtags();
    }
}
