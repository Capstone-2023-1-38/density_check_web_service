package com.example.density_check_web_service.config;


import com.example.density_check_web_service.domain.Location.Location;
import com.example.density_check_web_service.domain.Location.LocationRepository;
import com.example.density_check_web_service.domain.PiAddress.PiAddress;
import com.example.density_check_web_service.domain.PiAddress.PiAddressRepository;
import com.example.density_check_web_service.domain.Posts.Posts;
import com.example.density_check_web_service.domain.Posts.PostsRepository;
import com.example.density_check_web_service.domain.Users.Role;
import com.example.density_check_web_service.domain.Users.Users;
import com.example.density_check_web_service.domain.Users.UsersRepository;
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

    @Override
    public void run(String... args) throws Exception {
        List<Users> users = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            users.add(Users.builder().name("아무개"+i).email("nklcb"+i+"@pusan.ac.kr").role(Role.USER).build());
        }
        usersRepository.saveAll(users);

        List<Posts> posts = Arrays.asList(
                Posts.builder().location("B0").author(users.get(0)).title("확인 부탁드립니다.").content("여기 현재 사람이 너무 많은 것 같습니다.").build(),
                Posts.builder().location("C3").author(users.get(1)).title("도와주세요!").content("여기 현재 사람이 너무 많은 것 같습니다.").build()
        );
        postsRepository.saveAll(posts);

        List<PiAddress> piAddresses = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            PiAddress piAddress = PiAddress.builder().address("아무개"+i).build();
            piAddress.update(users.get(i));
            piAddresses.add(piAddress);
        }
        piAddressRepository.saveAll(piAddresses);

        List<Location> locations = new ArrayList<>();
        int[] distribution = {5, 14, 14, 15, 25, 32, 32, 37, 44, 46, 46, 48, 59, 63, 63, 66, 72, 75, 77, 78, 90, 91, 95, 99};
        int index = 0;
        for(int i = 0; i < 100; i++) {
            while(distribution[index] < i) {
                index++;
            }
            Location location = Location.builder().piAddress(piAddresses.get(i)).distance(0).x(index/4).y(index%4).build();
            locations.add(location);
        }
        locationRepository.saveAll(locations);

    }
}
