package com.example.SpringJPA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.SpringJPA.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
