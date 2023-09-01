package com.example.density_check_web_service.service;

import com.example.density_check_web_service.NotifyController;
import com.example.density_check_web_service.PostsController;
import com.example.density_check_web_service.domain.Notify.Notify;
import com.example.density_check_web_service.domain.Notify.NotifyRepository;
import com.example.density_check_web_service.domain.Notify.dto.NotifyDto;
import com.example.density_check_web_service.domain.Posts.dto.PostsListResponseDto;
import com.example.density_check_web_service.domain.Users.UsersRepository;
import groovy.util.NodeList;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NotifyService {
    private final NotifyRepository notifyRepository;
    private final UsersRepository usersRepository;
    @Transactional
    public List<NotifyDto> notify(String email) {
        List<Notify> notify = notifyRepository.findAllByEmail(email);
        if (notify != null) {
            return notify.stream().map(NotifyDto::new).collect(Collectors.toList());
        }
        else {
            return null;
        }
    }

    @Transactional
    public SseEmitter subscribe(String email) {

        long userId = usersRepository.findByEmail(email).get().getId();

        if (NotifyController.sseEmitters.containsKey(userId)) {
            return NotifyController.sseEmitters.get(userId);
        }

        // 현재 클라이언트를 위한 SseEmitter 생성
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            // 연결!!
            sseEmitter.send(SseEmitter.event().name("connect"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // user의 pk값을 key값으로 해서 SseEmitter를 저장
        NotifyController.sseEmitters.put(userId, sseEmitter);

        sseEmitter.onCompletion(() -> NotifyController.sseEmitters.remove(userId));
        sseEmitter.onTimeout(() -> NotifyController.sseEmitters.remove(userId));
        sseEmitter.onError((e) -> NotifyController.sseEmitters.remove(userId));

        return sseEmitter;
    }
}
