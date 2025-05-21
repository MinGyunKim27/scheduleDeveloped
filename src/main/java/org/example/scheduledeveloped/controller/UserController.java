package org.example.scheduledeveloped.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.Common.Const;
import org.example.scheduledeveloped.dto.UpdateUserRequestDto;
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
    public ResponseEntity<UserResponseDto> findUserById(
            @PathVariable Long id){
        UserResponseDto userResponseDto = userService.findUserById(id);

        return new ResponseEntity<>(userResponseDto,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> findUsers(
            @RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            UserResponseDto user = userService.findUserByUserName(name);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            List<UserResponseDto> users = userService.findAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @PatchMapping
    public ResponseEntity<UserResponseDto> updateUsers(
            @RequestBody UpdateUserRequestDto requestDto,
            @RequestParam String newUserName,
            @RequestParam String newPassword){
        UserResponseDto userResponseDto = userService.updateUser(requestDto,newUserName,newPassword);

        return new ResponseEntity<>(userResponseDto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id){
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser(HttpSession session) {
        UserResponseDto user = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(user);
    }

}
