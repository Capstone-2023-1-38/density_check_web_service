package com.example.density_check_web_service.domain.PiAddress.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PiAddressResultResponseDto {
    int code;
    String result;

    @Builder
    public PiAddressResultResponseDto(int code, String result) {
        this.code = code;
        this.result = result;
    }
}
