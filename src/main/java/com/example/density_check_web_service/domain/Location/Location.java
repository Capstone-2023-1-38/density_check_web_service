package com.example.density_check_web_service.domain.Location;

import com.example.density_check_web_service.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Location extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private int distance;
    @Column(nullable = false)
    private int x;
    @Column(nullable = false)
    private int y;

    @Builder
    public Location(Long id, String address, int distance, int x, int y) {
        this.id = id;
        this.address = address;
        this.distance = distance;
        this.x = x;
        this.y = y;
    }
}

