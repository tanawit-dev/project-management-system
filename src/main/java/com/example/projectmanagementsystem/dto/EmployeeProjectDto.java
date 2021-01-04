package com.example.projectmanagementsystem.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EmployeeProjectDto {

	private Long employeeId;

	private Long projectId;

	private LocalDateTime joinAt;

}
