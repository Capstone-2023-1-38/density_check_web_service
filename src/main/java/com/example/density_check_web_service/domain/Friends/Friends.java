package com.example.density_check_web_service.domain.Friends;

import com.example.density_check_web_service.domain.Users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Users from;

    @ManyToOne
    private Users to;

    @Column(nullable = false)
    private Boolean mutual;

    @Builder
    public Friends(Users from, Users to, Boolean mutual) {
        this.from = from;
        this.to = to;
        this.mutual = mutual;
    }

    public void update(Boolean mutual) {
        this.mutual = mutual;
    }
}
