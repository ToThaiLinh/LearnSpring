package com.example.SpringJPA.service;

import com.example.SpringJPA.dto.request.UserCreationRequest;
import com.example.SpringJPA.dto.request.UserUpdateRequest;
import com.example.SpringJPA.dto.response.UserResponse;
import com.example.SpringJPA.entity.Role;
import com.example.SpringJPA.entity.User;
import com.example.SpringJPA.exception.AppException;
import com.example.SpringJPA.exception.ErrorCode;
import com.example.SpringJPA.mapper.UserMapper;
import com.example.SpringJPA.repository.RoleRepository;
import com.example.SpringJPA.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request) {

        if(userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role roles = roleRepository.findById("USER").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));

        user.setRoles(new HashSet<>(Collections.singleton(roles)));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return userMapper.toListUserResponse(userRepository.findAll());
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String userId) {
        return userMapper.toUserResponse(userRepository.findById(userId).
                orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new RuntimeException("User not found"));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

}
