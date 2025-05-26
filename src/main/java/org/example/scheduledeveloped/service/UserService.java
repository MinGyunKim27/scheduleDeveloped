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


/**
 * 사용자 관련 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 사용자를 이름으로 찾아 리턴하는 메서드.
     * @param userName 사용자 이름
     * @return 사용자 응답 정보
     */
    public UserResponseDto findUserByUserName(String userName){
        User user = userRepository.findUserByUserName(userName).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다!"));;


        return new UserResponseDto(user.getId(),user.getEmail(),user.getUserName());
    }


    /**
     *사용자를 id 기반으로 찾아 리턴하는 메서드.
     * @param id 사용자 id
     * @return 사용자 응답 정보
     */
    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다!"));;

        return new UserResponseDto(user.getId(),user.getEmail(),user.getUserName());
    }


    /**
     * id 기반으로 찾은 사용자 정보를 password 정보를 포함하여 리턴하는 메서드
     * @param id 사용자 id
     * @return 사용자 응답 정보(세션 비교용)
     */
    public SessionUserResponseDto findUserByIdContainsPassword(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다!"));;

        return new SessionUserResponseDto(user.getId(),user.getEmail(),user.getUserName(), user.getPassword());
    }

    /**
     * 모든 사용자 조회 하는 메서드
     * @return 사용자 리스트
     */
    public List<UserResponseDto> findAllUsers(){

        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::toDto)
                .toList();
    }


    /**
     * id 기반으로 조회한 사용자를 수정하는 메서드.
     * @param id 사용자 id
     * @param dto 수정하고자 하는 사용자 정보
     * @param sessionUserResponseDto 비밀번호를 포함한 사용자 정보
     * @return 업데이트된 사용자 정보
     */
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


    /**
     * 사용자를 삭제하는 메서드
     * @param id 사용자 id
     * @param dto 세션에서 받아온 사용자 정보
     * @param sessionUserResponseDto password 를 포함하는 사용자 응답 정보
     */
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
