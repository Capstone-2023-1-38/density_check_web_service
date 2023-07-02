package com.example.density_check;

import com.example.density_check.domain.Location.LocationRepository;
import com.example.density_check.domain.Location.dto.LocationRequestDto;
import com.example.density_check.service.LocationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
}
