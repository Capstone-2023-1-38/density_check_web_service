package com.example.density_check_web_service.service;

import com.example.density_check_web_service.domain.Friends.Friends;
import com.example.density_check_web_service.domain.Friends.FriendsRepository;
import com.example.density_check_web_service.domain.Friends.dto.FriendsListResponseDto;
import com.example.density_check_web_service.domain.Users.Users;
import com.example.density_check_web_service.domain.Users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FriendsService {
    private final UsersRepository usersRepository;
    private final FriendsRepository friendsRepository;

    @Transactional
    public List<FriendsListResponseDto> findMutualByFrom(String email) {
        List<FriendsListResponseDto> friendsListResponseDtoList = friendsRepository.findMutualByFrom(email).stream()
                .map(FriendsListResponseDto::new).collect(Collectors.toList());

        if (friendsListResponseDtoList.isEmpty()) {
            return new ArrayList<>();
        }
        return friendsListResponseDtoList;
    }

    @Transactional
    public List<FriendsListResponseDto> findByFrom(String email) {
        List<FriendsListResponseDto> friendsListResponseDtoList = friendsRepository.findByFrom(email).stream()
                .map(FriendsListResponseDto::new).collect(Collectors.toList());
        return friendsListResponseDtoList;
    }

    @Transactional
    public void update(String fromEmail, String toEmail) {
        Friends friends = friendsRepository.findByFromAndTo(fromEmail, toEmail).orElseThrow();
        friends.update(true);
    }

    @Transactional
    public void delete(String fromEmail, String toEmail) {
        List<Friends> friends = new ArrayList<>();
        friends.add(friendsRepository.findByFromAndTo(fromEmail, toEmail).orElseThrow());
        friends.add(friendsRepository.findByFromAndTo(toEmail, fromEmail).orElseThrow());
        friendsRepository.deleteAllInBatch(friends);
    }

    @Transactional
    public int count(String email) {
        return friendsRepository.countByFrom(email);
    }

    @Transactional
    public String save(String fromEmail, String toEmail) {
        if(fromEmail.equals(toEmail)) {
            return "자기 자신에게 이웃 신청을 할 수 없습니다.";
        }
        if(!usersRepository.findByEmail(toEmail).isEmpty()) {
            if(friendsRepository.findByFromAndTo(fromEmail, toEmail).isEmpty()) {
                Users user = usersRepository.findByEmail(fromEmail).orElseThrow();
                Users user2 = usersRepository.findByEmail(toEmail).orElseThrow();
                List<Friends> tmp = new ArrayList<>();
                tmp.add(new Friends(user, user2, true));
                tmp.add(new Friends(user2, user, false));
                friendsRepository.saveAll(tmp);

                return "이웃 신청이 성공적으로 완료되었습니다.";
            }
            else {
                return "이미 이웃 신청을 했거나, 이웃인 이메일입니다.";
            }
        }
        else {
            return "이메일이 존재하지 않습니다.";
        }
    }

    @Transactional
    public void addFriendsForTest(String email) {
        Users user = usersRepository.findByEmail(email).orElseThrow();
        Users user1 = usersRepository.findById(10L).orElseThrow();
        Users user2 = usersRepository.findById(20L).orElseThrow();
        Users user3 = usersRepository.findById(30L).orElseThrow();

        List<Friends> tmp = new ArrayList<>();
        tmp.add(new Friends(user, user1, true));
        tmp.add(new Friends(user1, user, true));
        tmp.add(new Friends(user, user2, false));
        tmp.add(new Friends(user2, user, true));
        tmp.add(new Friends(user, user3, false));
        tmp.add(new Friends(user3, user, true));
        friendsRepository.saveAll(tmp);
    }
}
