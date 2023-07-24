package com.example.density_check_web_service.domain.PiAddress;

import com.example.density_check_web_service.domain.Friends.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PiAddressRepository extends JpaRepository<PiAddress, Long> {
    boolean existsByAddress(String Address);

    Optional<PiAddress> findByAddress(String Address);
    @Query("SELECT p FROM PiAddress p WHERE p.users.email = :email")
    Optional<PiAddress> findByEmail(@Param("email") String email);
}
