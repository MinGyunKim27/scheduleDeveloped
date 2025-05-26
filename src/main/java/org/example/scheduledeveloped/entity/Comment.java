package org.example.scheduledeveloped.entity;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * 댓글(Comment)에 해당하는 엔티티 클래스입니다.
 * 각 댓글은 특정 Todo 항목과 사용자(User)에 연관되어 있으며, 댓글 내용을 포함합니다.
 * BaseEntity를 상속받아 생성일, 수정일 등의 공통 필드를 포함합니다.
 */
@Entity
@Getter
@Table(name = "comment")
public class Comment extends BaseEntity {

    /**
     * 댓글의 고유 ID (자동 생성, 기본 키)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 댓글 내용 (null 불가)
     */
    @Column(nullable = false)
    private String contents;

    /**
     * 댓글이 속한 Todo 항목 (다대일 관계, null 불가)
     */
    @ManyToOne
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    /**
     * 댓글 작성자 (다대일 관계, null 불가)
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 댓글 생성자
     *
     * @param contents 댓글 내용
     * @param todo     연관된 Todo 객체
     * @param user     댓글 작성자(User)
     */
    public Comment(String contents, Todo todo, User user) {
        this.contents = contents;
        this.todo = todo;
        this.user = user;
    }

    /**
     * JPA에서 사용하는 기본 생성자 (직접 호출 금지)
     */
    public Comment() {
    }

    /**
     * 댓글 내용을 수정하는 메서드
     *
     * @param contents 수정할 댓글 내용
     */
    public void updateComment(String contents){
        this.contents = contents;
    }
}
