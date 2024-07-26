package com.example.SpringJPA.dto.request;

import com.example.SpringJPA.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;
    @Size(min = 4, message="PASSWORD_INVALID")
    String password;
    String firstname;
    String lastname;

    @DobConstraint(min = 16, message="INVALID_DOB")
    LocalDate dob;

}
