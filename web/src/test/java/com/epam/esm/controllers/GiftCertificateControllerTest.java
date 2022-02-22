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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class GiftCertificateControllerTest {
    private static final String EXPECTED_CERTIFICATE = "{\"id\":1,\"name\":\"photosession\",\"description\":" +
            "\"beautiful photos on memory\",\"price\":10.50,\"duration\":5,\"createDay\":\"2021-12-01T12:00:00.000Z\"" +
            ",\"lastUpdateDay\":\"2021-12-01T12:00:00.000Z\",\"tags\":[],\"_links\":{\"self\":" +
            "{\"href\":\"http://localhost/certificate/1?loc=en\"}}}";

    private static final String EXPECTED_ARRAY_CERTIFICATES = "[{\"id\":1,\"name\":\"photosession\",\"description\":" +
            "\"beautiful photos on memory\",\"price\":10.50,\"duration\":5,\"createDay\":\"2021-12-01T12:00:00.000Z\"," +
            "\"lastUpdateDay\":\"2021-12-01T12:00:00.000Z\",\"tags\":[],\"links\":[{\"rel\":\"self\"," +
            "\"href\":\"http://localhost/certificate/1?loc=en\"}]}]";

    private static final String ADD_BODY = "{\"name\": \"dance\",\"description\": \"learn dance\",\"price\": \"30\"" +
            ",\"duration\": \"30\"}";

    private static final String EXPECTED_ENTITY_NOT_FOUND = "{\"errorMessage\": \"Entity not found\",\"errorCode\": 40401}";
    private static final String EXPECTED_REDIRECT_URI = "http://localhost/oauth2/authorization/github";
    private static final String UPDATE_BODY = "{\"name\": \"test_name\"}";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void addTest() throws Exception {
        mockMvc.perform(post("/certificate/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ADD_BODY))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void forbiddenAddTest() throws Exception {
        mockMvc.perform(post("/certificate/add"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(EXPECTED_REDIRECT_URI))
                .andReturn();
    }

    @Test
    void findByIdTest() throws Exception {
        mockMvc.perform(get("/certificate/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(EXPECTED_CERTIFICATE))
                .andReturn();
    }

    @Test
    void negativeFindByIdTest() throws Exception {
        mockMvc.perform(get("/certificate/10"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(EXPECTED_ENTITY_NOT_FOUND))
                .andReturn();
    }

    @Test
    void findAllTest() throws Exception {
        mockMvc.perform(get("/certificate/all")
                        .param("page", "1")
                        .param("page_size", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(EXPECTED_ARRAY_CERTIFICATES))
                .andReturn();
    }

    @Test
    void sortTest() throws Exception {
        mockMvc.perform(get("/certificate/sort")
                        .param("certificate_name", "photosession")
                        .param("order_by_name", "asc")
                        .param("page", "1")
                        .param("page_size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(EXPECTED_ARRAY_CERTIFICATES))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void updateTest() throws Exception {
        mockMvc.perform(patch("/certificate/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UPDATE_BODY))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void forbiddenUpdateTest() throws Exception {
        mockMvc.perform(patch("/certificate/1"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(EXPECTED_REDIRECT_URI))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/certificate/3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void forbiddenDeleteTest() throws Exception {
        mockMvc.perform(delete("/certificate/3"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(EXPECTED_REDIRECT_URI))
                .andReturn();
    }
}