package com.todo.v1.web;

import com.todo.v1.domain.task.Task;
import com.todo.v1.domain.task.TaskRepository;
import com.todo.v1.web.dto.TaskAddRequestDto;
import com.todo.v1.web.dto.TaskResponseDto;
import com.todo.v1.web.dto.TaskUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    @After
    public void cleanup() throws Exception{
        taskRepository.deleteAll();
    }

    @Test
    public void Task_추가하다() throws Exception{
        String content = "공부할거다";
        String author = "haeun";

        TaskAddRequestDto requestDto = TaskAddRequestDto.builder()
                .content(content)
                .author(author)
                .build();

        String url = "http://localhost:"+port+"/api/v1/addTask";

        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(url, requestDto, Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Task> taskList = taskRepository.findAll();
        assertThat(taskList.get(0).getAuthor()).isEqualTo(author);
        assertThat(taskList.get(0).getContent()).isEqualTo(content);

    }

    @Test
    public void Task_수정하다() throws Exception{
        //추가하기
        String content = "공부할거다";
        String author = "haeun";

        Task savedTask = taskRepository.save(TaskAddRequestDto.builder().content(content).author(author).build().toEntity());

        Long updateId = taskRepository.findAll().get(0).getId();
        String newContent = "공부다함";
        String status = "Done";
        TaskUpdateRequestDto requestDto = TaskUpdateRequestDto.builder()
                                                .content(newContent)
                                                .status(status)
                                                .build();

        String url = "http://localhost:"+port+"/api/v1/updateTask/"+updateId;
        HttpEntity<TaskUpdateRequestDto> requestDtoHttpEntity = new HttpEntity<>(requestDto); //Put Method Body??

        //호출
        ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,requestDtoHttpEntity,Void.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Task> taskList = taskRepository.findAllByAuthor(author);
        assertThat(taskList.get(0).getContent()).isEqualTo(newContent);
        assertThat(taskList.get(0).getStatus()).isEqualTo(status);

    }

    @Test
    public void Task_삭제하다() throws Exception{
        //추가하기
        String content = "공부할거다";
        String author = "haeun";

        Task savedTask = taskRepository.save(TaskAddRequestDto.builder().content(content).author(author).build().toEntity());

        Long deleteId = taskRepository.findAll().get(0).getId();
        String url = "http://localhost:"+port+"/api/v1/deleteTask/"+deleteId;
        ResponseEntity<Void> responseEntity = restTemplate.getForEntity(url,Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(taskRepository.findById(deleteId)).isEmpty();

    }

    @Test
    public void Task_조회하다() throws Exception{

        String author = "testUser";
        String content1 = "로그인개발";
        String content2 = "세션저장";
        Task task1 = taskRepository.save(TaskAddRequestDto.builder().author(author).content(content1).build().toEntity());
        Task task2 = taskRepository.save(TaskAddRequestDto.builder().author(author).content(content2).build().toEntity());

        String url = "http://localhost:"+port+"/api/v1/allTask/"+author;
        ResponseEntity<TaskResponseDto[]> responseEntity = restTemplate.getForEntity(url, TaskResponseDto[].class);
        List<TaskResponseDto> responseList = Arrays.asList(responseEntity.getBody());
        //restTemplate.getForEntity 반환 객체가 List로 선언해도 LinkedHashMap 형식으로 나오기에, 에러발생의 여지가 있으므로,
        //배열로 받아준다음, 이걸 다시 List로 바꾼다.

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotEmpty();
        assertThat(responseList.get(0).getAuthor()).isEqualTo(author);
        assertThat(responseList.get(0).getContent()).isEqualTo(content1);
        assertThat(responseList.get(1).getContent()).isEqualTo(content2);

    }

}
