package com.todo.v1.domain.task;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
//@Builder //빌더는 아래에 생성자를 만들고 그 상단에 선언시, 생성자에 포함된 필드만 빌더에 포함.
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String task;

    @Column(length = 1, nullable = false)
    private String status;

    @Builder
    public Task(String author, String task){
        this.author = author;
        this.task = task;
        this.status = "C";
    }

}
