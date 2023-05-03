package org.joy.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
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
    void postsTest() {
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

    @DisplayName("게시글 등록일 & 수정일이 자동 등록된다.")
    @Test
    void baseTimeEntityTest() {
        // given
        LocalDateTime now = LocalDateTime.of(2023, 5, 3, 3, 0, 0);
        postsRepository.save(Posts.builder()
                .title("안녕")
                .content("하이")
                .author("joy")
                .build());

        // when
        List<Posts> findPostsList = postsRepository.findAll();

        // then
        Posts findPosts = findPostsList.get(0);
        System.out.println(findPosts.getCreatedDate() + ", " + findPosts.getModifiedDate());
        assertThat(findPosts.getCreatedDate()).isAfter(now);
        assertThat(findPosts.getModifiedDate()).isAfter(now);
    }
}
