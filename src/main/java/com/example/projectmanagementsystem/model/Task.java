package com.example.projectmanagementsystem.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column
	private LocalDateTime beginAt;

	@Column
	private LocalDateTime finishAt;

	@Column(updatable = false)
	@CreationTimestamp
	private LocalDateTime createAt;

	@Column
	private String detail;

	@EqualsAndHashCode.Exclude
	@ManyToOne
	@JoinColumn(name = "project_id")
	private Project project;

	@EqualsAndHashCode.Exclude
	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;
}
