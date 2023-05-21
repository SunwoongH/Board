package org.joy.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DummyController.class)
class DummyControllerTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @DisplayName("/dummy/string GET 요청 시 문자열을 반환한다.")
    @WithMockUser
    @Test
    void dummyStringTest() throws Exception {
        String result = "dummy";
        mockMvc.perform(get("/dummy/string"))
                .andExpect(status().isOk())
                .andExpect(content().string(result));
    }

    @DisplayName("/dummy/json GET 요청 시 json을 반환한다.")
    @WithMockUser
    @Test
    void dummyJsonTest() throws Exception {
        String name = "joy";
        int age = 25;
        mockMvc.perform(get("/dummy/json")
                        .param("name", name)
                        .param("age", String.valueOf(age)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }
}
