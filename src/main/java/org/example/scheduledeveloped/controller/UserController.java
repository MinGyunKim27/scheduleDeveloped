package org.example.scheduledeveloped.controller;

import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.dto.UserResponseDto;
import org.example.scheduledeveloped.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id){
        UserResponseDto userResponseDto = userService.findUserById(id);

        return new ResponseEntity<>(userResponseDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findUsers(@RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            UserResponseDto user = userService.findUserByUserName(name);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            List<UserResponseDto> users = userService.findAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

}
