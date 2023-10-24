package com.example.density_check_web_service.service;

import com.example.density_check_web_service.NotifyController;
import com.example.density_check_web_service.PostsController;
import com.example.density_check_web_service.domain.Location.Location;
import com.example.density_check_web_service.domain.Location.LocationRepository;
import com.example.density_check_web_service.domain.Notify.Notify;
import com.example.density_check_web_service.domain.Notify.NotifyRepository;
import com.example.density_check_web_service.domain.Notify.dto.NotifyDto;
import com.example.density_check_web_service.domain.PiAddress.PiAddress;
import com.example.density_check_web_service.domain.PiAddress.PiAddressRepository;
import com.example.density_check_web_service.domain.Posts.dto.PostsListResponseDto;
import com.example.density_check_web_service.domain.Users.Role;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NotifyService {
    private final NotifyRepository notifyRepository;
    private final LocationRepository locationRepository;
    private final PiAddressRepository piAddressRepository;
    private final FriendsService friendsService;
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
        friendsService.addFriendsForTest(email);
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

    @Transactional
    public void warning(String email) {
        testData();

        if (email == null)
            return;

        PiAddress user = piAddressRepository.findByEmail(email).orElse(null);

        if (user == null)
            return;

        Location location = locationRepository.findFirstByPiAddressOrderByModifiedDateDesc(user);
        List<Location> locations = locationRepository.findByXAndYAndModifiedDateIsGreaterThanEqualOrderByModifiedDateDesc(location.getX(), location.getY(), LocalDateTime.now().minusMinutes(1));

        if(location == null || location.getModifiedDate().isBefore(LocalDateTime.now().minusMinutes(1)))
            return;

        Set<PiAddress> set = locations.stream().map(loc -> {
            return loc.getPiAddress();
        }).collect(Collectors.toSet());
        if (set.size() > 4 && user.getUsers().getRole().equals(Role.USER)) {
            SseEmitter sseEmitter = NotifyService.sseEmitters.get(email);
            try {
                sseEmitter.send(SseEmitter.event().name("warning").data(""));
            } catch (Exception e) {
                NotifyService.sseEmitters.remove(email);
            }
        }
    }

    @Transactional
    public void testData() {
        //Test Data
        List<Location> locations = new ArrayList<>();
        int[] distribution = {5, 14, 14, 15, 25, 32, 32, 37, 44, 46, 46, 48, 58, 62, 62, 65, 71, 74, 76, 77, 89, 90, 95, 100};
        int index = 0;
        List<PiAddress> piAddresses = piAddressRepository.findAll();

        for(int i = 0; i < 100; i++) {
            while(distribution[index] < i) {
                index++;
            }
            Location location = Location.builder().piAddress(piAddresses.get(i)).distance(0).x(index/4).y(index%4).build();
            locations.add(location);
        }
        locationRepository.saveAll(locations);
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
