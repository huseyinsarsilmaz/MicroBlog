package com.hsynsarsilmaz.microblog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "{username.invalid.characters}")
    private String username;

    @NotBlank(message = "{password.required}")
    @Size(min = 8, max = 32, message = "{password.size}")
    private String password;
}