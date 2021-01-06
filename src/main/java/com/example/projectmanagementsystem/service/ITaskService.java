package com.example.projectmanagementsystem.service;

import java.util.List;
import java.util.Optional;

import com.example.projectmanagementsystem.model.Task;

public interface ITaskService {
	List<Task> findAll();

	Optional<Task> findById(Long id);

	Task create(Task task);

	Task update(Long id, Task task);

	Task logWork(Long id, Task task);

	void delete(Long id);
}
