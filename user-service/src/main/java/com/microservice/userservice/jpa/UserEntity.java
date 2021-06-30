package com.microservice.userservice.jpa;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")  // 테이블 이름을 명시적으로 지정할 수 있다
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, unique = true)
    private String encryptedPwd;
}
