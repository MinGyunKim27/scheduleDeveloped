package org.example.scheduledeveloped.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.config.PasswordEncoder;
import org.example.scheduledeveloped.dto.authDto.SignUpResponseDto;
import org.example.scheduledeveloped.dto.userDto.SessionUserResponseDto;
import org.example.scheduledeveloped.entity.User;
import org.example.scheduledeveloped.exception.PasswordMismatchException;
import org.example.scheduledeveloped.exception.UserNotFoundException;
import org.example.scheduledeveloped.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


/**
 *
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     *
     * @param email
     * @param password
     * @return
     */
    public SessionUserResponseDto login(String email, String password){
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("이메일에 해당하는 사용자가 없습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
        return SessionUserResponseDto.toDto(user);
    }


    /**
     *
     * @param userName
     * @param email
     * @param password
     * @return
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

        User user = new User(userName,email,encodedPassword);

        User saved = userRepository.save(user);

        return new SignUpResponseDto(saved.getId(),saved.getUserName(),saved.getEmail());
    }
}
