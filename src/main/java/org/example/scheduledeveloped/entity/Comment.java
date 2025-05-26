package org.example.scheduledeveloped.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "comment")
public class Comment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment(String contents, Todo todo, User user) {
        this.contents = contents;
        this.todo = todo;
        this.user = user;
    }

    public Comment() {
    }

    public void updateComment(String contents){
        this.contents = contents;
    }
}
