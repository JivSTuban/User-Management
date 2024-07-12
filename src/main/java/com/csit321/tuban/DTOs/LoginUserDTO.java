package com.csit321.tuban.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDTO {
    @JsonProperty("email")
    private @NonNull String email;

    @JsonProperty("password")
    private @NonNull String password;
}
