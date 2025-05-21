package org.example.scheduledeveloped.repository;

import org.example.scheduledeveloped.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo,Long> {
}
