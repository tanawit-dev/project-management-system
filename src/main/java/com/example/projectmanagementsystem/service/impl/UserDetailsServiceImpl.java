package com.example.projectmanagementsystem.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.projectmanagementsystem.dto.UserPrincipal;
import com.example.projectmanagementsystem.model.Employee;
import com.example.projectmanagementsystem.repository.EmployeeRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private EmployeeRepository employeeRepository;

	public UserDetailsServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findByUsername(username);
		if (employee == null) {
			throw new UsernameNotFoundException("Not found user: " + username);
		}
		return new UserPrincipal(employee);
	}

}
