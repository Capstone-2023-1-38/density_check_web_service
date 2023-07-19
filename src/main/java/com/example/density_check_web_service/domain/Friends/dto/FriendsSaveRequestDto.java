package com.example.density_check_web_service.domain.Friends.dto;

import com.example.density_check_web_service.domain.Friends.Friends;
import com.example.density_check_web_service.domain.Users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FriendsSaveRequestDto {
    private Users from;
    private Users to;
    private Boolean mutual;
    @Builder
    public FriendsSaveRequestDto(Users from, Users to, Boolean mutual) {
        this.from = from;
        this.to = to;
        this.mutual = mutual;
    }

    public Friends toEntity() {
        return Friends.builder()
                .from(from)
                .to(to)
                .mutual(false)
                .build();
    }
}
