package com.example.density_check_web_service;

import com.example.density_check_web_service.domain.Users.dto.UsersResponseDto;
import com.example.density_check_web_service.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UsersController {
    private final UsersService usersService;

    @ResponseBody
    @GetMapping("/setting/name")
    public UsersResponseDto findByEmail(Authentication authentication) {
        if (authentication == null)
            return new UsersResponseDto("로그인이 필요합니다.");

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        return usersService.findByEmail(email);
    }

    @PostMapping(consumes = "application/x-www-form-urlencoded", path = "/setting/name")
    public String updateName(String name, Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        usersService.updateName(email, name);
        return "redirect:/setting";
    }
}
