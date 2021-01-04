package com.example.projectmanagementsystem.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String name;

	@OneToMany(mappedBy = "project")
	private Set<Task> tasks;
	@OneToMany(mappedBy = "project")
	private Set<EmployeeProject> employeeProjects;
}
