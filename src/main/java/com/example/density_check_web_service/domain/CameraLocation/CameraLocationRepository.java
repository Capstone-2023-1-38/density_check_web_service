package com.example.density_check_web_service.domain.CameraLocation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CameraLocationRepository extends JpaRepository<CameraLocation, Long> {

    @Query("select distinct c.ip from CameraLocation c")
    List<String> findDistinctByIp();
    List<CameraLocation> findAllByIp(String ip);
    void deleteAllByIp(String ip);
}
