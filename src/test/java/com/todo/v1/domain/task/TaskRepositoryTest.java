package com.todo.v1.domain.task;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @After
    public void cleanup(){
        taskRepository.deleteAll();
    }

    @Test
    public void Task_생성하기(){
        String content = "공부하기!! ";
        String author = "haeun";

        taskRepository.save(Task.builder()
                .content(content)
                .author(author)
                .build()
        );

        //When
        List<Task> taskList = taskRepository.findAll();

        //Then
        Task thisTask = taskList.get(0);
        assertThat(thisTask.getContent()).isEqualTo(content);
        assertThat(thisTask.getAuthor()).isEqualTo(author);


    }
}
