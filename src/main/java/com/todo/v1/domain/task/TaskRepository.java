package com.todo.v1.domain.task;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Dao 와와 같은 DB Layer 접근자. JPA에서는 Repository 라 불리며 인터페이스로 생성됨.
//Entity 클래스와 같이 위치해야함.
//JpaRepository<Entity 클래스, PK타입> 을 상속하면 기본적인 CRUD 메소드가 생성됨.
public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findAllByAuthor(String author) throws IllegalArgumentException;
}
