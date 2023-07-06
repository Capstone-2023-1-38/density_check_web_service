package com.example.density_check_web_service.domain.PiAddress;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PiAddressRepository extends JpaRepository<PiAddress, Long> {
    boolean existsByAddress(String Address);
}
