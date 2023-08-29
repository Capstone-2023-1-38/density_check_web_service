package com.example.density_check_web_service;

import com.example.density_check_web_service.domain.CameraFrame.dto.CameraFrameRequestDto;
import com.example.density_check_web_service.domain.CameraLocation.dto.CameraLocationRequestDto;
import com.example.density_check_web_service.domain.CameraLocation.dto.CameraLocationRequestListDto;
import com.example.density_check_web_service.service.CameraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CameraController {
    private final CameraService cameraService;
    @ResponseBody
    @PostMapping(path = "/cameraLocation")
    public ResponseEntity<?> cameraLocation(@RequestBody CameraLocationRequestListDto cameraLocationRequestListDto) {
        cameraService.cameraLocation(cameraLocationRequestListDto);
        return ResponseEntity.ok(null);
    }

    @ResponseBody
    @GetMapping(path = "/getCameraLocation")
    public List<CameraLocationRequestDto> getCameraLocation() {
        return cameraService.getCameraLocation();
    }

    @ResponseBody
    @PostMapping(path = "/cameraFrame/{ip}")
    public ResponseEntity<?> cameraFrame(@PathVariable String ip, @RequestBody byte[] framedata) {
        cameraService.cameraFrame(ip, framedata);
        return ResponseEntity.ok(null);
    }

    @ResponseBody
    @GetMapping(path = "/cameraFrame/{ip}")
    public CameraFrameRequestDto getCameraFrame(@PathVariable String ip) throws IOException {
        CameraFrameRequestDto cameraFrameRequestDto = cameraService.getCameraFrame(ip);
        return cameraFrameRequestDto;
    }
}
