package com.example.projectmanagementsystem.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.projectmanagementsystem.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	List<Employee> findAll();
	Employee findByUsername(String username);
}
