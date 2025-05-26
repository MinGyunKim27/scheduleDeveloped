package org.example.scheduledeveloped.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.Common.Const;
import org.example.scheduledeveloped.dto.authDto.PasswordRequestDto;
import org.example.scheduledeveloped.dto.userDto.UpdateUserRequestDto;
import org.example.scheduledeveloped.dto.userDto.UserResponseDto;
import org.example.scheduledeveloped.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사용자(User) 관련 요청을 처리하는 컨트롤러입니다.
 * 사용자 조회, 수정, 삭제 등의 API 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 사용자 ID로 특정 사용자 정보를 조회합니다.
     *
     * @param id 조회할 사용자 ID
     * @return 조회된 사용자 정보를 담은 UserResponseDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id){
        UserResponseDto user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * 사용자 이름으로 특정 사용자 정보를 조회하거나, 전체 사용자 목록을 조회합니다.
     *
     * @param name (선택) 사용자 이름. 없으면 전체 사용자 목록 조회
     * @return 사용자 정보 혹은 사용자 목록
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
     * 사용자 정보를 수정합니다. 세션 사용자와 요청 ID가 일치하는지 확인한 후 수정 수행.
     *
     * @param id 수정할 사용자 ID
     * @param dto 수정 요청 정보 (이메일, 이름 등)
     * @param session 현재 로그인된 사용자 세션
     * @return 수정된 사용자 정보를 담은 UserResponseDto
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUsers(
            @PathVariable Long id,
            @Validated @RequestBody UpdateUserRequestDto dto,
            HttpSession session){

        UserResponseDto userResponseDto = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);
        UserResponseDto updateUser = userService.updateUser(id, dto, userResponseDto);

        // 세션 정보도 갱신
        session.setAttribute(Const.LOGIN_USER, updateUser);

        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    /**
     * 사용자를 삭제합니다. 비밀번호 검증을 거쳐 세션 사용자 본인만 삭제 가능.
     *
     * @param id 삭제할 사용자 ID
     * @param dto 비밀번호 요청 DTO
     * @param session 현재 로그인된 사용자 세션
     * @return HTTP 200 OK 응답
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            @RequestBody PasswordRequestDto dto,
            HttpSession session){

        UserResponseDto userResponseDto = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);
        userService.deleteUser(id, dto, userResponseDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 현재 로그인된 사용자 정보를 조회합니다.
     *
     * @param session 현재 세션
     * @return 로그인된 사용자 정보, 없으면 401 Unauthorized
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
