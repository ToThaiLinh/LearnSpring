package com.example.SpringJPA.controller;

import com.example.SpringJPA.dto.request.UserCreationRequest;
import com.example.SpringJPA.dto.response.UserResponse;
import com.example.SpringJPA.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.HashSet;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private LocalDate dob;

    @BeforeEach
    private void initData() {
        dob = LocalDate.of(2003, 05, 01);
        request = UserCreationRequest.builder()
                .username("linh")
                .password("linh")
                .firstname("To")
                .lastname("Linh")
                .dob(dob)
                .build();
        userResponse = UserResponse.builder()
                .id("f392a6fd2e2c")
                .username("linh")
                .firstname("To")
                .lastname("Linh")
                .dob(dob)
                .roles(new HashSet<>())
                .build();

    }
    @Test
    void createUser_validRequests_success() throws Exception {
        //GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.createUser(ArgumentMatchers.any()))
                        .thenReturn(userResponse);

        //WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("f392a6fd2e2c"))
        ;

    }
    @Test
    void createUser_usernameInvalid_fail() throws Exception {
        //GIVEN
        request.setUsername("li");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

//        Mockito.when(userService.createUser(ArgumentMatchers.any()))
//                        .thenReturn(userResponse);

        //WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be at least 3 characters"))
        ;

    }
}
