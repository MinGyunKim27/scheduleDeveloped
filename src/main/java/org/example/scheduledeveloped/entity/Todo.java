package org.example.scheduledeveloped.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


/**
 *
 */
@Getter
@Entity
@Table(name = "Todo")
public class Todo extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false,columnDefinition = "longtext")
    private String contents;


    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Todo(String title, String contents, User user) {
        this.title = title;
        this.contents = contents;
        this.user = user;
    }

    public Todo() {

    }

    public void updateTodo(String title, String contents){
        this.title = title;
        this.contents = contents;
    }
}