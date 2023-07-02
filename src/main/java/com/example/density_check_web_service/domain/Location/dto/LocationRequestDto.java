package com.example.density_check_web_service.domain.Location.dto;

import com.example.density_check_web_service.domain.Location.Location;
import lombok.Getter;

@Getter
public class LocationRequestDto {
    private String address;
    private int distance;
    private int x;
    private int y;
    public LocationRequestDto() {}

    public LocationRequestDto(Location entity) {
        this.address = entity.getAddress();
        this.distance = entity.getDistance();
        this.x = entity.getX();
        this.y = entity.getY();
    }

    public Location toEntity() {
        return Location.builder()
                .address(address)
                .distance(distance)
                .x(x)
                .y(y)
                .build();
    }
}
