package com.example.density_check_web_service.domain.Location.dto;

import com.example.density_check_web_service.domain.Location.Location;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LocationResponseDto {
    private String address;
    private int distance;
    private int x;
    private int y;
    private LocalDateTime modifiedDate;

    @Builder
    public LocationResponseDto(Location entity) {
        this.address = entity.getPiAddress().getAddress();
        this.distance = entity.getDistance();
        this.x = entity.getX();
        this.y = entity.getY();
        this.modifiedDate = entity.getModifiedDate();
    }
}
