package com.example.SpringJPA.repository;

import com.example.SpringJPA.dto.response.RoleResponse;
import com.example.SpringJPA.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}