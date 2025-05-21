package org.example.scheduledeveloped.repository;


import org.example.scheduledeveloped.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByUserName(String userName);

    Optional<User> findUserByEmailAndPassword(String email, String password);


    default User findMemberByUserNameOrElseThrow(String userName){
        return findUserByUserName(userName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Does not exist userName = " + userName));
    }

    default User findByIdOrElseThrow(Long id){
        return findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Does not exist userName = " + id));
    };

    Optional<User> findUserByUserNameAndPassword(String userName,String password);

    default User findUserByUserNameAndPasswordOrElseThrow(String userName, String password){
        return findUserByUserNameAndPassword(userName,password).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist userName and password"));
    };

    default User findUserByUserEmailAndPasswordOrElseThrow(String email, String password){
        return findUserByEmailAndPassword(email, password).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist Email and password"));
    };
}
