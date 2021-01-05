package com.example.projectmanagementsystem.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.example.projectmanagementsystem.model.Project;
import com.example.projectmanagementsystem.repository.ProjectRepository;
import com.example.projectmanagementsystem.service.IProjectService;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService {

	private final ProjectRepository projectRepository;

	@Override
	public List<Project> findAll() {
		return projectRepository.findAll();
	}

	@Override
	public Optional<Project> findById(Long id) {
		return this.projectRepository.findById(id);
	}

	@Override
	public Project create(Project project) {
		return projectRepository.save(project);
	}

	@Override
	public Project update(Long id, Project project) {
		return projectRepository.save(project);
	}

	@Override
	public void delete(Long id) {
		this.projectRepository.deleteById(id);
	}

	@Override
	public List<Project> findByEmployeeId(Long employeeId) {
		return projectRepository.findByEmployeeId(employeeId);
	}

}
