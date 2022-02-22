package com.epam.esm.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TagControllerTest {
    private static final String EXPECTED_TAG = "{\"id\":1,\"name\":\"food\",\"_links\":{\"self\":" +
            "{\"href\":\"http://localhost/tag/1?loc=en\"}}}";

    private static final String EXPECTED_ARRAY_TAGS ="[{\"id\":1,\"name\":\"food\",\"links\":[{\"rel\":\"self\"," +
            "\"href\":\"http://localhost/tag/1?loc=en\"}]}]";

    private static final String WIDELY_TAG = "{\"id\":2,\"name\":\"health\",\"_links\":{\"self\":{\"href\":" +
            "\"http://localhost/tag/2?loc=en\"}}}";

    private static final String ADD_BODY ="{\"name\":\"test\"}";
    private static final String EXPECTED_ENTITY_NOT_FOUND = "{\"errorMessage\": \"Entity not found\",\"errorCode\": 40401}";
    private static final String EXPECTED_REDIRECT_URI = "http://localhost/oauth2/authorization/github";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void addTest() throws Exception {
        mockMvc.perform(post("/tag/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ADD_BODY))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void forbiddenAddTest() throws Exception {
        mockMvc.perform(post("/tag/add"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(EXPECTED_REDIRECT_URI))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void findByIdTest() throws Exception {
        mockMvc.perform(get("/tag/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(EXPECTED_TAG))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void negativeFindByIdTest() throws Exception {
        mockMvc.perform(get("/tag/10"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(EXPECTED_ENTITY_NOT_FOUND))
                .andReturn();
    }

    @Test
    void forbiddenFindByIdTest() throws Exception {
        mockMvc.perform(get("/tag/add"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(EXPECTED_REDIRECT_URI))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void findAllTest() throws Exception {
        mockMvc.perform(get("/tag/all")
                        .param("page", "1")
                        .param("page_size", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(EXPECTED_ARRAY_TAGS))
                .andReturn();
    }

    @Test
    void forbiddenFindAllTest() throws Exception {
        mockMvc.perform(get("/tag/all")
                        .param("page", "1")
                        .param("page_size", "1"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(EXPECTED_REDIRECT_URI))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void findMostWidelyUsedTagTest() throws Exception {
        mockMvc.perform(get("/tag/widely/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(WIDELY_TAG))
                .andReturn();
    }

    @Test
    void forbiddenFindMostWidelyUsedTagTest() throws Exception {
        mockMvc.perform(get("/tag/widely/2"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(EXPECTED_REDIRECT_URI))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/tag/4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void forbiddenDeleteTest() throws Exception {
        mockMvc.perform(delete("/tag/4"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(EXPECTED_REDIRECT_URI))
                .andReturn();
    }
}