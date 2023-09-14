package com.example.density_check_web_service.domain.Situations;

import com.example.density_check_web_service.domain.Posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SituationsRepository extends JpaRepository<Situations, Long> {
    List<Situations> findAllByLoc(int loc);
}
