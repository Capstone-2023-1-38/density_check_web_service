package com.example.density_check_web_service.domain.Location.dto;

import com.example.density_check_web_service.domain.Location.Location;
import com.example.density_check_web_service.domain.PiAddress.PiAddress;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LocationRequestDto {
    private String address;
    private int distance;
    private int x;
    private int y;

    @Builder
    public LocationRequestDto(String address, int distance, int x, int y) {
        this.address = address;
        this.distance = distance;
        this.x = x;
        this.y = y;
    }

    public Location toEntity(PiAddress piAddress) {
        return Location.builder()
                .piAddress(piAddress)
                .distance(distance)
                .x(x)
                .y(y)
                .build();
    }
}
