package com.example.projectmanagementsystem.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Data;

@Data
@Entity
public class EmployeeProject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private LocalDateTime joinAt;

	@ManyToOne
	@MapsId("employeeId")
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@ManyToOne
	@MapsId("projectId")
	@JoinColumn(name = "project_id")
	private Project project;
}
