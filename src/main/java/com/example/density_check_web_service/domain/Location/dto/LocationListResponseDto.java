package com.example.density_check_web_service.domain.Location.dto;

import com.example.density_check_web_service.domain.Location.Location;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LocationListResponseDto {
    private Long piId;
    private String address;
    private String email;
    @Builder
    public LocationListResponseDto(Location entity) {
        this.piId = entity.getPiAddress().getId();
        this.address = entity.getPiAddress().getAddress();
        this.email = entity.getPiAddress().getUsers().getEmail();
    }
}
