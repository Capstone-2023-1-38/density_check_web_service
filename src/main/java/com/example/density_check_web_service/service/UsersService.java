package com.example.density_check_web_service.service;

import com.example.density_check_web_service.config.auth.PrincipalDetails;
import com.example.density_check_web_service.domain.Users.Role;
import com.example.density_check_web_service.domain.Users.Users;
import com.example.density_check_web_service.domain.Users.UsersRepository;
import com.example.density_check_web_service.domain.Users.dto.UsersResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Transactional
    public UsersResponseDto findByEmail(String email) {
        Users users = usersRepository.findByEmail(email).orElseThrow();
        return new UsersResponseDto(users);
    }
    @Transactional
    public void updateName(String email, String name) {
        Users users = usersRepository.findByEmail(email).orElseThrow();
        users.updateName(name);
    }

    @Transactional
    public void updateRole(Authentication authentication, String email) {
        Users users = usersRepository.findByEmail(email).orElseThrow();
        Role role = Role.USER;
        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(authentication.getAuthorities());
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            role = Role.ADMIN;
        }
        updatedAuthorities.add(new SimpleGrantedAuthority(role.getKey())); //add your role here [e.g., new SimpleGrantedAuthority("ROLE_NEW_ROLE")]
        updatedAuthorities.remove(0);
        Authentication newAuth = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), updatedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);

        users.updateRole(role);
    }

    private final String fileDir = System.getProperty("user.dir") + "\\density_check_web_service\\src\\main\\resources\\static\\img\\profile\\";
    public String getFullPath(String filename) { return fileDir + filename; }

    @Transactional
    public void updatePicture(String email, MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            deletePicture(email);
        }
        else {
            File file = new File(fileDir);
            // 저장할 위치의 디렉토리가 존지하지 않을 경우
            if (!file.exists()) {
                // mkdir() 함수와 다른 점은 상위 디렉토리가 존재하지 않을 때 그것까지 생성
                file.mkdirs();
            }

            String originalFilename = multipartFile.getOriginalFilename();
            // 작성자가 업로드한 파일명 -> 서버 내부에서 관리하는 파일명
            // 파일명을 중복되지 않게끔 UUID로 정하고 ".확장자"는 그대로
            String storeFilename = UUID.randomUUID() + "." + extractExt(originalFilename);

            // 파일을 저장하는 부분 -> 파일경로 + storeFilename 에 저장
            multipartFile.transferTo(new File(getFullPath(storeFilename)));
            Users users = usersRepository.findByEmail(email).orElse(null);
            users.updatePicture("img/profile/"+storeFilename);
            System.out.println(fileDir);
        }
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    @Transactional
    public void deletePicture(String email) {
        Users users = usersRepository.findByEmail(email).orElse(null);
        users.deletePicture();
    }
}
