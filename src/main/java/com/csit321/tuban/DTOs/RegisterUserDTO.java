package com.csit321.tuban.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO {
    @JsonProperty("email")
    private @NonNull String email;

    @JsonProperty("firstName")
    private @NonNull String firstName;

    @JsonProperty("lastName")
    private @NonNull String lastName;

    @JsonProperty("password")
    private @NonNull String password;

    @JsonProperty("PhoneNumber")
    private @NonNull String PhoneNumber;
}
