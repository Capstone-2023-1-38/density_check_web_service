package com.example.density_check_web_service;

import com.example.density_check_web_service.domain.Location.dto.LocationResponseDto;
import com.example.density_check_web_service.domain.PiAddress.dto.PiAddressResponseDto;
import com.example.density_check_web_service.domain.PiAddress.dto.PiAddressResultResponseDto;
import com.example.density_check_web_service.service.PiAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PiAddressController {
    private final PiAddressService piAddressService;
    @ResponseBody
    @GetMapping(path = "/piAddress")
    public List<PiAddressResponseDto> findAll() {

        return piAddressService.findAll();
    }

    @ResponseBody
    @PostMapping(path = "/piAddress/userRegist")
    public PiAddressResultResponseDto registUser(Long id, String email) {
        return piAddressService.registUser(id, email);
    }
}
