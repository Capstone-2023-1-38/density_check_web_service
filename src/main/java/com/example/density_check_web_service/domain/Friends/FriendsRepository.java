package com.example.density_check_web_service.domain.Friends;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendsRepository extends JpaRepository<Friends, Long> {
    @Query("SELECT f FROM Friends f WHERE f.from.email = :email and f.mutual = true")
    List<Friends> findMutualByFrom(@Param("email") String email);

    @Query("SELECT f FROM Friends f WHERE f.from.email = :email and f.mutual = false")
    List<Friends> findByFrom(@Param("email") String email);

    @Query("SELECT COUNT(*) FROM Friends f WHERE f.from.email = :email and f.mutual = false")
    int countByFrom(@Param("email") String email);
    @Query("SELECT f FROM Friends f WHERE f.from.email = :fromEmail and f.to.email = :toEmail")
    Optional<Friends> findByFromAndTo(@Param("fromEmail") String fromEmail, @Param("toEmail") String toEmail);
}
