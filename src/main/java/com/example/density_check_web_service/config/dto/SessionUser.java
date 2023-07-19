package com.example.density_check_web_service.config.dto;

import com.example.density_check_web_service.domain.Users.Users;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;

    public SessionUser(Users users) {
        this.name = users.getName();
        this.email = users.getEmail();
    }
}
