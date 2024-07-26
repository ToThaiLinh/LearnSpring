package com.example.SpringJPA.configuration;

import com.example.SpringJPA.entity.Role;
import com.example.SpringJPA.entity.User;
import com.example.SpringJPA.repository.RoleRepository;
import com.example.SpringJPA.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;
    UserRepository userRepository;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository,
                                        RoleRepository roleRepository) {
        return args -> {
            // Ensure the ADMIN role exists
            if (roleRepository.findById("ADMIN").isEmpty()) {
                Role adminRole = Role.builder()
                        .name("ADMIN")
                        .description("Admin role")
                        .permissions(new HashSet<>())
                        .build();
                roleRepository.save(adminRole);
                log.info("Admin role has been created");
            }
            if( userRepository.findByUsername("admin").isEmpty()) {
                Optional<Role> roles = roleRepository.findById("ADMIN");

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(new HashSet<>(List.of(roles.get())))
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };

    }

}
