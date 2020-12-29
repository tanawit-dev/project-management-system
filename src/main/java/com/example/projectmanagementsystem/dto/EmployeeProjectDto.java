package com.example.projectmanagementsystem.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EmployeeProjectDto {
	private Long employeeId;
	private Long projectId;
	private EmployeeDto employee;
	private ProjectDto project;
	private LocalDateTime joinAt;
}
