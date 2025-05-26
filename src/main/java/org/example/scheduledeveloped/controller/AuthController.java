package org.example.scheduledeveloped.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.Common.Const;
import org.example.scheduledeveloped.dto.authDto.LoginRequestDto;
import org.example.scheduledeveloped.dto.authDto.SignUpRequestDto;
import org.example.scheduledeveloped.dto.authDto.SignUpResponseDto;
import org.example.scheduledeveloped.dto.userDto.UserResponseDto;
import org.example.scheduledeveloped.service.AuthService;
import org.example.scheduledeveloped.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 인증(Authentication) 관련 요청을 처리하는 컨트롤러입니다.
 * 회원가입, 로그인, 로그아웃 기능을 제공합니다.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    /**
     * 사용자 회원가입을 처리합니다.
     *
     * @param requestDto 회원가입 요청 정보 (이름, 이메일, 비밀번호)
     * @return 생성된 사용자 정보를 담은 응답 DTO
     */
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@Validated @RequestBody SignUpRequestDto requestDto) {
        SignUpResponseDto signUpResponseDto = authService.signUp(
                requestDto.getUserName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );
        return new ResponseEntity<>(signUpResponseDto, HttpStatus.OK);
    }

    /**
     * 사용자 로그인을 처리합니다.
     * 로그인 성공 시 사용자 정보를 세션에 저장합니다.
     *
     * @param requestDto 로그인 요청 정보 (이메일, 비밀번호)
     * @param servletRequest HttpServletRequest 객체 (세션 접근용)
     * @return 로그인 성공 시 메시지 반환 또는 실패 시 리디렉션 문자열
     */
    @PostMapping("/login")
    public String login(
            @Validated @RequestBody LoginRequestDto requestDto,
            HttpServletRequest servletRequest
    ) {
        UserResponseDto responseDto = authService.login(requestDto.getEmail(), requestDto.getPassword());
        Long id = responseDto.getId();

        if (id == null) {
            return "redirect:/login";
        }

        HttpSession session = servletRequest.getSession();

        UserResponseDto loginUser = userService.findUserById(id);

        session.setAttribute(Const.LOGIN_USER, loginUser);

        return "loginSuccess!";
    }

    /**
     * 사용자 로그아웃을 처리합니다.
     * 현재 세션을 무효화하여 로그아웃 상태로 만듭니다.
     *
     * @param request HttpServletRequest 객체 (세션 접근용)
     * @return 로그아웃 후 리디렉션 문자열
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 이미 생성된 세션만 가져옴
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return "redirect:/login";
    }
}
