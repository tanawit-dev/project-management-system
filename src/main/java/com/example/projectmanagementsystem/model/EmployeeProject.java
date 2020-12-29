package com.example.projectmanagementsystem.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;

@Data
@Entity
public class EmployeeProject {
	@EmbeddedId
	private EmployeeProjectKey id;
	
	@ManyToOne
	@MapsId("employeeId")
	@JoinColumn(name = "employee_id")
	private Employee employee;
	
	@ManyToOne
	@MapsId("projectId")
	@JoinColumn(name = "project_id")
	private Project project;
	
	@Column
	private LocalDateTime joinAt;
}
