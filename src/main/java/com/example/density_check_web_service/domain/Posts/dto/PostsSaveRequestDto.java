package com.example.density_check_web_service.domain.Posts.dto;

import com.example.density_check_web_service.domain.Posts.Posts;
import com.example.density_check_web_service.domain.Users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String location;
    private String title;
    private String content;
    private Users author;

    @Builder
    public PostsSaveRequestDto(String location, String title, String content, Users author) {
        this.location = location;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .location(location)
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

}
