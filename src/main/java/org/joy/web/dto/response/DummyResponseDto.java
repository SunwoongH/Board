package org.joy.web.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DummyResponseDto {
    private final String name;
    private final int age;
}
