package com.example.density_check_web_service;

import com.example.density_check_web_service.config.auth.PrincipalDetails;
import com.example.density_check_web_service.domain.Posts.dto.PostsListResponseDto;
import com.example.density_check_web_service.domain.Posts.dto.PostsResponseDto;
import com.example.density_check_web_service.domain.Posts.dto.PostsSaveRequestDto;
import com.example.density_check_web_service.domain.Posts.dto.PostsUpdateRequestDto;
import com.example.density_check_web_service.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Controller
public class PostsController {
    private final PostsService postsService;

    @PostMapping(consumes = "application/x-www-form-urlencoded", path = "/posts/new")
    public String save(PostsSaveRequestDto requestDto, Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        postsService.save(requestDto, email);

        return "redirect:/report-board";
    }
    @ResponseBody
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto)
    {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/posts/{id}")
    public String findById(@PathVariable Long id, Model model)
    {
        model.addAttribute("post", postsService.findById(id));
        return "report-content";
    }
    @ResponseBody
    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id)
    {
        postsService.delete(id);
        return id;
    }

    @GetMapping("/posts/list")
    public String findAll(@RequestParam(defaultValue = "0") int page, Model model)
    {
        model.addAttribute("paging", postsService.findAllDesc(page));
        return "report-list";
    }

}
