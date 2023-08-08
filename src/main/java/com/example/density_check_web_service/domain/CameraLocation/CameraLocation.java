package com.example.density_check_web_service.domain.CameraLocation;

import com.example.density_check_web_service.domain.CameraLocation.dto.CameraLocationRequestDto;
import com.example.density_check_web_service.domain.PiAddress.PiAddress;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class CameraLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ip;

    @Column(nullable = false)
    private int x;

    @Column(nullable = false)
    private int y;

    @Builder
    public CameraLocation(String ip, CameraLocationRequestDto cameraLocationRequestDto) {
        this.ip = ip;
        this.x = cameraLocationRequestDto.getX();
        this.y = cameraLocationRequestDto.getY();
    }
}
