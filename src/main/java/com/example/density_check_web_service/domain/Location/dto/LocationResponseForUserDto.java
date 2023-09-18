package com.example.density_check_web_service.domain.Location.dto;

import com.example.density_check_web_service.domain.Location.Location;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LocationResponseForUserDto {
    private String address;
    private String name;
    private int distance;
    private int x;
    private int y;
    private String grade;
    private LocalDateTime modifiedDate;

    @Builder
    public LocationResponseForUserDto(Location entity, int count) {
        if (entity.getPiAddress() != null) {
            this.address = entity.getPiAddress().getAddress();
            this.name = entity.getPiAddress().getUsers().getName();
        }
        this.distance = entity.getDistance();
        this.x = entity.getX();
        this.y = entity.getY();
        this.modifiedDate = entity.getModifiedDate();
        if (count > 9) {
            this.grade = "고위험";
        } else if (count > 4) {
            this.grade = "위험";
        } else if (count > 2) {
            this.grade = "주의";
        }else {
            this.grade = "안전";
        }
    }
}
