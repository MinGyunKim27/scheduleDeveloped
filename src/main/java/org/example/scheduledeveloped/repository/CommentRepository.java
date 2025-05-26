package org.example.scheduledeveloped.repository;

import org.example.scheduledeveloped.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Comment 엔티티에 대한 데이터베이스 접근을 처리하는 JPA Repository 인터페이스입니다.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 특정 Todo에 연결된 모든 댓글을 조회합니다.
     *
     * @param todoId 댓글을 조회할 Todo의 ID
     * @return 해당 Todo에 연결된 Comment 리스트
     */
    List<Comment> findAllByTodo_Id(Long todoId);
}
