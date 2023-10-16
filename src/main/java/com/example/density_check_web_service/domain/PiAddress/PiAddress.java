package com.example.density_check_web_service.domain.PiAddress;

import com.example.density_check_web_service.domain.BaseTimeEntity;
import com.example.density_check_web_service.domain.Users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToOne
    private Users users;

    @Builder
    public PiAddress(String address) {
        this.address = address;
    }

    public void update(Users users) {
        this.users = users;
    }

    public void deleteUsers() {this.users = null;}
}
