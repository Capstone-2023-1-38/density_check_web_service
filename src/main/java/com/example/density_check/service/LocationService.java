package com.example.density_check.service;

import com.example.density_check.domain.Location.Location;
import com.example.density_check.domain.Location.LocationRepository;
import com.example.density_check.domain.Location.dto.LocationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LocationService {
    private final LocationRepository locationRepository;

    @Transactional
    public void saveLocation(LocationRequestDto locationRequestDto) {
        locationRepository.save(locationRequestDto.toEntity());
    }

    @Transactional
    public List<LocationRequestDto> allLocationToJson(){
        List<Location> entity = locationRepository.findFirst10ByOrderByIdDesc();
        return entity.stream()
                .map(LocationRequestDto::new)
                .collect(Collectors.toList());


    }


}
