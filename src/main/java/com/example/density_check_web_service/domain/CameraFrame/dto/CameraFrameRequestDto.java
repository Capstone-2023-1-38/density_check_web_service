package com.example.density_check_web_service.domain.CameraFrame.dto;

import com.example.density_check_web_service.domain.CameraFrame.CameraFrame;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@ToString
@Getter
public class CameraFrameRequestDto {
    private String ip;
    private byte[] frame;

    public CameraFrameRequestDto() {

    }

    @Builder
    public CameraFrameRequestDto(CameraFrame cameraFrame) throws IOException {
        this.ip = cameraFrame.getIp();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write("--frame\r\n Content-Type: image/jpeg\r\n\r\n".getBytes());
        outputStream.write(cameraFrame.getFrame());
        outputStream.write("\r\n".getBytes());
        byte[] result = outputStream.toByteArray();
        this.frame = "\r\n".getBytes();
    }
}
