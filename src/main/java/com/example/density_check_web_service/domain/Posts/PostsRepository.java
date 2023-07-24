package com.example.density_check_web_service.domain.Posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    Page<Posts> findAll(Pageable pageable);
}
