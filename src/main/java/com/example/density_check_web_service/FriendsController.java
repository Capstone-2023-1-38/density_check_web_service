package com.example.density_check_web_service;

import com.example.density_check_web_service.domain.Friends.Friends;
import com.example.density_check_web_service.domain.Friends.dto.FriendsListResponseDto;
import com.example.density_check_web_service.service.FriendsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class FriendsController {

    private final FriendsService friendsService;

    @ResponseBody
    @GetMapping(path = "/friends/mutual/list")
    public List<FriendsListResponseDto> findMutualByFrom(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        return friendsService.findMutualByFrom(email);
    }

    @ResponseBody
    @GetMapping(path = "/friends/add/list")
    public List<FriendsListResponseDto> findByFrom(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        return friendsService.findByFrom(email);
    }

    @GetMapping(path = "/friends/accept")
    public String update(@RequestParam String toEmail, Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String fromEmail = oAuth2User.getAttribute("email");

        friendsService.update(fromEmail, toEmail);
        return "redirect:/neighbor-add.html";
    }

    @GetMapping(path = "/friends/deny")
    public String delete(@RequestParam String toEmail, Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String fromEmail = oAuth2User.getAttribute("email");

        friendsService.delete(fromEmail, toEmail);
        return "redirect:/neighbor-add.html";
    }

    @ResponseBody
    @GetMapping(path = "/friends/add/count")
    public int count(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        return friendsService.count(email);
    }

    @GetMapping(path = "/friends/add/new")
    public String save(@RequestParam String toEmail, Authentication authentication, RedirectAttributes redirectAttributes) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String fromEmail = oAuth2User.getAttribute("email");
        redirectAttributes.addAttribute("result", friendsService.save(fromEmail, toEmail));

        return "redirect:/neighbor-add.html";
    }
}
