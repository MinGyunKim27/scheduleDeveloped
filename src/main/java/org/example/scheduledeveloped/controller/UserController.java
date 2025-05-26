package org.example.scheduledeveloped.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.Common.Const;
import org.example.scheduledeveloped.dto.authDto.PasswordRequestDto;
import org.example.scheduledeveloped.dto.userDto.SessionUserDto;
import org.example.scheduledeveloped.dto.userDto.UpdateUserRequestDto;
import org.example.scheduledeveloped.dto.userDto.UserResponseDto;
import org.example.scheduledeveloped.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(
            @PathVariable Long id){
        UserResponseDto user = userService.findUserById(id);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }


    /**
     *
     * @param name
     * @return
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


    /**
     *
     * @param id
     * @param dto
     * @param session
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUsers(
            @PathVariable Long id,
            @Validated @RequestBody UpdateUserRequestDto dto,
            HttpSession session){

        SessionUserDto sessionUserDto = (SessionUserDto) session.getAttribute(Const.LOGIN_USER);
        UserResponseDto updateUser = userService.updateUser(id,dto,sessionUserDto);

        session.setAttribute(Const.LOGIN_USER,updateUser);

        return new ResponseEntity<>(updateUser,HttpStatus.OK);
    }


    /**
     *
     * @param id
     * @param dto
     * @param session
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            @RequestBody PasswordRequestDto dto,
            HttpSession session){

        SessionUserDto sessionUserDto = (SessionUserDto) session.getAttribute(Const.LOGIN_USER);
        userService.deleteUser(id,dto,sessionUserDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     *
     * @param session
     * @return
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser(HttpSession session) {
        UserResponseDto user = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(user);
    }
}
