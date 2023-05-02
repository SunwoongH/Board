package org.joy.web;

import org.joy.web.dto.response.DummyResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy")
public class DummyController {
    @GetMapping("/string")
    public String dummyString() {
        return "dummy";
    }

    @GetMapping("/json")
    public DummyResponseDto dummyJson(@RequestParam String name,
                                      @RequestParam int age) {
        return new DummyResponseDto(name, age);
    }
}