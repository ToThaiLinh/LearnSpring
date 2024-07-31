package com.example.SpringJPA.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.SpringJPA.dto.request.ApiResponse;
import com.example.SpringJPA.dto.request.RoleRequest;
import com.example.SpringJPA.dto.response.RoleResponse;
import com.example.SpringJPA.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @GetMapping("/{roleName}")
    ApiResponse<RoleResponse> getRole(@PathVariable("roleName") String roleName) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.getRole(roleName))
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse delete(@PathVariable("role") String role) {
        roleService.delete(role);
        return ApiResponse.builder().result("Role has been deleted").build();
    }
}
