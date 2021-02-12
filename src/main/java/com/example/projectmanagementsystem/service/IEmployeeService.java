package com.example.projectmanagementsystem.service;

import com.example.projectmanagementsystem.model.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    List<Employee> findAll();

    Optional<Employee> findById(Long id);

    Employee findByUsername(String username);

    Employee create(Employee employee);

    Employee update(Long id, Employee employee);

    void delete(Long id);
}
