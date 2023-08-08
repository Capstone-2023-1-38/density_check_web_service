package com.example.density_check_web_service.domain.CameraLocation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class CameraLocationRequestListDto {
    private String ip;
    private List<CameraLocationRequestDto> xy;

    public CameraLocationRequestListDto() {
    }
    @Builder
    public CameraLocationRequestListDto(String ip, List<CameraLocationRequestDto> xy) {
        this.ip = ip;
        this.xy = xy;
    }
}
