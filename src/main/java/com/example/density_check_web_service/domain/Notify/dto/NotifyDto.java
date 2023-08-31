package com.example.density_check_web_service.domain.Notify.dto;

import com.example.density_check_web_service.domain.Notify.Notify;
import com.example.density_check_web_service.domain.Posts.Posts;
import com.example.density_check_web_service.domain.Posts.dto.PostsResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotifyDto {
    private Long id;
    private String name;
    private PostsResponseDto posts;
    private LocalDateTime createdDate;

    @Builder
    public NotifyDto(Notify notify) {
        this.id = notify.getId();
        this.name = notify.getUsers().getName();
        this.posts = new PostsResponseDto(notify.getPosts());
        this.createdDate = notify.getPosts().getCreatedDate();
    }
}