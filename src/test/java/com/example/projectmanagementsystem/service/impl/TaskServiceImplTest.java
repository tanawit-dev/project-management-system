package com.example.projectmanagementsystem.service.impl;

import com.example.projectmanagementsystem.model.Task;
import com.example.projectmanagementsystem.repository.TaskRepository;
import com.example.projectmanagementsystem.service.ITaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class TaskServiceImplTest {

    @Autowired
    ITaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    @BeforeEach
    public void cleanup() {
        taskRepository.deleteAll();
    }

    @Test
    @DisplayName("Should find all task then success")
    public void shouldFindAll_thenSuccess() {
        persistToDatabase(
                new Task("task 1"),
                new Task("task 2"),
                new Task("task 3")
        );

        List<Task> tasks = taskService.findAll();

        assertThat(tasks)
                .hasSize(3)
                .extracting("name")
                .containsExactly("task 1", "task 2", "task 3");
    }

    @Test
    @DisplayName("Should find by task id then success")
    public void shouldFindByTaskId_thenSuccess() {
        String name = "task 1";
        String detail = "task detail";
        LocalDateTime beginAt = LocalDateTime.now();
        LocalDateTime finishAt = beginAt.plusDays(1);
        Task expectedTask = new Task(name, beginAt, finishAt, detail);

        Long id = taskService.create(expectedTask).getId();
        Optional<Task> actuallyTask = taskService.findById(id);

        assertThat(actuallyTask.isPresent()).isTrue();
        assertThat(actuallyTask.get().getId()).isNotNull().isNotZero();
        assertThat(actuallyTask.get().getName()).isEqualTo(expectedTask.getName());
        assertThat(actuallyTask.get().getDetail()).isEqualTo(expectedTask.getDetail());
        assertThat(actuallyTask.get().getBeginAt()).isEqualToIgnoringSeconds(expectedTask.getBeginAt());
        assertThat(actuallyTask.get().getFinishAt()).isEqualToIgnoringSeconds(expectedTask.getFinishAt());
    }

    @Test
    @DisplayName("Should not found any task when given non existing id")
    public void shouldNotFound_whenGivenNonExistingId() {
        Optional<Task> nonExistingTask = taskService.findById(new Random().nextLong());

        assertThat(nonExistingTask.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Should create task then success")
    public void shouldCreateTask_thenSuccess() {
        String name = "task 1";
        String detail = "task detail";
        LocalDateTime beginAt = LocalDateTime.now();
        LocalDateTime finishAt = beginAt.plusDays(1);
        Task expectedTask = new Task(name, beginAt, finishAt, detail);

        Task actuallyTask = taskService.create(expectedTask);

        assertThat(actuallyTask).isNotNull();
        assertThat(actuallyTask.getId()).isNotNull().isNotZero();
        assertThat(actuallyTask.getName()).isEqualTo(expectedTask.getName());
        assertThat(actuallyTask.getDetail()).isEqualTo(expectedTask.getDetail());
        assertThat(actuallyTask.getBeginAt()).isEqualToIgnoringSeconds(expectedTask.getBeginAt());
        assertThat(actuallyTask.getFinishAt()).isEqualToIgnoringSeconds(expectedTask.getFinishAt());
        assertThat(actuallyTask.getCreateAt()).isBefore(LocalDateTime.now()).isAfter(LocalDateTime.now().minusMinutes(5));
    }

    @Test
    @DisplayName("Should update task then success")
    public void shouldUpdateTask_thenSuccess() {
        String name = "task 1";
        String detail = "task detail";
        LocalDateTime beginAt = LocalDateTime.now();
        LocalDateTime finishAt = beginAt.plusDays(1);
        Task expectedTask = new Task(name, beginAt, finishAt, detail);

        expectedTask = taskService.create(expectedTask);
        expectedTask.setName(name + " edit");
        expectedTask.setDetail(detail + " edit");
        expectedTask.setBeginAt(LocalDateTime.now().plusDays(5));
        expectedTask.setFinishAt(expectedTask.getBeginAt().plusDays(1));
        Task actuallyTask = taskService.update(expectedTask.getId(), expectedTask);

        assertThat(actuallyTask).isNotNull();
        assertThat(actuallyTask.getId()).isEqualTo(expectedTask.getId());
        assertThat(actuallyTask.getName()).isEqualTo(expectedTask.getName());
        assertThat(actuallyTask.getDetail()).isEqualTo(expectedTask.getDetail());
        assertThat(actuallyTask.getBeginAt()).isEqualToIgnoringSeconds(expectedTask.getBeginAt());
        assertThat(actuallyTask.getFinishAt()).isEqualToIgnoringSeconds(expectedTask.getFinishAt());
    }

    @Test
    @DisplayName("Should delete task success")
    public void shouldDeleteTask_thenSuccess() {
        Task expectedTask = new Task("task 1");

        expectedTask = taskService.create(expectedTask);
        taskService.delete(expectedTask.getId());
        Optional<Task> actuallyTask = taskService.findById(expectedTask.getId());

        assertThat(actuallyTask.isPresent()).isFalse();
        assertThat(taskRepository.count()).isZero();
    }

    private void persistToDatabase(Task... tasks) {
        taskRepository.saveAll(Arrays.asList(tasks));
    }
}
