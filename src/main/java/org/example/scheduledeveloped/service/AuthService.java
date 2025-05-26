package org.example.scheduledeveloped.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.config.PasswordEncoder;
import org.example.scheduledeveloped.dto.authDto.SignUpResponseDto;
import org.example.scheduledeveloped.dto.userDto.UserResponseDto;
import org.example.scheduledeveloped.entity.User;
import org.example.scheduledeveloped.exception.PasswordMismatchException;
import org.example.scheduledeveloped.exception.UserNotFoundException;
import org.example.scheduledeveloped.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * 인증 및 회원가입 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자의 로그인 요청을 처리합니다.
     * 이메일과 비밀번호가 일치하는 사용자가 존재하면 세션에 저장할 사용자 정보를 반환합니다.
     *
     * @param email    로그인하려는 사용자의 이메일
     * @param password 로그인하려는 사용자의 비밀번호(평문)
     * @return 로그인한 사용자 정보를 담은 SessionUserResponseDto
     * @throws UserNotFoundException       이메일에 해당하는 사용자가 없을 경우
     * @throws PasswordMismatchException   비밀번호가 일치하지 않을 경우
     */
    public UserResponseDto login(String email, String password){
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("이메일에 해당하는 사용자가 없습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        return UserResponseDto.toDto(user);
    }

    /**
     * 새로운 사용자의 회원가입 요청을 처리합니다.
     * 사용자 이름과 이메일 중복 여부를 검사한 후, 비밀번호를 암호화하여 사용자 정보를 저장합니다.
     *
     * @param userName 회원가입하려는 사용자의 이름
     * @param email    회원가입하려는 사용자의 이메일
     * @param password 회원가입하려는 사용자의 비밀번호(평문)
     * @return 가입된 사용자 정보를 담은 SignUpResponseDto
     * @throws ResponseStatusException 사용자 이름 또는 이메일이 이미 존재할 경우 409 CONFLICT 예외 발생
     */
    @Transactional
    public SignUpResponseDto signUp(String userName, String email, String password){
        String encodedPassword = passwordEncoder.encode(password);

        if (userRepository.countByUserName(userName) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"사용자 이름이 중복되었습니다!");
        }

        if (userRepository.countByEmail(email) > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"이메일이 중복되었습니다!");
        }

        User user = new User(userName, email, encodedPassword);

        User saved = userRepository.save(user);

        return new SignUpResponseDto(saved.getId(), saved.getUserName(), saved.getEmail());
    }
}
