package com.csit321.tuban.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO {
    @JsonProperty("email")
    @NotBlank(message = "Email is required")
    private String email;

    @JsonProperty("firstName")
    @NotBlank(message = "First name is required")
    private String firstName;

    @JsonProperty("lastName")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @JsonProperty("password")
    @NotBlank(message = "Password is required")
    private String password;

    @JsonProperty("PhoneNumber")
    @NotBlank(message = "Phone number is required")
    private String PhoneNumber;
}
