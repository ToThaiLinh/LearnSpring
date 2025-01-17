package com.example.SpringJPA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringJPA.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
