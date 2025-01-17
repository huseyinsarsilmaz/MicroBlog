package com.hsynsarsilmaz.microblog.dto;

import com.hsynsarsilmaz.microblog.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterResponse {
    private String username;

    public RegisterResponse(User user) {
        username = user.getUsername();
    }

}
