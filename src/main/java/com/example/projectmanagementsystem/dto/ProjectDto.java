package com.example.projectmanagementsystem.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ProjectDto {
	private Long id;
	@NotBlank
	private String name;
	private Set<TaskDto> tasks;
	private Set<EmployeeProjectDto> employeeProjects;
}
