package com.example.density_check_web_service.config;


import com.example.density_check_web_service.domain.Location.Location;
import com.example.density_check_web_service.domain.Location.LocationRepository;
import com.example.density_check_web_service.domain.PiAddress.PiAddress;
import com.example.density_check_web_service.domain.PiAddress.PiAddressRepository;
import com.example.density_check_web_service.domain.Posts.Posts;
import com.example.density_check_web_service.domain.Posts.PostsRepository;
import com.example.density_check_web_service.domain.Situations.Situations;
import com.example.density_check_web_service.domain.Situations.SituationsRepository;
import com.example.density_check_web_service.domain.Users.Role;
import com.example.density_check_web_service.domain.Users.Users;
import com.example.density_check_web_service.domain.Users.UsersRepository;
import com.example.density_check_web_service.service.SituationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Profile("test")
@RequiredArgsConstructor
public class TestData implements CommandLineRunner {
    private final UsersRepository usersRepository;
    private final PostsRepository postsRepository;
    private final PiAddressRepository piAddressRepository;
    private final LocationRepository locationRepository;
    private final SituationsRepository situationsRepository;
    private final SituationsService situationsService;

    @Override
    public void run(String... args) throws Exception {
        List<Users> users = new ArrayList<>();
        for (int i = 0; i < 105; i++) {
            users.add(Users.builder().name("아무개" + i).email("nklcb" + i + "@pusan.ac.kr").role(Role.USER).build());
        }
        usersRepository.saveAll(users);

        List<Posts> posts = Arrays.asList(
                Posts.builder().location("B0").author(users.get(0)).title("확인 부탁드립니다.").content("여기 현재 사람이 너무 많은 것 같습니다.").build(),
                Posts.builder().location("C3").author(users.get(1)).title("도와주세요!").content("여기 현재 사람이 너무 많은 것 같습니다.").build()
        );
        postsRepository.saveAll(posts);

        List<PiAddress> piAddresses = new ArrayList<>();
        for (int i = 0; i < 105; i++) {
            String strI = String.valueOf(i);
            PiAddress piAddress = PiAddress.builder().address(strI+":"+strI+":"+strI+":"+strI).build();
            piAddress.update(users.get(i));
            piAddresses.add(piAddress);
        }
        piAddressRepository.saveAll(piAddresses);

        List<Location> locations = new ArrayList<>();
        int[] distribution = {5, 14, 14, 15, 25, 32, 32, 37, 44, 46, 46, 48, 58, 62, 62, 65, 71, 74, 76, 77, 89, 90, 95, 100};
        int index = 0;
        for (int i = 0; i < 100; i++) {
            while (distribution[index] < i) {
                index++;
            }
            Location location = Location.builder().piAddress(piAddresses.get(i)).distance(0).x(index / 4).y(index % 4).build();
            locations.add(location);
        }
        for (int i = 100; i < 105; i++) {
            Location location = Location.builder().piAddress(piAddresses.get(i)).distance(0).x(4).y(0).build();
            locations.add(location);
        }

        locationRepository.saveAll(locations);

        int[] totalDistribution = {10, 15, 30, 40, 60, 5, 11, 8, 15, 3, 20, 25, 12, 40, 35, 13, 24, 21, 10, 18};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                situationsRepository.save(new Situations(i, totalDistribution[i*5+j]-3, 3));
            }
        }
        situationsService.saveSituations();
    }
}
