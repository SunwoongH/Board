package org.joy.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    void afterEach() {
        postsRepository.deleteAll();
    }

    @DisplayName("게시글을 저장하고 조회한다.")
    @Test
    void postTest() {
        // given
        Posts posts = Posts.builder()
                .title("안녕")
                .content("하이")
                .author("joy")
                .build();
        postsRepository.save(posts);

        // when
        List<Posts> findPostsList = postsRepository.findAll();

        // then
        Posts findPosts = findPostsList.get(0);
        assertThat(findPosts.getTitle()).isEqualTo("안녕");
        assertThat(findPosts.getContent()).isEqualTo("하이");
        assertThat(findPosts.getAuthor()).isEqualTo("joy");
    }
}
