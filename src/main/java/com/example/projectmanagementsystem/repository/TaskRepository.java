package com.example.projectmanagementsystem.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.projectmanagementsystem.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

}
