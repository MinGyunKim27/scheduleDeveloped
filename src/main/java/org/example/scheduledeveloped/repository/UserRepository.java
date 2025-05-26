package org.example.scheduledeveloped.repository;


import org.example.scheduledeveloped.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 *
 */
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUserName(String userName);

    Optional<User> findUserByEmailAndPassword(String email, String password);

    Optional<User> findUserById(Long id);

    Long countByUserName(String name);

    Long countByEmail(String email);

    Optional<User> findUserByEmail(String email);
}
