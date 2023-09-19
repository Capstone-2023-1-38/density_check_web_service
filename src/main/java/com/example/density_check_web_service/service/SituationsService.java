package com.example.density_check_web_service.service;

import com.example.density_check_web_service.config.ExcelColumnName;
import com.example.density_check_web_service.domain.CameraFrame.CameraFrame;
import com.example.density_check_web_service.domain.CameraFrame.CameraFrameRepository;
import com.example.density_check_web_service.domain.CameraLocation.CameraLocation;
import com.example.density_check_web_service.domain.CameraLocation.CameraLocationRepository;
import com.example.density_check_web_service.domain.Location.Location;
import com.example.density_check_web_service.domain.Location.LocationRepository;
import com.example.density_check_web_service.domain.PiAddress.PiAddress;
import com.example.density_check_web_service.domain.PiAddress.PiAddressRepository;
import com.example.density_check_web_service.domain.Situations.Situations;
import com.example.density_check_web_service.domain.Situations.SituationsRepository;
import com.example.density_check_web_service.domain.Situations.dto.SituationsDto;
import com.example.density_check_web_service.domain.Situations.dto.SituationsListResponseDto;
import com.example.density_check_web_service.domain.Situations.dto.SituationsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SituationsService {
    private final LocationRepository locationRepository;
    private final CameraLocationRepository cameraLocationRepository;
    private final SituationsRepository situationsRepository;
    @Transactional
    public void saveSituations() {
        int[] cameras = {0, 0, 0, 0};
        List<CameraLocation> cameraLocations = cameraLocationRepository.findAll();
        for (CameraLocation c:cameraLocations) {
            int x = c.getX();
            int y = c.getY();
            if (x < 298) {
                if (y < 394) {
                    cameras[0]++;
                }
                else {
                    cameras[2]++;
                }
            }
            else {
                if (y < 394) {
                    cameras[1]++;
                }
                else {
                    cameras[4]++;
                }
            }
        }
        for(int i=0;i<4;i++) {
            int pi = 0;
            for (int x = i/2*3; x < i/2*3+3; x++) {
                for (int y = i%2*2; y < i%2*2+2; y++) {
                    List<Location> locations = locationRepository.findByXAndYAndModifiedDateIsGreaterThanEqualOrderByModifiedDateDesc(x, y, LocalDateTime.now().minusMinutes(1));
                    Set<PiAddress> set = locations.stream().map(loc -> {
                        return loc.getPiAddress();
                    }).collect(Collectors.toSet());
                    pi += set.size();
                }
            }
            situationsRepository.save(new Situations(i, pi, cameras[i]));
        }
    }

    @Transactional
    public List<SituationsResponseDto> findAll() {
        List<SituationsResponseDto> situations = new ArrayList<>();
        int[] cameras = {9, 6, 3, 0};
        List<CameraLocation> cameraLocations = cameraLocationRepository.findAll();
        for (CameraLocation c:cameraLocations) {
            int x = c.getX();
            int y = c.getY();
            if (x < 298) {
                if (y < 394) {
                    cameras[0]++;
                }
                else {
                    cameras[2]++;
                }
            }
            else {
                if (y < 394) {
                    cameras[1]++;
                }
                else {
                    cameras[4]++;
                }
            }
        }
        for(int i=0;i<4;i++) {
            int pi = 0;
            for (int x = i/2*3; x < i/2*3+3; x++) {
                for (int y = i%2*2; y < i%2*2+2; y++) {
                    List<Location> locations = locationRepository.findByXAndYAndModifiedDateIsGreaterThanEqualOrderByModifiedDateDesc(x, y, LocalDateTime.now().minusMinutes(1));
                    Set<PiAddress> set = locations.stream().map(loc -> {
                        return loc.getPiAddress();
                    }).collect(Collectors.toSet());
                    pi += set.size();
                }
            }
            Situations tmp = new Situations(i, pi, cameras[i]);
            situations.add(SituationsResponseDto.builder().entity(tmp).build());
        }
        return situations;
    }

    @Transactional
    public SituationsListResponseDto findAllByLoc(int loc) {
        for (int i=0; i<6; i++)
            situationsRepository.save(new Situations(loc, 1, 2));
        situationsRepository.flush();
        List<Situations> situations = situationsRepository.findAllByLoc(loc);
        return SituationsListResponseDto.builder().entity(situations).loc(loc).build();
    }

    @Transactional
    public List<SituationsDto> findAllByLocForExcel(int loc) {
        List<Situations> situations = situationsRepository.findAllByLoc(loc);
        return situations.stream().map(SituationsDto::new).collect(Collectors.toList());
    }

}
