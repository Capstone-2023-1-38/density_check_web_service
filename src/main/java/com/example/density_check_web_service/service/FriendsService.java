package com.example.density_check_web_service.service;

import com.example.density_check_web_service.domain.Friends.Friends;
import com.example.density_check_web_service.domain.Friends.FriendsRepository;
import com.example.density_check_web_service.domain.Friends.dto.FriendsListResponseDto;
import com.example.density_check_web_service.domain.Friends.dto.FriendsSaveRequestDto;
import com.example.density_check_web_service.domain.Users.Role;
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
        if(usersRepository.findByEmail("chju0905@naver.com").isEmpty()) {
            Users user = usersRepository.findById(1L).orElseThrow();
            Users user2 = usersRepository.saveAndFlush(new Users("아무개", "chju0905@naver.com", null, Role.USER));
            if(friendsRepository.findByFromAndTo(user.getEmail(), user2.getEmail()).isEmpty()) {
                List<Friends> tmp = new ArrayList<>();
                tmp.add(new Friends(user, user2, true));
                tmp.add(new Friends(user2, user, true));
                friendsRepository.saveAllAndFlush(tmp);
            }
        }
        List<FriendsListResponseDto> friendsListResponseDtoList = friendsRepository.findMutualByFrom(email).stream()
                .map(FriendsListResponseDto::new).collect(Collectors.toList());
        return friendsListResponseDtoList;
    }

    @Transactional
    public List<FriendsListResponseDto> findByFrom(String email) {
//        Users user = usersRepository.findById(1L).orElseThrow();
//        Users user2 = usersRepository.findById(3L).orElse(usersRepository.saveAndFlush(new Users("아무개2", "chju0905@kakao.com", Role.USER)));
//        if(friendsRepository.findByFromAndTo(user.getEmail(), user2.getEmail()).isEmpty()) {
//            List<Friends> tmp = new ArrayList<>();
//            tmp.add(new Friends(user, user2, false));
//            tmp.add(new Friends(user2, user, true));
//            friendsRepository.saveAllAndFlush(tmp);
//        }
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
        Users user2 = null;
        if (usersRepository.findById(3L).isEmpty()) {
            Users user = usersRepository.findById(1L).orElseThrow();
            user2 = usersRepository.saveAndFlush(new Users("아무개2", "chju0905@kakao.com", null, Role.USER));
            if(friendsRepository.findByFromAndTo(user.getEmail(), user2.getEmail()).isEmpty()) {
                List<Friends> tmp = new ArrayList<>();
                tmp.add(new Friends(user, user2, false));
                tmp.add(new Friends(user2, user, true));
                friendsRepository.saveAllAndFlush(tmp);
            }
        }

        return friendsRepository.countByFrom(email);
    }

    @Transactional
    public String save(String fromEmail, String toEmail) {
        if(fromEmail == toEmail) {
            return "자기자신에게 이웃 신청을 할 수 없습니다.";
        }
        if(!usersRepository.findByEmail(toEmail).isEmpty()) {
            if(friendsRepository.findByFromAndTo(fromEmail, toEmail).isEmpty()) {
                Users user = usersRepository.findByEmail(fromEmail).orElseThrow();
                Users user2 = usersRepository.findByEmail(toEmail).orElseThrow();
                List<Friends> tmp = new ArrayList<>();
                tmp.add(new Friends(user, user2, true));
                tmp.add(new Friends(user2, user, false));
                friendsRepository.saveAllAndFlush(tmp);

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
}
