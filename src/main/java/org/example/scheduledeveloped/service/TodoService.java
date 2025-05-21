package org.example.scheduledeveloped.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.scheduledeveloped.dto.TodoResponseDto;
import org.example.scheduledeveloped.entity.Todo;
import org.example.scheduledeveloped.entity.User;
import org.example.scheduledeveloped.repository.TodoRepository;
import org.example.scheduledeveloped.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;


    @Transactional
    public TodoResponseDto save(String title, String contents, String userName){

        log.info("조회 시도 userName: {}", userName);

        User user = userRepository.findMemberByUserNameOrElseThrow(userName);

        Todo todo = new Todo(title,contents);
        todo.setUser(user);

        todoRepository.save(todo);

        return new TodoResponseDto(todo.getId(),todo.getTitle(),todo.getContents());
    }

    public List<TodoResponseDto> findAll(){
        return todoRepository.findAll()
                .stream()
                .map(TodoResponseDto::toDto)
                .toList();
    }
}
