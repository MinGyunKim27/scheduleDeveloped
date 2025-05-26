package org.example.scheduledeveloped.entity;


import jakarta.persistence.*;
import lombok.Getter;

/**
 *
 */
@Entity
@Table(name = "userDev")
@Getter
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public void updateUserNameAndPassword(String userName,String password){
        this.userName = userName;
        this.password = password;
    }
}