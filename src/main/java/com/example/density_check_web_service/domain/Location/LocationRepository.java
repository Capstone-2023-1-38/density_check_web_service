package com.example.density_check_web_service.domain.Location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findFirst10ByOrderByIdDesc();

    Long countByAddress(String address);

    Location findFirstByAddressOrderByModifiedDateDesc(String address);

    Location findFirstByAddressOrderByModifiedDateAsc(String address);

}
