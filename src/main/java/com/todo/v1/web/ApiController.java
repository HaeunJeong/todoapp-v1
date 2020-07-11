package com.todo.v1.web;

import com.todo.v1.service.TaskService;
import com.todo.v1.web.dto.TaskAddRequestDto;
import com.todo.v1.web.dto.TaskResponseDto;
import com.todo.v1.web.dto.TaskUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiController {

    private final TaskService taskService;

    /*
    * description: TODO리스트에 task를 추가한다.
    * method: POST
    * url: /api/v1/addTask
    * param: author(String), task(String)
    * return: void
    */
    @PostMapping("/api/v1/addTask")
    public void addTask(@RequestBody TaskAddRequestDto requestDto){
        taskService.addTask(requestDto);
    }

    /*
     * description: TODO리스트의 task의 content 및 상태(status)를 업데이트 한다.
     * method: PUT
     * url: /api/v1/updateTask/{id}
     * param: id(Long), TaskUpdateRequestDto(task, status)
     * return: void
     */
    @PutMapping("/api/v1/updateTask/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody TaskUpdateRequestDto requestDto){
        taskService.update(id,requestDto);
    }

    @GetMapping("/api/v1/deleteTask/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.delete(id);
    }

    /*
     * description: TODO리스트의 task들을 가져온다.
     * method: GET
     * url: /api/v1/allTask
     * param: author(String)
     * return: TaskResponseDto
     */
    @GetMapping("/api/v1/allTask/{author}")
    public List<TaskResponseDto> findAll(@PathVariable String author){
        return taskService.findAll(author);
    }


}
