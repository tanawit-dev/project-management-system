package com.example.projectmanagementsystem.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.example.projectmanagementsystem.type.EmployeeRole;

import lombok.Data;

@Data
@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private EmployeeRole role;

	@Column(unique = true)
	private String username;

	private String password;

	@OneToMany(mappedBy = "employee")
	private Set<Task> tasks;

	@OneToMany(mappedBy = "employee")
	private Set<EmployeeProject> employeeProjects;
}
