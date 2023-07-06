package com.example.density_check_web_service.service;

import com.example.density_check_web_service.domain.Location.Location;
import com.example.density_check_web_service.domain.Location.LocationRepository;
import com.example.density_check_web_service.domain.Location.dto.LocationRequestDto;
import com.example.density_check_web_service.domain.PiAddress.PiAddress;
import com.example.density_check_web_service.domain.PiAddress.PiAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final PiAddressRepository piAddressRepository;

    @Transactional
    public void saveLocation(LocationRequestDto locationRequestDto) {
        if (locationRepository.countByAddress(locationRequestDto.getAddress()) > 1000) {
            Location tmp = locationRepository.findFirstByAddressOrderByModifiedDateAsc(locationRequestDto.getAddress());
            tmp.setX(locationRequestDto.getX());
            tmp.setY(locationRequestDto.getY());
            locationRepository.save(tmp);
        }
        else {
            if (!piAddressRepository.existsByAddress(locationRequestDto.getAddress())) {
                PiAddress tmp = new PiAddress(locationRequestDto.getAddress());
                piAddressRepository.save(tmp);
            }
            locationRepository.save(locationRequestDto.toEntity());
        }
    }

    @Transactional
    public List<LocationRequestDto> allLocationToJson(){
        List<Location> entity = locationRepository.findFirst10ByOrderByIdDesc();
        return entity.stream()
                .map(LocationRequestDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<LocationRequestDto> eachLocationToJson(){
        List<PiAddress> allAddress = piAddressRepository.findAll();
        List<Location> entity = new ArrayList<>();;
        for(PiAddress tmp : allAddress) {
            entity.add(locationRepository.findFirstByAddressOrderByModifiedDateDesc(tmp.getAddress()));
        }
        return entity.stream()
                .map(LocationRequestDto::new)
                .collect(Collectors.toList());
    }


}
