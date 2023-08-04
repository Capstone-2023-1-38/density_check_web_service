package com.example.density_check_web_service.service;

import com.example.density_check_web_service.domain.PiAddress.PiAddress;
import com.example.density_check_web_service.domain.PiAddress.PiAddressRepository;
import com.example.density_check_web_service.domain.PiAddress.dto.PiAddressResponseDto;
import com.example.density_check_web_service.domain.PiAddress.dto.PiAddressResultResponseDto;
import com.example.density_check_web_service.domain.Users.Users;
import com.example.density_check_web_service.domain.Users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PiAddressService {
    private final UsersRepository usersRepository;
    private final PiAddressRepository piAddressRepository;

    @Transactional
    public List<PiAddressResponseDto> findAll() {

        return piAddressRepository.findAll().stream().map(PiAddressResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public PiAddressResultResponseDto registUser(Long id, String email) {
        if(!piAddressRepository.findByEmail(email).isEmpty()) {
            return new PiAddressResultResponseDto(400, "이미 등록된 사용자 입니다.");
        }
        Users users = usersRepository.findByEmail(email).orElse(null);
        if(users == null) {
            return new PiAddressResultResponseDto(404, "존재하지 않는 사용자 입니다.");
        }
        else {
            PiAddress piAddress = piAddressRepository.findById(id).orElseThrow();
            piAddress.update(users);
            return new PiAddressResultResponseDto(200, "저장되었습니다.");
        }
    }
}
