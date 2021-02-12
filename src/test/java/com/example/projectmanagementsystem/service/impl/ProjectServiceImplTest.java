package com.example.projectmanagementsystem.service.impl;

import com.example.projectmanagementsystem.model.Project;
import com.example.projectmanagementsystem.repository.ProjectRepository;
import com.example.projectmanagementsystem.service.IProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ProjectServiceImplTest {

    @Autowired
    IProjectService projectService;

    @Autowired
    ProjectRepository projectRepository;

    @BeforeEach
    public void cleanup() {
        projectRepository.deleteAll();
    }

    @Test
    @DisplayName("Should list all project and then success")
    public void shouldFindAll_thenSuccess() {
        persistToDatabase(
                new Project("project 1"),
                new Project("project 2"),
                new Project("project 3")
        );

        List<Project> projects = projectService.findAll();

        assertThat(projects.size()).isEqualTo(3);
        assertThat(projects)
                .hasSize(3)
                .extracting("name")
                .containsExactly("project 1", "project 2", "project 3");
    }

    @Test
    @DisplayName("Should find by project id and then success")
    public void shouldFindByProjectId_thenSuccess() {
        String projectName = "project 1";
        Project expectedProject = new Project();
        expectedProject.setName(projectName);

        Optional<Project> actuallyProject = projectService.findById(projectService.create(expectedProject).getId());

        assertThat(actuallyProject.isPresent()).isTrue();
        assertThat(actuallyProject.get().getId()).isNotNull();
        assertThat(actuallyProject.get().getName()).isEqualTo(expectedProject.getName());
    }

    @Test
    @DisplayName("Should not found any project when given non existing id")
    public void shouldNotFound_whenGivenNotExistingId() {
        Optional<Project> nonExistingProject = projectService.findById(new Random().nextLong());

        assertThat(nonExistingProject.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Should create project and then success")
    public void shouldCreateProject_thenSuccess() {
        String projectName = "project 1";
        Project expectedProject = new Project();
        expectedProject.setName(projectName);

        Project actuallyProject = projectService.create(expectedProject);

        assertThat(actuallyProject).isNotNull();
        assertThat(actuallyProject.getId()).isNotZero();
        assertThat(actuallyProject.getName()).isEqualTo(expectedProject.getName());
    }

    @Test
    @DisplayName("Should update project ane then success")
    public void shouldUpdateProject_thenSuccess() {
        String projectName = "project 1";
        Project expectedProject = new Project();
        expectedProject.setName(projectName);

        expectedProject = projectService.create(expectedProject);
        expectedProject.setName(projectName + " edit");
        projectService.update(expectedProject.getId(), expectedProject);
        Optional<Project> actuallyProject = projectService.findById(expectedProject.getId());

        assertThat(actuallyProject.isPresent()).isTrue();
        assertThat(actuallyProject.get().getId()).isEqualTo(expectedProject.getId());
        assertThat(actuallyProject.get().getName()).isEqualTo(expectedProject.getName());
    }

    @Test
    @DisplayName("Should delete project success")
    public void shouldDeleteProject_thenSuccess() {
        Project expectedProject = new Project("project 1");

        expectedProject = projectService.create(expectedProject);
        projectService.delete(expectedProject.getId());
        Optional<Project> actuallyProject = projectService.findById(expectedProject.getId());

        assertThat(projectRepository.count()).isEqualTo(0);
        assertThat(actuallyProject.isPresent()).isFalse();
    }

    private void persistToDatabase(Project... projects) {
        projectRepository.saveAll(Arrays.asList(projects));
    }

}
