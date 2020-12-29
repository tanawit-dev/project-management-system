package com.example.projectmanagementsystem.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.projectmanagementsystem.exception.NotFoundExcetion;
import com.example.projectmanagementsystem.model.Task;
import com.example.projectmanagementsystem.repository.TaskRepository;
import com.example.projectmanagementsystem.service.ITaskService;

@Service
public class TaskServiceImpl implements ITaskService {

	private TaskRepository taskRepository;

	public TaskServiceImpl(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Override
	public Iterable<Task> findAll() {
		return taskRepository.findAll();
	}

	@Override
	public Optional<Task> findById(Long id) {
		return taskRepository.findById(id);
	}

	@Override
	public Task create(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public Task update(Long id, Task task) {
		if (!taskRepository.existsById(id)) {
			throw new NotFoundExcetion();
		}
		return taskRepository.save(task);
	}

	@Override
	public void delete(Long id) {
		taskRepository.deleteById(id);
	}

}
