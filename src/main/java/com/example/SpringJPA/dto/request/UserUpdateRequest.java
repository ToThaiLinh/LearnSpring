package com.example.SpringJPA.dto.request;

import com.example.SpringJPA.entity.Role;
import com.example.SpringJPA.validator.DobConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
        String password;
        String firstname;
        String lastname;

        @DobConstraint(min = 18, message = "INVALID_DOB")
        LocalDate dob;

        List<String> roles;
}
