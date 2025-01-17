package com.example.SpringJPA.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.SpringJPA.dto.request.ApiResponse;
import com.example.SpringJPA.dto.request.PermissionRequest;
import com.example.SpringJPA.dto.response.PermissionResponse;
import com.example.SpringJPA.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse delete(@PathVariable("permission") String permission) {
        permissionService.delete(permission);
        return ApiResponse.builder().result("Permission has been deleted").build();
    }
}
