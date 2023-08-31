package com.example.density_check_web_service.domain.Notify;

import com.example.density_check_web_service.domain.BaseTimeEntity;
import com.example.density_check_web_service.domain.Posts.Posts;
import com.example.density_check_web_service.domain.Users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Notify extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Posts posts;

    @Column(nullable = false)
    private Boolean isRead;

    @ManyToOne
    private Users users;

    @Builder
    public Notify(Posts posts, Boolean isRead, Users users) {
        this.posts = posts;
        this.isRead = isRead;
        this.users = users;
    }

}
