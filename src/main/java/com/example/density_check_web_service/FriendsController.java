package com.example.density_check_web_service;

import com.example.density_check_web_service.domain.Friends.Friends;
import com.example.density_check_web_service.domain.Friends.dto.FriendsListResponseDto;
import com.example.density_check_web_service.service.FriendsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FriendsController {

    private final FriendsService friendsService;

    @GetMapping(path = "/friends/mutual/list")
    public List<FriendsListResponseDto> findMutualByFrom(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        return friendsService.findMutualByFrom(email);
    }

    @GetMapping(path = "/friends/add/list")
    public List<FriendsListResponseDto> findByFrom(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        return friendsService.findByFrom(email);
    }

    @GetMapping(path = "/friends/accept")
    public RedirectView update(@RequestParam String toEmail, Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String fromEmail = oAuth2User.getAttribute("email");

        friendsService.update(fromEmail, toEmail);
        return new RedirectView("/neighbor-add.html");
    }

    @GetMapping(path = "/friends/deny")
    public RedirectView delete(@RequestParam String toEmail, Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String fromEmail = oAuth2User.getAttribute("email");

        friendsService.delete(fromEmail, toEmail);
        return new RedirectView("/neighbor-add.html");
    }

    @GetMapping(path = "/friends/add/count")
    public int count(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        return friendsService.count(email);
    }

    @GetMapping(path = "/friends/add/new")
    public RedirectView save(@RequestParam String toEmail, Authentication authentication, RedirectAttributes ra ) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String fromEmail = oAuth2User.getAttribute("email");
        friendsService.save(fromEmail, toEmail);
        return new RedirectView("/neighbor-add.html");
    }
}
