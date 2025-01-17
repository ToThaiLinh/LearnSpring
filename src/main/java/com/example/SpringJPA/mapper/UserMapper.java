package com.example.SpringJPA.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.SpringJPA.dto.request.UserCreationRequest;
import com.example.SpringJPA.dto.request.UserUpdateRequest;
import com.example.SpringJPA.dto.response.UserResponse;
import com.example.SpringJPA.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    //     @Mapping(source = "firstname", target = "lastname")
    UserResponse toUserResponse(User user);

    List<UserResponse> toListUserResponse(List<User> listUser);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
