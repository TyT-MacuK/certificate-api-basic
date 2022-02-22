package com.epam.esm.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {
    private static final String EXPECTED_MESSAGE_USER_NOT_FOUND = "{\"errorMessage\":\"User is not found\"," +
            "\"errorCode\":40401}";

    private static final String EXPECTED_MESSAGE_ENTITY_EXIST = "{\"errorMessage\":\"This entity already exists\"," +
            "\"errorCode\":40901}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginTest() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Tom\",\"password\": \"12345\"}"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void incorrectNameLoginTest() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Tooooom\",\"password\": \"12345\"}"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json(EXPECTED_MESSAGE_USER_NOT_FOUND))
                .andReturn();
    }

    @Test
    void incorrectPasswordLoginTest() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Tom\",\"password\": \"54321\"}"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json(EXPECTED_MESSAGE_USER_NOT_FOUND))
                .andReturn();
    }

    @Test
    void signUpTest() throws Exception {
        mockMvc.perform(post("/auth/sign_up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Mia\",\"email\": \"Mia@gmail.com\",\"password\": \"12345\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void entityExistsSignUpTest() throws Exception {
        mockMvc.perform(post("/auth/sign_up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Tom\",\"email\": \"tom@gmail.com\",\"password\": \"12345\"}"))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().json(EXPECTED_MESSAGE_ENTITY_EXIST))
                .andReturn();
    }
}
