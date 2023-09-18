package com.example.density_check_web_service.domain.Friends.dto;

import com.example.density_check_web_service.domain.Friends.Friends;
import com.example.density_check_web_service.domain.Users.Users;
import lombok.Getter;

@Getter
public class FriendsListResponseDto {
    private Long id;
    private String toName;
    private String toEmail;
    private String toPicture;

    public FriendsListResponseDto(Friends entity) {
        this.id = entity.getId();
        this.toName = entity.getTo().getName();
        this.toEmail = entity.getTo().getEmail();
        this.toPicture = entity.getTo().getPicture();
    }
}
