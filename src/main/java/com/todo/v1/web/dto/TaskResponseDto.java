package com.todo.v1.web.dto;

import com.todo.v1.domain.task.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TaskResponseDto {

    private Long id;
    private String author;
    private String content;
    private String status;

    //Response DTO 는 Entity의 필드에서 값을 받아 생성한다.
    public TaskResponseDto(Task entity){
        this.id = entity.getId();
        this.author = entity.getAuthor();
        this.content = entity.getContent();
        this.status = entity.getStatus();
    }

}
