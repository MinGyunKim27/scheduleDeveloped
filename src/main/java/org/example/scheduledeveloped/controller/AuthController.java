package org.example.scheduledeveloped.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.Common.Const;
import org.example.scheduledeveloped.dto.authDto.LoginRequestDto;
import org.example.scheduledeveloped.dto.authDto.SignUpRequestDto;
import org.example.scheduledeveloped.dto.authDto.SignUpResponseDto;
import org.example.scheduledeveloped.dto.userDto.SessionUserResponseDto;
import org.example.scheduledeveloped.service.AuthService;
import org.example.scheduledeveloped.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 *
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;


    /**
     *
     * @param requestDto
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@Validated @RequestBody SignUpRequestDto requestDto ){
        SignUpResponseDto signUpResponseDto = authService.signUp(requestDto.getUserName(), requestDto.getEmail(), requestDto.getPassword());

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.OK);
    }

    /**
     *
     * @param requestDto
     * @param servletRequest
     * @return
     */
    @PostMapping("/login")
    public String login(
            @Validated @RequestBody LoginRequestDto requestDto,
            HttpServletRequest servletRequest
    ){

        SessionUserResponseDto responseDto = authService.login(requestDto.getEmail(), requestDto.getPassword());
        Long id = responseDto.getId();

        if (id == null){
            return "redirect:/login";
        }

        HttpSession session = servletRequest.getSession();

        SessionUserResponseDto loginUser = userService.findUserByIdContainsPassword(id);

        session.setAttribute(Const.LOGIN_USER,loginUser);

        return "loginSuccess!";

    }

    /**
     *
     * @param request
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }


}
