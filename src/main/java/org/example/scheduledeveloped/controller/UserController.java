package org.example.scheduledeveloped.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.Common.Const;
import org.example.scheduledeveloped.dto.authDto.PasswordRequestDto;
import org.example.scheduledeveloped.dto.userDto.SessionUserResponseDto;
import org.example.scheduledeveloped.dto.userDto.UpdateUserRequestDto;
import org.example.scheduledeveloped.dto.userDto.UserResponseDto;
import org.example.scheduledeveloped.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
        UserResponseDto user = userService.findUserById(id);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }


    /*
    고민 한 부분
    여기서 로직을 나눌 것인가, 엔티티에서 나눌 것인가
     */
    @GetMapping
    public ResponseEntity<?> findUsers(
            @Validated @RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            UserResponseDto user = userService.findUserByUserName(name);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            List<UserResponseDto> users = userService.findAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SessionUserResponseDto> updateUsers(
            @PathVariable Long id,
            @Validated @RequestBody UpdateUserRequestDto dto,
            HttpSession session){

        SessionUserResponseDto sessionUserResponseDto = (SessionUserResponseDto) session.getAttribute(Const.LOGIN_USER);
        SessionUserResponseDto user = userService.updateUser(id,dto,sessionUserResponseDto);

        session.setAttribute(Const.LOGIN_USER,user);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            @RequestBody PasswordRequestDto dto,
            HttpSession session){

        SessionUserResponseDto sessionUserResponseDto = (SessionUserResponseDto) session.getAttribute(Const.LOGIN_USER);
        userService.deleteUser(id,dto,sessionUserResponseDto);

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
