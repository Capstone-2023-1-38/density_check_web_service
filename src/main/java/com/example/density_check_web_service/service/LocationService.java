package com.example.density_check_web_service.service;

import com.example.density_check_web_service.domain.CameraLocation.CameraLocation;
import com.example.density_check_web_service.domain.CameraLocation.CameraLocationRepository;
import com.example.density_check_web_service.domain.CameraLocation.dto.CameraLocationRequestDto;
import com.example.density_check_web_service.domain.CameraLocation.dto.CameraLocationRequestListDto;
import com.example.density_check_web_service.domain.CameraLocation.dto.CameraLocationResponseDto;
import com.example.density_check_web_service.domain.Location.Location;
import com.example.density_check_web_service.domain.Location.LocationRepository;
import com.example.density_check_web_service.domain.Location.dto.LocationListResponseDto;
import com.example.density_check_web_service.domain.Location.dto.LocationRequestDto;
import com.example.density_check_web_service.domain.Location.dto.LocationResponseDto;
import com.example.density_check_web_service.domain.Location.dto.LocationResponseForUserDto;
import com.example.density_check_web_service.domain.Notify.Notify;
import com.example.density_check_web_service.domain.Notify.dto.NotifyDto;
import com.example.density_check_web_service.domain.PiAddress.PiAddress;
import com.example.density_check_web_service.domain.PiAddress.PiAddressRepository;
import com.example.density_check_web_service.domain.Users.Role;
import com.example.density_check_web_service.domain.Users.Users;
import com.example.density_check_web_service.domain.Users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final PiAddressRepository piAddressRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public void saveLocation(LocationRequestDto locationRequestDto) {
        PiAddress tmp = piAddressRepository.findByAddress(locationRequestDto.getAddress())
                .orElse(piAddressRepository.save(new PiAddress(locationRequestDto.getAddress())));
        if (locationRepository.countByPiAddress(tmp) > 1000) {
            Location location = locationRepository.findFirstByPiAddressOrderByModifiedDateAsc(tmp);
            location.setX(locationRequestDto.getX());
            location.setY(locationRequestDto.getY());
            locationRepository.save(location);
        }
        else {
            locationRepository.save(locationRequestDto.toEntity(tmp));
        }
    }

    @Transactional
    public List<LocationResponseDto> allLocationToJson(){
        List<Location> entity = locationRepository.findFirst10ByOrderByIdDesc();
        return entity.stream()
                .map(LocationResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<LocationResponseDto> eachLocationToJson(){
        List<PiAddress> allAddress = piAddressRepository.findAll();
        List<Location> entity = new ArrayList<>();
        for(PiAddress tmp : allAddress) {
            Location location = locationRepository.findFirstByPiAddressOrderByModifiedDateDesc(tmp);
            if (location.getModifiedDate().isAfter(LocalDateTime.now().minusMinutes(1)))
                entity.add(location);
        }
        return entity.stream()
                .map(LocationResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public LocationResponseForUserDto findLocationByEmail(String email) {
        if(piAddressRepository.findByEmail(email).isEmpty()) {
//            return new LocationResponseDto(new Location(null, 0, 0, 0));
            Users users = usersRepository.findByEmail(email).orElse(null);
            if(users == null) {
                users = new Users("아무개3", email, null, Role.USER);
                usersRepository.saveAndFlush(users);
            }
            PiAddress piAddress = new PiAddress("111.111.111.111");
            piAddress.setUsers(users);
            piAddress = piAddressRepository.saveAndFlush(piAddress);
            locationRepository.saveAndFlush(new Location(piAddress, 0, 0, 0));
        }
        PiAddress piAddress = piAddressRepository.findByEmail(email).orElseThrow();
        Location location = locationRepository.findFirstByPiAddressOrderByModifiedDateAsc(piAddress);
        List<Location> locations = locationRepository.findByXAndYAndModifiedDateIsGreaterThanEqualOrderByModifiedDateDesc(location.getX(), location.getY(), location.getModifiedDate().minusMinutes(1));
        Set<PiAddress> set = locations.stream().map(l -> {
            return l.getPiAddress();
        }).collect(Collectors.toSet());
        return new LocationResponseForUserDto(location, set.size());
    }

    @Transactional
    public List<LocationListResponseDto> findUserByArea(int x, int y, int duration) {
        if(piAddressRepository.findByAddress("222").isEmpty()) {
            for (int i = 2; i < 5; i++) {
                Users users = new Users("아무개" + String.valueOf(i), "email@email.com" + String.valueOf(i), null, Role.USER);
                usersRepository.saveAndFlush(users);
                PiAddress piAddress = new PiAddress(String.valueOf(i * 111));
                piAddress.setUsers(users);
                piAddressRepository.saveAndFlush(piAddress);
                locationRepository.saveAndFlush(new Location(piAddress, 0, x, y));
                locationRepository.saveAndFlush(new Location(piAddress, 0, x, y));
            }
        }

        List<Location> locations = locationRepository.findByXAndYAndModifiedDateIsGreaterThanEqualOrderByModifiedDateDesc(x, y, LocalDateTime.now().minusMinutes(duration));
        Set<PiAddress> set = locations.stream().map(location -> {
            return location.getPiAddress();
        }).collect(Collectors.toSet());
        List<LocationListResponseDto> modifiedLocation = new ArrayList<>();

        for (Location location : locations) {
            PiAddress piAddress = location.getPiAddress();
            if (set.contains(piAddress)) {
                modifiedLocation.add(LocationListResponseDto.builder().entity(location).build());
                set.remove(piAddress);
            }
        }

        return modifiedLocation;
    }
}
