package com.example.SpringJPA.mapper;

import com.example.SpringJPA.dto.request.PermissionRequest;
import com.example.SpringJPA.dto.response.PermissionResponse;
import com.example.SpringJPA.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
     Permission toPermission(PermissionRequest permissionRequest);
     PermissionResponse toPermissionResponse(Permission permission);

}
