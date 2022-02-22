package com.epam.esm.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    private static final String EXPECTED_REDIRECT_URI = "http://localhost/oauth2/authorization/github";
    private static final String EXPECTED_ACCESS_DENIED_MESSAGE = "{\"headers\":{},\"body\":{\"errorMessage\":" +
            "\"Access denied\",\"errorCode\":40301},\"statusCode\":\"FORBIDDEN\",\"statusCodeValue\":403}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void addTest() throws Exception {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("Tom", "12345"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(post("/order/add")
                        .param("user_id", "2")
                        .param("certificate_id", "1"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void incorrectUserIdAddTest() throws Exception {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("Tom", "12345"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(post("/order/add")
                        .param("user_id", "1")//Tom has id 2
                        .param("certificate_id", "1"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(EXPECTED_ACCESS_DENIED_MESSAGE))
                .andReturn();
    }

    @Test
    void forbiddenAddTest() throws Exception {
        mockMvc.perform(post("/order/add")
                        .param("user_id", "2")
                        .param("certificate_id", "1"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(EXPECTED_REDIRECT_URI))
                .andReturn();
    }
}
