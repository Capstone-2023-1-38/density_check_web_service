package com.example.density_check_web_service.service;

import com.example.density_check_web_service.domain.CameraFrame.CameraFrame;
import com.example.density_check_web_service.domain.CameraFrame.CameraFrameRepository;
import com.example.density_check_web_service.domain.CameraFrame.dto.CameraFrameRequestDto;
import com.example.density_check_web_service.domain.CameraLocation.CameraLocation;
import com.example.density_check_web_service.domain.CameraLocation.CameraLocationRepository;
import com.example.density_check_web_service.domain.CameraLocation.dto.CameraLocationRequestDto;
import com.example.density_check_web_service.domain.CameraLocation.dto.CameraLocationRequestListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CameraService {
    private final CameraLocationRepository cameraLocationRepository;
    private final CameraFrameRepository cameraFrameRepository;

    @Transactional
    public void cameraLocation(CameraLocationRequestListDto cameraLocationRequestListDto) {
        cameraLocationRepository.deleteAllByIp(cameraLocationRequestListDto.getIp());
        for (CameraLocationRequestDto xy: cameraLocationRequestListDto.getXy()) {
            cameraLocationRepository.save(new CameraLocation(cameraLocationRequestListDto.getIp(), xy));
        }
    }

    @Transactional
    public List<CameraLocationRequestDto> getCameraLocation() {
        List<CameraLocation> cameraLocations = cameraLocationRepository.findAll();
        List<CameraLocationRequestDto> cameraLocationResponseDtos = cameraLocations.stream().map(CameraLocationRequestDto::new).collect(Collectors.toList());
        return cameraLocationResponseDtos;
    }

    @Transactional
    public void cameraFrame(String ip, byte[] data) {
        CameraFrame cameraFrame = cameraFrameRepository.findByIp(ip).orElse(null);
        if (cameraFrame == null)
            cameraFrameRepository.save(new CameraFrame(ip, data));
        else
            cameraFrame.update(data);
    }

    @Transactional
    public CameraFrameRequestDto getCameraFrame(String ip) throws IOException {
        CameraFrame cameraFrame = cameraFrameRepository.findByIp(ip).orElse(null);
        CameraFrameRequestDto cameraFrameRequestDto = CameraFrameRequestDto.builder().cameraFrame(cameraFrame).build();
//        CameraFrameRequestDto cameraFrameRequestDto = new CameraFrameRequestDto(new CameraFrame(ip, "\00".getBytes()));
        return cameraFrameRequestDto;
    }
}
