package com.example.density_check_web_service;

import com.example.density_check_web_service.domain.Location.dto.LocationRequestDto;
import com.example.density_check_web_service.domain.Location.dto.LocationResponseDto;
import com.example.density_check_web_service.domain.Location.dto.LocationResponseForUserDto;
import com.example.density_check_web_service.domain.PiAddress.dto.PiAddressResponseDto;
import com.example.density_check_web_service.domain.Users.dto.UsersResponseDto;
import com.example.density_check_web_service.service.LocationService;
import com.example.density_check_web_service.service.SituationsService;
import lombok.RequiredArgsConstructor;
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
    public void sendLocations(@RequestBody LocationRequestDto locationRequestDto) {
        locationService.saveLocation(locationRequestDto);
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
    public LocationResponseForUserDto findByEmail(String email, Authentication authentication) {
        if(email.isEmpty()) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            email = oAuth2User.getAttribute("email");
        }
        LocationResponseForUserDto locationResponseForUserDto = locationService.findLocationByEmail(email);
//        model.addAttribute("x", locationRequestDto.getX());
//        model.addAttribute("y", locationRequestDto.getY());
        return locationResponseForUserDto;
    }

    @ResponseBody
    @GetMapping(path="/findUsers")
    public List<UsersResponseDto> findUsersByEmail(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        return locationService.findUsersByEmail(email);
    }

    @GetMapping(path = "/area")
    public String findUserByArea(@RequestParam int x, @RequestParam int y, @RequestParam(defaultValue = "1") int duration, Model model) {
        List<PiAddressResponseDto> piAddressResponseDtos = locationService.findUserByArea(x, y, duration);
        model.addAttribute("list", piAddressResponseDtos);
        return "specific-area-user";
    }
}
