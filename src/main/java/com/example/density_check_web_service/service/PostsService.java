package com.example.density_check_web_service.service;

import com.example.density_check_web_service.NotifyController;
import com.example.density_check_web_service.PostsController;
import com.example.density_check_web_service.domain.Notify.Notify;
import com.example.density_check_web_service.domain.Notify.NotifyRepository;
import com.example.density_check_web_service.domain.Notify.dto.NotifyDto;
import com.example.density_check_web_service.domain.Posts.Posts;
import com.example.density_check_web_service.domain.Posts.PostsRepository;
import com.example.density_check_web_service.domain.Posts.dto.PostsListResponseDto;
import com.example.density_check_web_service.domain.Posts.dto.PostsResponseDto;
import com.example.density_check_web_service.domain.Posts.dto.PostsSaveRequestDto;
import com.example.density_check_web_service.domain.Posts.dto.PostsUpdateRequestDto;
import com.example.density_check_web_service.domain.Users.Users;
import com.example.density_check_web_service.domain.Users.UsersRepository;
import com.example.density_check_web_service.domain.Users.dto.UsersResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final UsersRepository usersRepository;
    private final NotifyRepository notifyRepository;

    @Transactional
    public void save(PostsSaveRequestDto requestDto, String email)
    {
        Users users = usersRepository.findByEmail(email).orElseThrow();
        requestDto.setAuthor(users);
        Posts posts = postsRepository.save(requestDto.toEntity());

        List<Users> subsToList = usersRepository.findAll();
        for (Users user : subsToList) {
//            if (user.getId() != users.getId()) {
            Notify notify = new Notify(posts, false, user);
            notifyRepository.save(notify);
            SseEmitter sseEmitter = NotifyService.sseEmitters.get(user.getEmail());
            try {
                sseEmitter.send(SseEmitter.event().name("notification").data(new NotifyDto(notify)));
            } catch (Exception e) {
                NotifyService.sseEmitters.remove(user.getEmail());
            }
//            }
        }
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    @Transactional(readOnly = true)
    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public Page<PostsListResponseDto> findAllDesc(int page) {
        Sort sort = Sort.by("createdDate").descending();
        Pageable pageable = PageRequest.of(page, 10, sort);

        return postsRepository.findAll(pageable)
                .map(p -> PostsListResponseDto.builder().entity(p).build());
    }
}
