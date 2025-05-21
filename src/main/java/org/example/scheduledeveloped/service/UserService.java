package org.example.scheduledeveloped.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduledeveloped.dto.SignUpResponseDto;
import org.example.scheduledeveloped.dto.UpdateUserRequestDto;
import org.example.scheduledeveloped.dto.UserResponseDto;
import org.example.scheduledeveloped.entity.User;
import org.example.scheduledeveloped.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public SignUpResponseDto save(String userName, String email, String password){
        User user = new User(userName,email,password);

        User saved = userRepository.save(user);

        return new SignUpResponseDto(saved.getId(),saved.getUserName(),saved.getEmail());
    }

    public UserResponseDto findUserByUserName(String userName){
        User user = userRepository.findMemberByUserNameOrElseThrow(userName);


        return new UserResponseDto(user.getId(),user.getEmail(),user.getUserName());
    }


    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findByIdOrElseThrow(id);

        return new UserResponseDto(user.getId(),user.getEmail(),user.getUserName());
    }

    public List<UserResponseDto> findAllUsers(){

        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::toDto)
                .toList();
    }

    public UserResponseDto updateUser(UpdateUserRequestDto requestDto, String newUserName,String newPassword) {
        User foundedUser = userRepository.findUserByUserNameAndPasswordOrElseThrow(requestDto.getUserName(), requestDto.getPassword());

        foundedUser.updateUserNameAndPassword(newUserName,newPassword);

        return UserResponseDto.toDto(foundedUser);
    }


    public void deleteUser(Long id) {
        User byId = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User가 존재하지 않습니다."));
        userRepository.delete(byId);
    }

    public UserResponseDto login(String email, String password){
        User loginUser = userRepository.findUserByUserEmailAndPasswordOrElseThrow(email,password);

        return UserResponseDto.toDto(loginUser);
    }
}
