package org.joy.web;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IndexControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @DisplayName("웰컴 페이지가 로딩된다.")
    @Test
    void indexPageTest() {
        // given
        String title = "게시판판판";

        // when
        String body = this.restTemplate.getForObject("/", String.class);

        // then
        Assertions.assertThat(body).contains(title);
    }
}
