package org.joy.web.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DummyResponseTest {
    @DisplayName("lombok @Getter & @RequiredArgsConstructor 적용 시 정상 동작한다.")
    @Test
    void lombokTest() {
        // given
        String name = "joy";
        int age = 25;

        // when
        DummyResponse dummyResponse = new DummyResponse(name, age);

        // then
        assertThat(dummyResponse.getName()).isEqualTo(name);
        assertThat(dummyResponse.getAge()).isEqualTo(age);
    }
}
