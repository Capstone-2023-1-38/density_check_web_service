package com.example.density_check_web_service.domain.PiAddress.dto;

import com.example.density_check_web_service.domain.PiAddress.PiAddress;
import lombok.Builder;
import lombok.Getter;
@Getter
public class PiAddressResponseDto {
    private Long id;
    private String address;
    private String email;
    @Builder
    public PiAddressResponseDto(PiAddress entity) {
        this.id = entity.getId();
        this.address = entity.getAddress();
        if(entity.getUsers() != null) {
            this.email = entity.getUsers().getEmail();
        }
        else {
            this.email = null;
        }
    }
}
