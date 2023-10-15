package com.example.density_check_web_service.domain.PiAddress.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PiAddressResultResponseDto {
    int code;
    String result;
    String email;

    @Builder
    public PiAddressResultResponseDto(int code, String result, String email) {
        this.code = code;
        this.result = result;
        this.email = email;
    }
}
