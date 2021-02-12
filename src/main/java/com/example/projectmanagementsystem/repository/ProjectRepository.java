package com.example.projectmanagementsystem.repository;

import com.example.projectmanagementsystem.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT p FROM Project p JOIN p.employeeProjects ep WHERE ep.employee.id = ?1")
    List<Project> findByEmployeeId(Long employeeId);
}
