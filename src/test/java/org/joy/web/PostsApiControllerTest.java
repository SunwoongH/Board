package org.joy.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.joy.domain.posts.Posts;
import org.joy.domain.posts.PostsRepository;
import org.joy.web.dto.request.PostsSaveRequestDto;
import org.joy.web.dto.request.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PostsApiControllerTest {
    @LocalServerPort
    int port;
    @Autowired
    WebApplicationContext webApplicationContext;
    MockMvc mockMvc;
    @Autowired
    PostsRepository postsRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    void afterEach() {
        postsRepository.deleteAll();
    }

    @DisplayName("게시글이 등록된다.")
    @WithMockUser
    @Test
    void postsSaveTest() throws Exception {
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
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postsSaveRequestDto)))
                .andExpect(status().isOk());

        // then
        List<Posts> findPostsList = postsRepository.findAll();
        Posts findPosts = findPostsList.get(0);
        assertThat(findPosts.getTitle()).isEqualTo(title);
        assertThat(findPosts.getContent()).isEqualTo(content);
        assertThat(findPosts.getAuthor()).isEqualTo(author);
    }

    @DisplayName("게시글이 수정된다.")
    @WithMockUser
    @Test
    void postsUpdateTest() throws Exception {
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

        // when
        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postsUpdateRequestDto)))
                .andExpect(status().isOk());

        // then
        List<Posts> findPostsList = postsRepository.findAll();
        Posts findPosts = findPostsList.get(0);
        assertThat(findPosts.getTitle()).isEqualTo(title);
        assertThat(findPosts.getContent()).isEqualTo(content);
    }

    @DisplayName("게시글이 조회된다.")
    @WithMockUser
    @Test
    void postsFindByIdTest() throws Exception {
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
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.author").value(author));
    }
}
