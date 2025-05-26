package org.example.scheduledeveloped.repository;

import org.example.scheduledeveloped.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Todo 엔티티에 대한 데이터베이스 접근을 처리하는 JPA Repository 인터페이스입니다.
 */
public interface TodoRepository extends JpaRepository<Todo, Long> {

    /**
     * 제목에 특정 키워드가 포함된 Todo 목록을 조회합니다.
     *
     * @param title 포함되어야 할 제목 키워드
     * @return 조건에 일치하는 Todo 리스트
     */
    List<Todo> findByTitleContaining(String title);

    /**
     * 내용에 특정 키워드가 포함된 Todo 목록을 조회합니다.
     *
     * @param contents 포함되어야 할 내용 키워드
     * @return 조건에 일치하는 Todo 리스트
     */
    List<Todo> findByContentsContaining(String contents);

    /**
     * 제목과 내용 모두에 특정 키워드가 포함된 Todo 목록을 조회합니다.
     *
     * @param contents 포함되어야 할 내용 키워드
     * @param title 포함되어야 할 제목 키워드
     * @return 조건에 일치하는 Todo 리스트
     */
    List<Todo> findByContentsContainingAndTitleContaining(String contents, String title);

    /**
     * ID를 기준으로 Todo를 조회합니다.
     *
     * @param id 조회할 Todo ID
     * @return Optional 형태의 Todo
     */
    Optional<Todo> findTodoById(Long id);

    /**
     * ID를 기준으로 Todo를 조회하고, 존재하지 않으면 404 예외를 던집니다.
     *
     * @param id 조회할 Todo ID
     * @return 조회된 Todo 엔티티
     * @throws ResponseStatusException ID에 해당하는 Todo가 없을 경우 404 예외 발생
     */
    default Todo findTodoByIdOrElseThrow(Long id) {
        return findTodoById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "아이디에 일치하는 Todo가 없습니다!"));
    }

}
