package com.example.density_check_web_service.domain.Notify;

import com.example.density_check_web_service.domain.PiAddress.PiAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface NotifyRepository extends JpaRepository<Notify, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Notify n WHERE n.posts.id = :id")
    void deleteByPostsId(@Param("id") Long id);
    @Query("SELECT n FROM Notify n WHERE n.users.email = :email")
    List<Notify> findAllByEmail(@Param("email") String email);
}
