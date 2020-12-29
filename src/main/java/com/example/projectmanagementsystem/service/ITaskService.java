package com.example.projectmanagementsystem.service;

import java.util.Optional;

import com.example.projectmanagementsystem.model.Task;

public interface ITaskService {
	Iterable<Task> findAll();

	Optional<Task> findById(Long id);

	Task create(Task task);

	Task update(Long id, Task task);

	void delete(Long id);
}
