package com.example.SpringJPA.service;

import com.example.SpringJPA.dto.request.PermissionRequest;
import com.example.SpringJPA.dto.response.PermissionResponse;
import com.example.SpringJPA.entity.Permission;
import com.example.SpringJPA.mapper.PermissionMapper;
import com.example.SpringJPA.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public PermissionResponse update(PermissionResponse request) {
        return null;
    }

    public void delete(String permissionName) {
        permissionRepository.deleteById(permissionName);
    }

}
