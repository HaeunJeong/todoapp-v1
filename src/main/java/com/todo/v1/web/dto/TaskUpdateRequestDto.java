package com.todo.v1.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TaskUpdateRequestDto {
    private String content;
    private String status;

    @Builder
    public TaskUpdateRequestDto(String content, String status){
        this.content = content;
        this.status = status;
    }
}
