package com.example.density_check_web_service;

import com.example.density_check_web_service.domain.Posts.dto.PostsListResponseDto;
import com.example.density_check_web_service.domain.Posts.dto.PostsResponseDto;
import com.example.density_check_web_service.domain.Posts.dto.PostsSaveRequestDto;
import com.example.density_check_web_service.domain.Posts.dto.PostsUpdateRequestDto;
import com.example.density_check_web_service.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsController {
    private final PostsService postsService;

    @PostMapping(consumes = "application/x-www-form-urlencoded", path = "/posts/new")
    public RedirectView save(PostsSaveRequestDto requestDto, Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        requestDto.setAuthor(oAuth2User.getAttribute("name"));
        postsService.save(requestDto);
        return new RedirectView("/report-board.html");
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto)
    {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id)
    {

        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id)
    {
        postsService.delete(id);
        return id;
    }

    @GetMapping("/posts/list")
    public List<PostsListResponseDto> findAll()
    {
        return postsService.findAllDesc();
    }

}
