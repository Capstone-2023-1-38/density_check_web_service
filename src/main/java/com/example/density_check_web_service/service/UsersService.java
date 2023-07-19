package com.example.density_check_web_service.service;

import com.example.density_check_web_service.domain.Users.Users;
import com.example.density_check_web_service.domain.Users.UsersRepository;
import com.example.density_check_web_service.domain.Users.dto.UsersResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Transactional
    public UsersResponseDto findByEmail(String email) {
        Users users = usersRepository.findByEmail(email).orElseThrow();
        return new UsersResponseDto(users);
    }
    @Transactional
    public void updateName(String email, String name) {
        Users users = usersRepository.findByEmail(email).orElseThrow();
        users.update(name);
    }
}
