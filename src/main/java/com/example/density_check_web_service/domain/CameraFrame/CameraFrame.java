package com.example.density_check_web_service.domain.CameraFrame;

import com.example.density_check_web_service.domain.CameraFrame.dto.CameraFrameRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class CameraFrame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ip;

    @Column(nullable = false, length = 1000)
    private byte[] frame;

    @Builder
    public CameraFrame(String ip, byte[] frame) {
        this.ip = ip;
        this.frame = frame;
    }

    public void update(byte[] frame) {
        this.frame = frame;
    }
}
