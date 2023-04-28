package org.joy.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DummyResponse {
    private final String name;
    private final int age;
}
