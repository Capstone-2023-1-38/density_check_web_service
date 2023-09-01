package com.example.density_check_web_service;

import com.example.density_check_web_service.domain.Notify.NotifyRepository;
import com.example.density_check_web_service.domain.Notify.dto.NotifyDto;
import com.example.density_check_web_service.service.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Controller
public class NotifyController {
    private final NotifyService notifyService;

    public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    @ResponseBody
    @GetMapping(value = "/sub", produces = "text/event-stream")
    public SseEmitter subscribe(Authentication authentication,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        SseEmitter sseEmitter = notifyService.subscribe(email);

        return sseEmitter;
    }
    @ResponseBody
    @GetMapping("/notify")
    public List<NotifyDto> notify(Authentication authentication)
    {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        return notifyService.notify(email);
    }
}
