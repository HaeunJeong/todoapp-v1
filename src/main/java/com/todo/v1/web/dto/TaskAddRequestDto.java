package com.todo.v1.web.dto;

import com.todo.v1.domain.task.Task;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
public class TaskAddRequestDto {

    private String author;
    private String content;
    private String status;

    @Builder
    public TaskAddRequestDto(String author, String content){
        this.author = author;
        this.content = content;
        this.status = "C";
    }

    public Task toEntity(){
        return Task.builder()
                .content(content)
                .author(author)
                .build();
    }
}
