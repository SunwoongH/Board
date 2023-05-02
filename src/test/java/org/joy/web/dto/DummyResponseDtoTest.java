package org.joy.web.dto;

import org.joy.web.dto.response.DummyResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DummyResponseDtoTest {
    @DisplayName("lombok @Getter & @RequiredArgsConstructor 적용 시 정상 동작한다.")
    @Test
    void lombokTest() {
        // given
        String name = "joy";
        int age = 25;

        // when
        DummyResponseDto dummyResponseDto = new DummyResponseDto(name, age);

        // then
        assertThat(dummyResponseDto.getName()).isEqualTo(name);
        assertThat(dummyResponseDto.getAge()).isEqualTo(age);
    }
}
