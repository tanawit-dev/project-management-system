package com.example.projectmanagementsystem.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class EmployeeProjectKey implements Serializable {
	@Column(name = "employee_id")
	private Long employeeId;
	@Column(name = "project_id")
	private Long projectId;
}
