package com.example.projectmanagementsystem.service;

import com.example.projectmanagementsystem.model.Project;

import java.util.List;
import java.util.Optional;

public interface IProjectService {
    List<Project> findAll();

    Optional<Project> findById(Long id);

    List<Project> findByEmployeeId(Long employeeId);

    Project create(Project project);

    Project update(Long id, Project project);

    void delete(Long id);
}
