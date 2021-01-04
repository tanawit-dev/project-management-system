package com.example.projectmanagementsystem.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.example.projectmanagementsystem.model.Employee;
import com.example.projectmanagementsystem.repository.EmployeeRepository;
import com.example.projectmanagementsystem.service.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	private EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> findById(Long id) {
		return employeeRepository.findById(id);
	}
	
	@Override
	public Employee findByUsername(String username) {
		return employeeRepository.findByUsername(username);
	}

	@Override
	public Employee create(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public Employee update(Long id, Employee employee) {
		if (!employeeRepository.findById(id).isPresent()) {
			throw new EntityNotFoundException();
		}
		return employeeRepository.save(employee);
	}

	@Override
	public void delete(Long id) {
		employeeRepository.deleteById(id);
	}

}
