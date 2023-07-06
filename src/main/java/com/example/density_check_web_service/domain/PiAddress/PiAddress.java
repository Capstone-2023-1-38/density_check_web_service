package com.example.density_check_web_service.domain.PiAddress;

import com.example.density_check_web_service.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class PiAddress extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Builder
    public PiAddress(String address) {
        this.address = address;
    }
}
