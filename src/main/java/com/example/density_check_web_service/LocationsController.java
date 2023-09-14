package com.example.density_check_web_service;

import com.example.density_check_web_service.domain.Location.dto.LocationListResponseDto;
import com.example.density_check_web_service.domain.Location.dto.LocationRequestDto;
import com.example.density_check_web_service.domain.Location.dto.LocationResponseDto;
import com.example.density_check_web_service.service.LocationService;
import com.example.density_check_web_service.service.SituationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class LocationsController {
    private final LocationService locationService;
    private final SituationsService situationsService;
    private static boolean check0030 = true;
    @RequestMapping(path = "/")
    public String dashboard() {
        return "../templates/index.html";
    }

    @ResponseBody
    @PostMapping(path = "/sendLocations")
    public void sendLocations(@RequestBody LocationRequestDto locationRequestDto, Authentication authentication) {
        String email = null;
        if (authentication != null) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            email = oAuth2User.getAttribute("email");
        }
        locationService.saveLocation(locationRequestDto);
        locationService.warning(email);
        int minute = LocalDateTime.now().getMinute();
        if(check0030 && (minute == 0 || minute == 30)) {
            situationsService.saveSituations();
            check0030 = false;
        }
        else if(!check0030 && (minute == 1 || minute == 31)) {
            check0030 = true;
        }
    }
    @ResponseBody
    @GetMapping(path = "/getLocations")
    public List<LocationResponseDto> getLocations() {
        List<LocationResponseDto> allLocation = locationService.allLocationToJson();
        return allLocation;
    }
    @ResponseBody
    @GetMapping(path = "/getLocationsEach")
    public List<LocationResponseDto> getLocationsEach() {
        List<LocationResponseDto> eachLocation = locationService.eachLocationToJson();
        return eachLocation;
    }

    @ResponseBody
    @PostMapping(consumes = "application/x-www-form-urlencoded", path="/find")
    public LocationResponseDto findByEmail(String email, Authentication authentication) {
        if(email == null) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            email = oAuth2User.getAttribute("email");
        }
        LocationResponseDto locationResponseDto = locationService.findLocationByEmail(email);
//        model.addAttribute("x", locationRequestDto.getX());
//        model.addAttribute("y", locationRequestDto.getY());
        return locationResponseDto;
    }

    @GetMapping(path = "/area")
    public String findUserByArea(@RequestParam int x, @RequestParam int y, @RequestParam(defaultValue = "1") int duration, Model model) {
        List<LocationListResponseDto> locationListResponseDto = locationService.findUserByArea(x, y, duration);
        model.addAttribute("list", locationListResponseDto);
        return "specific-area-user";
    }
}
