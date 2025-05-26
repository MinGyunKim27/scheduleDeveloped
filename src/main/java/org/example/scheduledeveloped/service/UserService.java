package org.example.scheduledeveloped.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.config.PasswordEncoder;
import org.example.scheduledeveloped.dto.authDto.PasswordRequestDto;
import org.example.scheduledeveloped.dto.userDto.SessionUserResponseDto;
import org.example.scheduledeveloped.dto.userDto.UpdateUserRequestDto;
import org.example.scheduledeveloped.dto.userDto.UserResponseDto;
import org.example.scheduledeveloped.entity.User;
import org.example.scheduledeveloped.exception.PasswordMismatchException;
import org.example.scheduledeveloped.exception.UserNotFoundException;
import org.example.scheduledeveloped.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto findUserByUserName(String userName){
        User user = userRepository.findUserByUserName(userName).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다!"));;


        return new UserResponseDto(user.getId(),user.getEmail(),user.getUserName());
    }


    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다!"));;

        return new UserResponseDto(user.getId(),user.getEmail(),user.getUserName());
    }

    public SessionUserResponseDto findUserByIdContainsPassword(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다!"));;

        return new SessionUserResponseDto(user.getId(),user.getEmail(),user.getUserName(), user.getPassword());
    }

    public List<UserResponseDto> findAllUsers(){

        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::toDto)
                .toList();
    }

    @Transactional
    public SessionUserResponseDto updateUser(
            Long id,
            UpdateUserRequestDto dto,
            SessionUserResponseDto sessionUserResponseDto) {

        if (!passwordEncoder.matches(dto.getOldPassword(),sessionUserResponseDto.getPassword())){
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        User foundedUser = userRepository.findUserById(id).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다!"));
        String encodedNewPassword = passwordEncoder.encode(dto.getNewPassword());
        foundedUser.updateUserNameAndPassword(dto.getUserName(), encodedNewPassword);

        return SessionUserResponseDto.toDto(foundedUser);
    }

    @Transactional
    public void deleteUser(
            Long id,
            PasswordRequestDto dto,
            SessionUserResponseDto sessionUserResponseDto
    ) {

        if (!passwordEncoder.matches(dto.getPassword(),sessionUserResponseDto.getPassword())){
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        User byId = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다!"));
        userRepository.delete(byId);
    }
}
