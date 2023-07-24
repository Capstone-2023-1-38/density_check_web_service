package com.example.density_check_web_service.domain.Users.dto;

import com.example.density_check_web_service.domain.Users.Users;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UsersResponseDto {
    private Long id;
    private String name;
    private String picture;

    @Builder
    public UsersResponseDto(Users entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.picture = entity.getPicture();
    }

    public UsersResponseDto(String name) {
        this.id = -1L;
        this.name = name;
        this.picture = null;
    }
}
