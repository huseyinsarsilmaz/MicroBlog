package com.hsynsarsilmaz.microblog.dto;

import com.hsynsarsilmaz.microblog.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserProfile {
    private String username;

    public UserProfile(User user) {
        this.username = user.getUsername();
    }

}
