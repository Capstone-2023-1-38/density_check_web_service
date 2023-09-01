package com.example.density_check_web_service.service;

import com.example.density_check_web_service.NotifyController;
import com.example.density_check_web_service.PostsController;
import com.example.density_check_web_service.domain.Notify.Notify;
import com.example.density_check_web_service.domain.Notify.NotifyRepository;
import com.example.density_check_web_service.domain.Notify.dto.NotifyDto;
import com.example.density_check_web_service.domain.Posts.dto.PostsListResponseDto;
import com.example.density_check_web_service.domain.Users.Users;
import com.example.density_check_web_service.domain.Users.UsersRepository;
import groovy.util.NodeList;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NotifyService {
    private final NotifyRepository notifyRepository;
    private final UsersRepository usersRepository;
    public static Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    public List<NotifyDto> notify(String email, Long id) {
        if (id != 0) {
            notifyRepository.deleteByPostsId(id);
            notifyRepository.flush();
        }
        List<Notify> notify = notifyRepository.findAllByEmail(email);
        if (notify != null) {
            return notify.stream().map(NotifyDto::new).collect(Collectors.toList());
        }
        else {
            return null;
        }
    }

    public SseEmitter subscribe(String email) {

        if (sseEmitters.containsKey(email)) {
            return sseEmitters.get(email);
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
        sseEmitters.put(email, sseEmitter);

        sseEmitter.onCompletion(() -> sseEmitters.remove(email));
        sseEmitter.onTimeout(() -> sseEmitters.remove(email));
        sseEmitter.onError((e) -> sseEmitters.remove(email));

        return sseEmitter;
    }

//    @Async
//    @EventListener
//    public void catchEvent(Notify notify){
//        Users user = notify.getUsers();
//        SseEmitter sseEmitter = NotifyService.sseEmitters.get(user.getId());
//        try {
//            sseEmitter.send(SseEmitter.event().name("notification").data(new NotifyDto(notify)));
//        } catch (Exception e) {
//            NotifyService.sseEmitters.remove(user.getId());
//        }
//    }
}
