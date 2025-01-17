package com.example.SpringJPA.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.SpringJPA.dto.request.RoleRequest;
import com.example.SpringJPA.dto.response.RoleResponse;
import com.example.SpringJPA.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest roleRequest);

    RoleResponse toRoleResponse(Role role);
}
