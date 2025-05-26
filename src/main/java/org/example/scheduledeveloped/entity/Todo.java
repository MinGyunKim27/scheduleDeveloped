package org.example.scheduledeveloped.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * 할 일(Todo)을 표현하는 엔티티 클래스입니다.
 * 제목, 내용, 작성자(User)를 포함하며, 생성일과 수정일은 BaseEntity로부터 상속받습니다.
 */
@Getter
@Entity
@Table(name = "Todo")
public class Todo extends BaseEntity {

    /**
     * Todo 고유 ID (자동 증가).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Todo 제목.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Todo 상세 내용.
     */
    @Column(nullable = false, columnDefinition = "longtext")
    private String contents;

    /**
     * 이 Todo를 작성한 사용자.
     */
    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Todo 생성자.
     *
     * @param title Todo 제목
     * @param contents Todo 내용
     * @param user 작성자
     */
    public Todo(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    /**
     * JPA 기본 생성자 (프록시 생성을 위해 필요).
     */
    public Todo() {
    }

    /**
     * Todo의 제목과 내용을 수정합니다.
     *
     * @param title 수정할 제목
     * @param contents 수정할 내용
     */
    public void updateTodo(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
