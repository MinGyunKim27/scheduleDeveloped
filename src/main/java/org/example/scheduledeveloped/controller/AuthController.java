package org.example.scheduledeveloped.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.Common.Const;
import org.example.scheduledeveloped.dto.LoginRequestDto;
import org.example.scheduledeveloped.dto.SignUpRequestDto;
import org.example.scheduledeveloped.dto.SignUpResponseDto;
import org.example.scheduledeveloped.dto.UserResponseDto;
import org.example.scheduledeveloped.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp( @RequestBody SignUpRequestDto requestDto ){
        SignUpResponseDto signUpResponseDto = userService.save(requestDto.getUserName(), requestDto.getEmail(), requestDto.getPassword());

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(
            @RequestBody LoginRequestDto requestDto,
            HttpServletRequest servletRequest
    ){

        UserResponseDto responseDto = userService.login(requestDto.getEmail(), requestDto.getPassword());
        Long id = responseDto.getId();

        if (id == null){
            return "redirect:/login";
        }

        HttpSession session = servletRequest.getSession();

        UserResponseDto loginUser = userService.findUserById(id);

        session.setAttribute(Const.LOGIN_USER,loginUser);

        return "loginSuccess!";

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }


}
