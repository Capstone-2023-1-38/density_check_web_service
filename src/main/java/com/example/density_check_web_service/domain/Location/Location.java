package com.example.density_check_web_service.domain.Location;

import com.example.density_check_web_service.domain.BaseTimeEntity;
import com.example.density_check_web_service.domain.PiAddress.PiAddress;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Location extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private PiAddress piAddress;
    @Column(nullable = false)
    private int distance;
    @Column(nullable = false)
    private int x;
    @Column(nullable = false)
    private int y;

    @Builder
    public Location(PiAddress piAddress, int distance, int x, int y) {
        this.piAddress = piAddress;
        this.distance = distance;
        this.x = x;
        this.y = y;
    }
}

