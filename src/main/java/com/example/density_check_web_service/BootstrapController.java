package com.example.density_check_web_service;

import com.example.density_check_web_service.domain.Location.dto.LocationRequestDto;
import com.example.density_check_web_service.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BootstrapController {
    private final LocationService locationService;

    @RequestMapping(path = "/dashboard")
    public String dashboard() {
        return "index.html";
    }

    @PostMapping(path = "/sendLocations")
    public void sendLocations(@RequestBody LocationRequestDto locationRequestDto) {
        locationService.saveLocation(locationRequestDto);
    }

    @GetMapping(path = "/getLocations")
    public List<LocationRequestDto> getLocations() {
        List<LocationRequestDto> allLocation = locationService.allLocationToJson();
        return allLocation;
    }

    @GetMapping(path = "/getLocationsEach")
    public List<LocationRequestDto> getLocationsEach() {
        List<LocationRequestDto> eachLocation = locationService.eachLocationToJson();
        return eachLocation;
    }
}
