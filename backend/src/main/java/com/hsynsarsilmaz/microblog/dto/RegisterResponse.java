package com.hsynsarsilmaz.microblog.dto;

import com.hsynsarsilmaz.microblog.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterResponse {
    private String email;

    public RegisterResponse(User user) {
        email = user.getUsername();
    }

}
