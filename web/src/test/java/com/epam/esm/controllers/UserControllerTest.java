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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    private static final String EXPECTED_USER = "{\"id\":1,\"name\":\"Jack\",\"_links\":{\"self\":{\"href\":" +
            "\"http://localhost/user/1?loc=en\"}}}";

    private static final String EXPECTED_ARRAY_USERS = "[{\"id\":1,\"name\":\"Jack\",\"links\":[{\"rel\":\"self\"," +
            "\"href\":\"http://localhost/user/1?loc=en\"}]}]";

    private static final String EXPECTED_ORDER = "{\"id\":2,\"cost\":40.00,\"createDate\":\"2021-12-25T10:00:00.000Z\"" +
            ",\"user\":{\"id\":2,\"name\":\"Tom\"},\"giftCertificate\":{\"id\":2,\"name\":\"spa\",\"description\":" +
            "\"relax\",\"price\":40.00,\"duration\":30,\"createDay\":\"2021-01-10T14:00:00.000Z\",\"lastUpdateDay\":" +
            "\"2021-01-10T14:00:00.000Z\",\"tags\":[{\"id\":2,\"name\":\"health\"},{\"id\":3,\"name\":\"beauty\"}]}," +
            "\"_links\":{\"self\":{\"href\":\"http://localhost/user/2?loc=en\"}}}";

    private static final String EXPECTED_ARRAY_ORDERS = "[{\"id\":2,\"cost\":40.00,\"createDate\":" +
            "\"2021-12-25T10:00:00.000Z\",\"user\":{\"id\":2,\"name\":\"Tom\",\"links\":[{\"rel\":\"self\",\"href\":" +
            "\"http://localhost/user/2?loc=en\"}]},\"giftCertificate\":{\"id\":2,\"name\":\"spa\",\"description\":" +
            "\"relax\",\"price\":40.00,\"duration\":30,\"createDay\":\"2021-01-10T14:00:00.000Z\",\"lastUpdateDay\":" +
            "\"2021-01-10T14:00:00.000Z\",\"tags\":[{\"id\":2,\"name\":\"health\",\"links\":[{\"rel\":\"self\"," +
            "\"href\":\"http://localhost/tag/2?loc=en\"}]},{\"id\":3,\"name\":\"beauty\",\"links\":[{\"rel\":\"self\"," +
            "\"href\":\"http://localhost/tag/3?loc=en\"}]}],\"links\":[{\"rel\":\"self\",\"href\":" +
            "\"http://localhost/certificate/2?loc=en\"}]},\"links\":[{\"rel\":\"self\",\"href\":" +
            "\"http://localhost/user/2?loc=en\"}]}]";

    private static final String EXPECTED_ACCESS_DENIED_MESSAGE = "{\"headers\":{},\"body\":{\"errorMessage\":" +
            "\"Access denied\",\"errorCode\":40301},\"statusCode\":\"FORBIDDEN\",\"statusCodeValue\":403}";

    private static final String EXPECTED_ENTITY_NOT_FOUND = "{\"errorMessage\": \"Entity not found\",\"errorCode\": 40401}";
    private static final String EXPECTED_REDIRECT_URI = "http://localhost/oauth2/authorization/github";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void findByIdTest() throws Exception {
        mockMvc.perform(get("/user/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(EXPECTED_USER))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void incorrectRoleFindByIdTest() throws Exception {
        mockMvc.perform(get("/user/1"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(EXPECTED_ACCESS_DENIED_MESSAGE))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    void findAllTest() throws Exception {
        mockMvc.perform(get("/user/all")
                        .param("page", "1")
                        .param("page_size", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(EXPECTED_ARRAY_USERS))
                .andReturn();
    }

    @Test
    void forbiddenFindAllTest() throws Exception {
        mockMvc.perform(get("/user/all")
                        .param("page", "1")
                        .param("page_size", "1"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(EXPECTED_REDIRECT_URI))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void findUserOrderByIdTest() throws Exception {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("Tom", "12345"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(get("/user/2/order/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(EXPECTED_ORDER))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void incorrectUserIdFindUserOrderByIdTest() throws Exception {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("Tom", "12345"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(get("/user/1/order/2"))//Tom has id 2
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().json(EXPECTED_ACCESS_DENIED_MESSAGE))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void incorrectOrderIdFindUserOrderByIdTest() throws Exception {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("Tom", "12345"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(get("/user/2/order/1"))//Tom doesn't have order with id 1
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().json(EXPECTED_ENTITY_NOT_FOUND))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void findUserOrdersTest() throws Exception {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("Tom", "12345"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(get("/user/2/orders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(EXPECTED_ARRAY_ORDERS))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ROLE_USER")
    void incorrectUserIdFindUserOrdersTest() throws Exception {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("Tom", "12345"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        mockMvc.perform(get("/user/1/orders"))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().json(EXPECTED_ACCESS_DENIED_MESSAGE))
                .andReturn();
    }

    @Test
    void forbiddenFindUserOrdersTest() throws Exception {
             mockMvc.perform(get("/user/2/orders"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(EXPECTED_REDIRECT_URI))
                .andReturn();
    }
}
