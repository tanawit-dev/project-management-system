package com.example.projectmanagementsystem.service.impl;

import com.example.projectmanagementsystem.model.Project;
import com.example.projectmanagementsystem.repository.ProjectRepository;
import com.example.projectmanagementsystem.service.IProjectService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class ProjectServiceImplTest {

    @Autowired
    IProjectService projectService;
    @Autowired
    ProjectRepository projectRepository;
    private List<Project> projects;

    @BeforeEach
    public void beforeEach() {
        Project project1 = new Project();
        project1.setName("project1");
        Project project2 = new Project();
        project2.setName("project2");
        Project project3 = new Project();
        project3.setName("project3");
        projects = new ArrayList<>();
        projects.add(project1);
        projects.add(project2);
        projects.add(project3);

        projects.forEach(p -> projectService.create(p));
    }

    @AfterEach
    public void afterEach() {
        projectRepository.deleteAll();
    }

    @Test
    @DisplayName("Should list all project and then success")
    public void shouldFindAll_thenSuccess() {
        List<Project> retrivedProjects = projectService.findAll();
        Assertions.assertEquals(3, retrivedProjects.size());
    }

    @Test
    @DisplayName("Should find by project id and then success")
    public void shouldFindByProjectId_thenSuccess() {
        String projectName = "p1";
        Project newProject = new Project();
        newProject.setName(projectName);

        newProject = projectService.create(newProject);
        Optional<Project> retrivedProjectOpt = projectService.findById(newProject.getId());

        Assertions.assertTrue(retrivedProjectOpt.isPresent());
        Project retrivedProject = retrivedProjectOpt.get();
        Assertions.assertEquals(retrivedProject.getName(), projectName);
    }

    @Test
    @DisplayName("Should create project and then success")
    public void shouldCreateProject_thenSuccess() {
        Project p1 = new Project();
        p1.setName("p1");

        Project createdProject = projectService.create(p1);

        Assertions.assertNotNull(createdProject);
        Assertions.assertNotNull(createdProject.getId());
        Assertions.assertEquals("p1", createdProject.getName());
    }

    @Test
    @DisplayName("Should update project ane then success")
    public void shouldUpdateProject_thenSuccess() {
        Project p = new Project();
        p.setName("p1");
        p = projectService.create(p);
        p.setName("p1 edit");

        projectService.update(p.getId(), p);
        Optional<Project> retrivedProjectOpt = projectService.findById(p.getId());

        Assertions.assertTrue(retrivedProjectOpt.isPresent());
        Project retrivedProject = retrivedProjectOpt.get();
        Assertions.assertEquals("p1 edit", retrivedProject.getName());
    }

    @Test
    @DisplayName("Should delete project success")
    public void shouldDeleteProject_thenSuccess() {
        Project p = new Project();
        p.setName("project name");

        Project createdProject = projectService.create(p);
        int count = projectService.findAll().size();
        projectService.delete(createdProject.getId());

        Assertions.assertEquals(projectService.findAll().size(), count - 1);
        Assertions.assertFalse(projectService.findById(createdProject.getId()).isPresent());
    }

}
