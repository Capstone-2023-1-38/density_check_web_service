package com.example.density_check_web_service.domain.CameraFrame;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CameraFrameRepository extends JpaRepository<CameraFrame, Long> {

    Optional<CameraFrame> findByIp(String ip);

}
