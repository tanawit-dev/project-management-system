package com.example.projectmanagementsystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmployeeProjectDto {

    private Long employeeId;

    private Long projectId;

    private LocalDateTime joinAt;

}
