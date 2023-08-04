package com.example.density_check_web_service.domain.CameraLocation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@ToString
@Getter
public class CameraLocationResponseDto {
    private double x;
    private double y;

    public CameraLocationResponseDto() {
    }
    @Builder
    public CameraLocationResponseDto(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
