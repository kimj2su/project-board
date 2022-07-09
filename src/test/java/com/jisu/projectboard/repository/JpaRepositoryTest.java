package com.jisu.projectboard.repository;

import com.jisu.projectboard.config.JpaConfig;
import com.jisu.projectboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine(){
        //given

        //when
        List<Article> articles = articleRepository.findAll();


        //then
        assertThat(articles).isNotNull().hasSize(25);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine(){
        //given
        long count = articleRepository.count();
        Article article = Article.of("new article", "new content", "#spring");

        //when
        Article savedArticle= articleRepository.save(article);

        //then
        assertThat(articleRepository.count()).isEqualTo(count+1);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine(){
        //given
        Article findArticle = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        findArticle.setHashtag(updatedHashtag);
        //when
        Article savedArticle= articleRepository.saveAndFlush(findArticle);
        //update쿼리 보기위해 flush해줌

        //then
        assertThat(findArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine(){
        //given
        Article findArticle = articleRepository.findById(1L).orElseThrow();
        long articleCount = articleRepository.count();
        long articleCommentCount = articleCommentRepository.count();
        long deletedCommentSize = findArticle.getArticleComments().size();

        //when
        articleRepository.delete(findArticle);

        //then
        assertThat(articleRepository.count()).isEqualTo(articleCount -1);
        assertThat(articleCommentRepository.count()).isEqualTo(articleCommentCount -deletedCommentSize);
    }


}