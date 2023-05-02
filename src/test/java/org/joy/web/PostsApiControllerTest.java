package org.joy.web;

import org.joy.domain.posts.Posts;
import org.joy.domain.posts.PostsRepository;
import org.joy.web.dto.request.PostsSaveRequestDto;
import org.joy.web.dto.request.PostsUpdateRequestDto;
import org.joy.web.dto.response.PostsResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {
    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    void afterEach() {
        postsRepository.deleteAll();
    }

    @DisplayName("게시글이 정상적으로 등록된다.")
    @Test
    void postsRegisterTest() {
        // given
        String title = "안녕";
        String content = "하이";
        String author = "joy";
        PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
        String url = "http://localhost:" + port + "/api/posts";

        // when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, postsSaveRequestDto, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Posts> findPostsList = postsRepository.findAll();
        Posts findPosts = findPostsList.get(0);
        assertThat(findPosts.getTitle()).isEqualTo(title);
        assertThat(findPosts.getContent()).isEqualTo(content);
        assertThat(findPosts.getAuthor()).isEqualTo(author);
    }

    @DisplayName("게시글이 정상적으로 수정된다.")
    @Test
    void postsUpdateTest() {
        // given
        Posts savedPosts = postsRepository.save(Posts.builder().title("안녕")
                .content("하이")
                .author("joy")
                .build());
        Long id = savedPosts.getId();
        String title = "안녕녕";
        String content = "하이이";
        PostsUpdateRequestDto postsUpdateRequestDto = PostsUpdateRequestDto.builder()
                .title(title)
                .content(content)
                .build();
        String url = "http://localhost:" + port + "/api/posts/" + id;
        HttpEntity<PostsUpdateRequestDto> httpEntity = new HttpEntity<>(postsUpdateRequestDto);

        // when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Long.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<Posts> findPostsList = postsRepository.findAll();
        Posts findPosts = findPostsList.get(0);
        assertThat(findPosts.getTitle()).isEqualTo(title);
        assertThat(findPosts.getContent()).isEqualTo(content);
    }

    @DisplayName("게시글이 정상적으로 조회된다.")
    @Test
    void postsFindByIdTest() {
        // given
        String title = "안녕";
        String content = "하이";
        String author = "joy";
        Posts savedPosts = Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
        Long id = postsRepository.save(savedPosts).getId();
        String url = "http://localhost:" + port + "/api/posts/" + id;

        // when
        ResponseEntity<PostsResponseDto> responseEntity = restTemplate.getForEntity(url, PostsResponseDto.class);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getId()).isEqualTo(id);
    }
}
