package com.example.density_check.domain.Location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findFirst10ByOrderByIdDesc();
}
