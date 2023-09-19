package com.example.density_check_web_service.domain.Situations;

import com.example.density_check_web_service.domain.BaseTimeEntity;
import com.example.density_check_web_service.domain.Users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Situations extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int loc;

    @Column(nullable = false)
    private int pi;

    @Column(nullable = false)
    private int camera;


    @Builder
    public Situations(int loc, int pi, int camera) {
        this.loc = loc;
        this.pi = pi;
        this.camera = camera;
    }
}
