package com.example.projectmanagementsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.projectmanagementsystem.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {
	List<Project> findAll();
	
	@Query("SELECT p FROM Project p JOIN p.employeeProjects ep WHERE ep.id.employeeId = ?1")
	List<Project> findByEmployeeId(Long employeeId);
}
