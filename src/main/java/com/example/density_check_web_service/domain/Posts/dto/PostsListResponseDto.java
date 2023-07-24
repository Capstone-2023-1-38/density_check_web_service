package com.example.density_check_web_service.domain.Posts.dto;

import com.example.density_check_web_service.domain.Posts.Posts;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class PostsListResponseDto {
    private Long id;
    private String location;
    private String title;
    private String author;
    private LocalDateTime createdDate;

    @Builder
    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.location = entity.getLocation();
        this.title = entity.getTitle();
        this.author = entity.getAuthor().getName();
        this.createdDate = entity.getCreatedDate();
    }
}
