package com.example.projectmanagementsystem.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.projectmanagementsystem.dto.EmployeeDto;
import com.example.projectmanagementsystem.dto.ProjectDto;
import com.example.projectmanagementsystem.dto.TaskDto;
import com.example.projectmanagementsystem.exception.NotFoundExcetion;
import com.example.projectmanagementsystem.model.Employee;
import com.example.projectmanagementsystem.model.Project;
import com.example.projectmanagementsystem.model.Task;
import com.example.projectmanagementsystem.service.IEmployeeService;
import com.example.projectmanagementsystem.service.IProjectService;
import com.example.projectmanagementsystem.service.ITaskService;
import com.example.projectmanagementsystem.type.EmployeeRole;

@Controller
@RequestMapping("/projects")
public class ProjectController {

	private IProjectService projectService;
	private ITaskService taskService;
	private ModelMapper modelMapper;
	private UserDetailsService userDetailsService;
	private IEmployeeService employeeService;

	public ProjectController(IProjectService projectService, ModelMapper modelMapper, ITaskService taskService,
			UserDetailsService userDetailsService, IEmployeeService employeeService) {
		this.projectService = projectService;
		this.modelMapper = modelMapper;
		this.taskService = taskService;
		this.userDetailsService = userDetailsService;
		this.employeeService = employeeService;
	}

	@GetMapping
	public String getProjects(Model model, Principal principal) {
		String username = principal.getName();
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		boolean isManager = userDetails.getAuthorities().stream()
				.anyMatch(u -> u.getAuthority().equals(EmployeeRole.MANAGER.name()));
		List<Project> projects = new ArrayList<>();
		if (isManager) {
			projects = projectService.findAll();
		} else {
			Employee employee = employeeService.findByUsername(username);
			projects = projectService.findByEmployeeId(employee.getId());
		}
		List<ProjectDto> projectDtos = new ArrayList<>();
		projects.forEach(p -> projectDtos.add(convertToDto(p)));
		model.addAttribute("projects", projectDtos);
		return "projects";
	}

	@GetMapping("/new")
	public String newProject(Model model) {
		model.addAttribute("project", new ProjectDto());
		return "new-project";
	}

	@GetMapping("/{id}/edit")
	public String editProject(Model model, @PathVariable Long id) {
		Project project = projectService.findById(id).orElseThrow(NotFoundExcetion::new);
		model.addAttribute("project", convertToDto(project));
		return "edit-project";
	}

	@GetMapping("/{id}")
	public String getProject(@PathVariable Long id, Model model) {
		Project project = projectService.findById(id).orElseThrow(NotFoundExcetion::new);
		model.addAttribute("project", convertToDto(project));
		return "project";
	}

	@PostMapping
	public String createProject(@Valid @ModelAttribute("project") ProjectDto projectDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "new-project";
		}

		projectService.create(convertToEntity(projectDto));
		return "redirect:/projects";
	}

	@PostMapping("/{id}")
	public String updateProject(@PathVariable Long id, @Valid @ModelAttribute("project") ProjectDto projectDto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "project";
		}

		projectService.update(id, convertToEntity(projectDto));
		return "redirect:/projects";
	}

	@GetMapping("/{id}/delete")
	public String deleteProject(@PathVariable Long id) {
		projectService.delete(id);
		return "redirect:/projects";
	}

	@GetMapping("/{id}/tasks/{taskId}")
	public String getTask(@PathVariable Long taskId, Model model, @PathVariable("id") Long projectId) {
		Task task = taskService.findById(taskId).orElseThrow(NotFoundExcetion::new);
		Project project = projectService.findById(projectId).orElseThrow(NotFoundExcetion::new);

		model.addAttribute("task", convertToDto(task));
		model.addAttribute("project", convertToDto(project));
		return "task-detail";
	}

	@GetMapping("/{id}/tasks/new")
	public String newTask(Model model, @PathVariable(name = "id") Long projectId) {
		Project project = projectService.findById(projectId).orElseThrow(NotFoundExcetion::new);

		model.addAttribute("task", new TaskDto());
		model.addAttribute("project", convertToDto(project));
		return "new-task";
	}

	@GetMapping("{id}/tasks/{taskId}/edit")
	public String editTask(Model model, @PathVariable Long taskId, @PathVariable("id") Long projectId) {
		Project project = projectService.findById(projectId).orElseThrow(NotFoundExcetion::new);
		Task task = taskService.findById(taskId).orElseThrow(NotFoundExcetion::new);
		Set<EmployeeDto> employeeDtos = employeeService.findAll().stream().map(this::convertToDto)
				.collect(Collectors.toSet());
		model.addAttribute("task", convertToDto(task));
		model.addAttribute("project", convertToDto(project));
		model.addAttribute("employees", employeeDtos);
		return "edit-task";
	}

	@PostMapping("/{id}/tasks")
	public String createTask(@Valid @ModelAttribute("task") TaskDto taskDto, BindingResult bindingResult,
			@PathVariable Long id, @ModelAttribute("project") ProjectDto projectDto) {
		if (bindingResult.hasErrors()) {
			return "new-task";
		}

		Project project = projectService.findById(id).orElseThrow(NotFoundExcetion::new);
		Task task = convertToEntity(taskDto);
		task.setProject(project);
		taskService.create(task);

		return "redirect:/projects/" + id;
	}

	/**
	 * FIXME: เรื่อง คำนวนเวลาทำงาน
	 * 
	 * @param task
	 * @param bindingResult
	 * @param taskId
	 * @param id
	 * @return
	 */
	@PostMapping("/{id}/tasks/{taskId}")
	public String updateTask(@Valid @ModelAttribute("task") TaskDto taskDto, BindingResult bindingResult,
			@PathVariable Long taskId, @PathVariable Long id) {
		if (bindingResult.hasErrors()) {
			return "edit-task";
		}

		Project project = projectService.findById(id).orElseThrow(NotFoundExcetion::new);
		Employee assignedEmployee = employeeService.findById(taskDto.getAssignedId()).orElseThrow(NotFoundExcetion::new);
		Task task = convertToEntity(taskDto);
		task.setProject(project);
		task.setEmployee(assignedEmployee);
		taskService.update(taskId, task);
		return "redirect:/projects/" + id;
	}

	/**
	 * FIXME: เรื่องห้ามลบ task ที่ complete แล้ว
	 * 
	 * @param taskId
	 * @return
	 */
	@GetMapping("/{id}/tasks/{taskId}/delete")
	public String deleteTask(@PathVariable Long taskId, @PathVariable Long id) {
//		Task task = taskService.findById(taskId).orElse(null);
//		if (task == null) {
//			return "not-found";
//		}
//		
//		TaskDto taskDto = convertToDto(task);
//		if (taskDto.isCompleted()) {
//			throw new 
//		}
		taskService.delete(taskId);
		return "redirect:/projects/" + id;
	}

	private ProjectDto convertToDto(Project project) {
		ProjectDto projectDto = modelMapper.map(project, ProjectDto.class);
		if (project.getTasks() != null) {
			projectDto.setTasks(project.getTasks().stream().map(p -> convertToDto(p)).collect(Collectors.toSet()));
		}
//		ProjectDto projectDto = new ProjectDto();
//		projectDto.setId(project.getId());
//		projectDto.setName(project.getName());
//		if (project.getTasks() != null) {
//			projectDto.setTasks(project.getTasks().stream().map(this::convertToDto).collect(Collectors.toSet()));
//		}
		return projectDto;
	}

	private Project convertToEntity(ProjectDto projectDto) {
		Project project = modelMapper.map(projectDto, Project.class);
		if (projectDto.getTasks() != null) {
			project.setTasks(projectDto.getTasks().stream().map(p -> convertToEntity(p)).collect(Collectors.toSet()));
		}
		return project;
	}

	private TaskDto convertToDto(Task task) {
		TaskDto taskDto = modelMapper.map(task, TaskDto.class);
		if (task.getEmployee() != null) {
			taskDto.setAssignedId(task.getEmployee().getId());
		}
		return taskDto;
	}

	private Task convertToEntity(TaskDto taskDto) {
		Task task = modelMapper.map(taskDto, Task.class);
		return task;
	}

	private EmployeeDto convertToDto(Employee employee) {
		EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
		return employeeDto;
	}

	private Employee convertToEntity(Employee employeeDto) {
		Employee employee = modelMapper.map(employeeDto, Employee.class);
		return employee;
	}

}
