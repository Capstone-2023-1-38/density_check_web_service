package com.example.density_check_web_service;

import com.example.density_check_web_service.config.auth.PrincipalDetails;
import com.example.density_check_web_service.domain.Users.dto.UsersResponseDto;
import com.example.density_check_web_service.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class UsersController {
    private final UsersService usersService;

    @ResponseBody
    @GetMapping("/setting/info")
    public UsersResponseDto findByEmail(Authentication authentication) {
        if (authentication == null)
            return new UsersResponseDto("로그인이 필요합니다.");

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        return usersService.findByEmail(email);
    }

    @PostMapping("/setting/image")
    public String updateImage(@Validated @RequestParam("image") MultipartFile file, Authentication authentication) throws Exception {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        usersService.updatePicture(email, file);
        return "redirect:/setting";
    }

    @PostMapping(consumes = "application/x-www-form-urlencoded", path = "/setting/name")
    public String updateName(String name, Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        usersService.updateName(email, name);
        return "redirect:/setting";
    }

    @GetMapping(path = "/updateRole")
    public String updateRole(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        usersService.updateRole(authentication, email);
        System.out.println(Arrays.toString(authentication.getAuthorities().toArray()));
        return "redirect:/";
    }
}
