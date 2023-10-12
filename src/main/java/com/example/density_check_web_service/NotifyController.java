package com.example.density_check_web_service;

import com.example.density_check_web_service.domain.Notify.NotifyRepository;
import com.example.density_check_web_service.domain.Notify.dto.NotifyDto;
import com.example.density_check_web_service.domain.Users.UsersRepository;
import com.example.density_check_web_service.service.NotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("/notify/{id}")
    public List<NotifyDto> notify(Authentication authentication, @PathVariable Long id)
    {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        return notifyService.notify(email, id);
    }

    @ResponseBody
    @GetMapping("/warning")
    public void warning(Authentication authentication)
    {
        String email = null;
        if (authentication != null) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            email = oAuth2User.getAttribute("email");
        }
        notifyService.warning(email);
    }
}
