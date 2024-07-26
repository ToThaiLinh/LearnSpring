package com.example.SpringJPA.service;

import com.example.SpringJPA.dto.request.RoleRequest;
import com.example.SpringJPA.dto.response.RoleResponse;
import com.example.SpringJPA.mapper.RoleMapper;
import com.example.SpringJPA.repository.PermissionRepository;
import com.example.SpringJPA.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest roleRequest) {
        var role = roleMapper.toRole(roleRequest);
        var permissions = permissionRepository.findAllById(roleRequest.getPermissions());

        role.setPermissions(new HashSet<>(permissions));

        return roleMapper.toRoleResponse(roleRepository.save(role));

    }

    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    public RoleResponse getRole(String roleName) {
        var role = roleRepository.findById(roleName);
        return role.map(roleMapper::toRoleResponse).orElse(null);
    }

    public void delete(String roleName) {
        roleRepository.deleteById(roleName);
    }
}
