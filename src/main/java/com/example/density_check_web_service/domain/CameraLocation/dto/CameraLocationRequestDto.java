package com.example.density_check_web_service.domain.CameraLocation.dto;

import com.example.density_check_web_service.domain.CameraLocation.CameraLocation;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class CameraLocationRequestDto {
    private int x;
    private int y;

    public CameraLocationRequestDto() {

    }

    @Builder
    public CameraLocationRequestDto(CameraLocation cameraLocation) {
        this.x = cameraLocation.getX();
        this.y = cameraLocation.getY();
    }
}

