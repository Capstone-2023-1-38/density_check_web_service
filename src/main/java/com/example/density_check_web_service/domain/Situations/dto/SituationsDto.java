package com.example.density_check_web_service.domain.Situations.dto;

import com.example.density_check_web_service.config.ExcelColumnName;
import com.example.density_check_web_service.domain.Situations.Situations;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SituationsDto {
    @ExcelColumnName(name = "무선 신호 기반 인원 수")
    private int pi;

    @ExcelColumnName(name = "비전 기반 인원 수")
    private int camera;

    @ExcelColumnName(name = "총 인원 수")
    private int cnt;

    @ExcelColumnName(name = "생성 시간")
    private LocalDateTime createdDate;
    @Builder
    public SituationsDto(Situations entity) {
        this.pi = entity.getPi();
        this.camera = entity.getCamera();
        this.cnt = entity.getPi()+entity.getCamera();
        this.createdDate = entity.getCreatedDate();
    }
}
