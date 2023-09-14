package com.example.density_check_web_service.domain.Situations.dto;

import com.example.density_check_web_service.domain.Situations.Situations;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SituationsResponseDto {
    private int loc;
    private int pi;
    private int camera;
    private LocalDateTime createdDate;

    @Builder
    public SituationsResponseDto(Situations entity) {
        this.loc = entity.getLoc();
        this.pi = entity.getPi();
        this.camera = entity.getCamera();
        this.createdDate = entity.getCreatedDate();
    }
}
