package com.hsynsarsilmaz.microblog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "{username.required}")
    @Size(min = 2, max = 32, message = "{username.size}")
    private String username;

    @NotBlank(message = "{password.required}")
    @Size(min = 8, max = 32, message = "{password.size}")
    private String password;
}