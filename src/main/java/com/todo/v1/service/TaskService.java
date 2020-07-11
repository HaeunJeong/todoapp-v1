package com.todo.v1.service;

import com.todo.v1.domain.task.Task;
import com.todo.v1.domain.task.TaskRepository;
import com.todo.v1.web.dto.TaskAddRequestDto;
import com.todo.v1.web.dto.TaskResponseDto;
import com.todo.v1.web.dto.TaskUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    /*
    * @Autowired로 Bean을 주입 받는 대신, final로 필드를 선언하고,
    * @RequiredArgsConstructor 롬복 어노테이션을 이용하여, 생성자가 자동생성되게 함으로써
    * Bean을 주입받는다.
    * */

    @Transactional
    public void addTask(TaskAddRequestDto requestDto) {
        taskRepository.save(requestDto.toEntity());
    }

    @Transactional
    public void update(Long id, TaskUpdateRequestDto requestDto) {
        Task task = taskRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 Task가 없습니다. id="+id));
        task.update(requestDto.getContent(),requestDto.getStatus());
    }

    public List<TaskResponseDto> findAll(String author) {
        List<Task> taskList = taskRepository.findAllByAuthor(author);
        List<TaskResponseDto> response = new ArrayList<TaskResponseDto>();
        for(Task i : taskList){
            response.add(new TaskResponseDto(i));
        }
        return response;
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}