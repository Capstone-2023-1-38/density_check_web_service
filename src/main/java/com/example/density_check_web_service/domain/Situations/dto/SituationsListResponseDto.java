package com.example.density_check_web_service.domain.Situations.dto;

import com.example.density_check_web_service.domain.Situations.Situations;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class SituationsListResponseDto {
    private int loc;
    private List<SituationsDto> situationsDtos;

    @Builder
    public SituationsListResponseDto(int loc, List<Situations> entity) {
        this.loc = loc;
        this.situationsDtos = entity.stream().map(SituationsDto::new).collect(Collectors.toList());
    }

    @Getter
    public class SituationsDto {
        private int cnt;
        private LocalDateTime createdDate;
        public SituationsDto(Situations entity) {
            this.cnt = entity.getPi()+entity.getCamera();
            this.createdDate = entity.getCreatedDate();
        }

    }

}
