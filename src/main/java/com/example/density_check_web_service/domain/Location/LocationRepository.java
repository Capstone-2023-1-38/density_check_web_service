package com.example.density_check_web_service.domain.Location;

import com.example.density_check_web_service.domain.Friends.Friends;
import com.example.density_check_web_service.domain.PiAddress.PiAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findFirst10ByOrderByIdDesc();

    Long countByPiAddress(PiAddress piAddress);

    Location findFirstByPiAddressOrderByModifiedDateDesc(PiAddress piAddress);

    Location findFirstByPiAddressOrderByModifiedDateAsc(PiAddress piAddress);

    List<Location> findByXAndYAndModifiedDateIsGreaterThanEqualOrderByModifiedDateDesc(int x, int y, LocalDateTime modifiedDate);

}
