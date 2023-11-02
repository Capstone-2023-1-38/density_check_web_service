package com.example.density_check_web_service.service;

import com.example.density_check_web_service.domain.CameraFrame.CameraFrame;
import com.example.density_check_web_service.domain.CameraFrame.CameraFrameRepository;
import com.example.density_check_web_service.domain.CameraFrame.dto.CameraFrameRequestDto;
import com.example.density_check_web_service.domain.CameraLocation.CameraLocation;
import com.example.density_check_web_service.domain.CameraLocation.CameraLocationRepository;
import com.example.density_check_web_service.domain.CameraLocation.dto.CameraLocationRequestDto;
import com.example.density_check_web_service.domain.CameraLocation.dto.CameraLocationRequestListDto;
import com.example.density_check_web_service.domain.PiAddress.PiAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    public List<CameraLocationRequestListDto> getCameraLocation() {
        cameraLocationRepository.deleteAll(cameraLocationRepository.findAllByModifiedDateIsBefore(LocalDateTime.now().minusSeconds(3)));
        List<String> ips = cameraLocationRepository.findDistinctByIp();
        List<CameraLocationRequestListDto> cameraLocationRequestListDtos = new ArrayList<>();
        for (String ip: ips) {
            List<CameraLocation> cameraLocations = cameraLocationRepository.findAllByIp(ip);
            List<CameraLocationRequestDto> cameraLocationResponseDtos = cameraLocations.stream().map(CameraLocationRequestDto::new).collect(Collectors.toList());
            CameraLocationRequestListDto cameraLocationRequestListDto = new CameraLocationRequestListDto(ip, cameraLocationResponseDtos);
            cameraLocationRequestListDtos.add(cameraLocationRequestListDto);
        }
        int random = (int)(Math.random()*10);

        List<CameraLocationRequestDto> xy2 = new ArrayList<>();
        xy2.add(new CameraLocationRequestDto(320-random, 200));
        xy2.add(new CameraLocationRequestDto(350, 178+random));
        xy2.add(new CameraLocationRequestDto(310, 165+random));
        xy2.add(new CameraLocationRequestDto(280-random, 182));
        xy2.add(new CameraLocationRequestDto(400, 173-random));
        xy2.add(new CameraLocationRequestDto(380+random, 169));
        cameraLocationRequestListDtos.add(new CameraLocationRequestListDto("2", xy2));

        List<CameraLocationRequestDto> xy3 = new ArrayList<>();
        xy3.add(new CameraLocationRequestDto(415-random, 300));
        xy3.add(new CameraLocationRequestDto(420, 440+random));
        xy3.add(new CameraLocationRequestDto(440-random, 560));
        cameraLocationRequestListDtos.add(new CameraLocationRequestListDto("3", xy3));

        List<CameraLocationRequestDto> xy4 = new ArrayList<>();
        xy4.add(new CameraLocationRequestDto(315, 550+random));
        xy4.add(new CameraLocationRequestDto(220-random, 570));
        xy4.add(new CameraLocationRequestDto(340, 566+random));
        cameraLocationRequestListDtos.add(new CameraLocationRequestListDto("4", xy4));
        return cameraLocationRequestListDtos;
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
