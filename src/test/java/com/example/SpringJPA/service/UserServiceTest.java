package com.example.SpringJPA.service;

import com.example.SpringJPA.dto.request.UserCreationRequest;
import com.example.SpringJPA.dto.response.UserResponse;
import com.example.SpringJPA.entity.Role;
import com.example.SpringJPA.entity.User;
import com.example.SpringJPA.exception.AppException;
import com.example.SpringJPA.repository.RoleRepository;
import com.example.SpringJPA.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {
    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    private UserCreationRequest request;
    private UserResponse userResponse;
    private User user;
    private Role role;
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

        user = User.builder()
                .id("f392a6fd2e2c")
                .username("linh")
                .firstname("To")
                .lastname("Linh")
                .dob(dob)
                .roles(new HashSet<>())
                .build();

        role = Role.builder()
                .name("USER")
                .description("Role is defined")
                .permissions(new HashSet<>())
                .build();

    }

    @Test
    public void createUser_validRequest_success() {
        //GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(roleRepository.findById(anyString())).thenReturn(Optional.of(role));
        when(userRepository.save(any())).thenReturn(user);

        //WHEN
        var response = userService.createUser(request);

        //THEN
        Assertions.assertThat(response.getId()).isEqualTo("f392a6fd2e2c");
        Assertions.assertThat(response.getUsername()).isEqualTo("linh");

    }
    @Test
    public void createUser_userExisted_fail() {
        //GIVEN
        when(userRepository.existsByUsername(anyString())).thenReturn(true);
        //when(userRepository.save(any())).thenReturn(user);

        //WHEN, THEN
        var exception = assertThrows(AppException.class, () -> userService.createUser(request));

        Assertions.assertThat(exception.getErrorCode().getCode())
                .isEqualTo(1002);
    }

    @Test
    @WithMockUser(username = "linh")
    void getMyInfo_valid_success() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        var response = userService.getMyInfo();

        Assertions.assertThat(response.getUsername()).isEqualTo("linh");
        Assertions.assertThat(response.getId()).isEqualTo("f392a6fd2e2c");
    }
    @Test
    @WithMockUser(username = "linh")
    void getMyInfo_inValid_fail() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(null));

        var exception = assertThrows(AppException.class, () -> userService.getMyInfo());

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);

    }
}
