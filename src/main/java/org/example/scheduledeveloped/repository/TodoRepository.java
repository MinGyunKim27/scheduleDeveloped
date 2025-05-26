package org.example.scheduledeveloped.repository;

import org.example.scheduledeveloped.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo,Long> {

    List<Todo> findByTitleContaining(String title);

    List<Todo> findByContentsContaining(String contents);

    List<Todo> findByContentsContainingAndTitleContaining(String contents, String title);

    Optional<Todo> findTodoById(Long id);

    default Todo findTodoByIdOrElseThrow(Long id){
        return findTodoById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"아이디에 일치하는 Todo가 없습니다!"));
    }

}


