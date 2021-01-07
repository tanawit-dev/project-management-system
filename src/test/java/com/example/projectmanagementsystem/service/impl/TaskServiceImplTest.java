package com.example.projectmanagementsystem.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.projectmanagementsystem.model.Task;
import com.example.projectmanagementsystem.repository.TaskRepository;
import com.example.projectmanagementsystem.service.ITaskService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class TaskServiceImplTest {

    @Autowired
    ITaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    private final int INIT_SIZE = 3;

    @BeforeEach
    public void beforeEach() {
        for (int i = 1; i <= INIT_SIZE; i++) {
            Task task = new Task();
            task.setName("task " + i);
            taskRepository.save(task);
        }
    }

    @AfterEach
    public void afterEach() {
        taskRepository.deleteAll();
    }

    @Test
    @DisplayName("Should find all task then success")
    public void shouldFindAll_thenSuccess() {
        List<Task> tasks = taskService.findAll();
        assertNotNull(tasks);
        assertEquals(INIT_SIZE, tasks.size());
    }

    @Test
    @DisplayName("Should find by task id then success")
    public void shouldFindByTaskId_thenSuccess() {
        String name = "task";
        String detail = "detail";
        LocalDateTime beginAt = LocalDateTime.now();
        LocalDateTime finishAt = beginAt.plusDays(1);
        Task task = new Task();
        task.setName(name);
        task.setDetail(detail);
        task.setBeginAt(beginAt);
        task.setFinishAt(finishAt);

        Long id = taskService.create(task).getId();
        Optional<Task> taskOptional = taskService.findById(id);

        assertTrue(taskOptional.isPresent());
        task = taskOptional.get();

        assertEquals(name, task.getName());
        assertEquals(detail, task.getDetail());
        assertEquals(beginAt, task.getBeginAt());
        assertEquals(finishAt, task.getFinishAt());
    }

    @Test
    @DisplayName("Should create task then succes")
    public void shouldCreateTask_thenSuccess() {
        int count = taskService.findAll().size();
        Task task = new Task();
        task.setName("task");

        Task createdTask = taskService.create(task);

        assertEquals(count + 1, taskService.findAll().size());
        assertNotNull(createdTask);
        assertNotNull(createdTask.getId());
    }

    @Test
    @DisplayName("Should update task then success")
    public void shouldUpdateTask_thenSuccess() {
        Task task = taskService.findAll().get(0);
        String name = "task edit";
        String detail = "detail edit";
        LocalDateTime beginAt = LocalDateTime.now();
        LocalDateTime finishAt = beginAt.plusDays(1);

        task.setName(name);
        task.setDetail(detail);
        task.setBeginAt(beginAt);
        task.setFinishAt(finishAt);

        taskService.update(task.getId(), task);
        Optional<Task> retrivedTaskOpt = taskService.findById(task.getId());
        assertTrue(retrivedTaskOpt.isPresent());
        Task retrivedTask = retrivedTaskOpt.get();
    }
}
