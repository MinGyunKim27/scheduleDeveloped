package org.example.scheduledeveloped.entity;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * 사용자(User) 정보를 저장하는 엔티티 클래스입니다.
 * 사용자 이름, 이메일, 비밀번호 등의 정보를 포함하며,
 * BaseEntity를 상속하여 생성일/수정일 필드를 포함합니다.
 */
@Entity
@Table(name = "userDev")
@Getter
public class User extends BaseEntity {

    /**
     * 사용자 고유 ID (자동 증가).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사용자 이름.
     */
    @Column(nullable = false)
    private String userName;

    /**
     * 사용자 이메일.
     */
    @Column(nullable = false)
    private String email;

    /**
     * 사용자 비밀번호.
     */
    @Column(nullable = false)
    private String password;

    /**
     * 사용자 객체 생성자.
     *
     * @param userName 사용자 이름
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호
     */
    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    /**
     * JPA 기본 생성자 (프록시 생성을 위해 필요).
     */
    public User() {
    }

    /**
     * 사용자 이름과 비밀번호를 수정합니다.
     *
     * @param userName 수정할 사용자 이름
     * @param password 수정할 비밀번호
     */
    public void updateUserNameAndPassword(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
