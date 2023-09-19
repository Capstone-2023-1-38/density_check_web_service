package com.example.density_check_web_service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class IndexController {
    @GetMapping(path = "/current-location")
    public String currentLocation() {
        return "current-location.html";
    }

    @GetMapping(path = "/density-check")
    public String densityCheck() {
        return "density-check.html";
    }

    @GetMapping(path = "/building")
    public String building() {
        return "building.html";
    }

    @GetMapping(path = "/camera-location")
    public String cameraLocation() {
        return "camera-location.html";
    }

    @GetMapping(path = "/camera")
    public String camera() {
        return "camera.html";
    }

    @GetMapping(path = "/report")
    public String report() {
        return "report.html";
    }

    @GetMapping(path = "/report-board")
    public String reportBoard() {
        return "report-board.html";
    }

    @GetMapping(path = "/neighbor")
    public String neighbor() {
        return "neighbor.html";
    }

    @GetMapping(path = "/neighbor-add")
    public String neighborAdd() {
        return "neighbor-add.html";
    }

    @GetMapping(path = "/user-find")
    public String userFind() {
        return "user-find.html";
    }

    @GetMapping(path = "/specific-area-user-find")
    public String specificAreaUserFind() {
        return "specific-area-user-find.html";
    }

    @GetMapping(path = "/user-management")
    public String userManagement() {
        return "user-management.html";
    }

    @GetMapping(path = "/situation")
    public String situation() {return "situation.html";}

    @GetMapping(path = "/setting")
    public String setting() {
        return "setting.html";
    }

//    @GetMapping(path = "/logout")
//    public String logout() {
//        return "../templates/index.html";
//    }

    @GetMapping(path = "/customLogin")
    public String login() {
        return "login.html";
    }


}
