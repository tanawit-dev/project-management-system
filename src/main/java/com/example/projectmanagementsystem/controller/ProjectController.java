package com.example.projectmanagementsystem.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.projectmanagementsystem.dto.EmployeeDto;
import com.example.projectmanagementsystem.dto.ProjectDto;
import com.example.projectmanagementsystem.dto.TaskDto;
import com.example.projectmanagementsystem.exception.NotFoundExcetion;
import com.example.projectmanagementsystem.mapper.EmployeeMapper;
import com.example.projectmanagementsystem.mapper.ProjectMapper;
import com.example.projectmanagementsystem.mapper.TaskMapper;
import com.example.projectmanagementsystem.model.Employee;
import com.example.projectmanagementsystem.model.Project;
import com.example.projectmanagementsystem.model.Task;
import com.example.projectmanagementsystem.service.IEmployeeService;
import com.example.projectmanagementsystem.service.IProjectService;
import com.example.projectmanagementsystem.service.ITaskService;
import com.example.projectmanagementsystem.type.EmployeeRole;

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

@Controller
@RequestMapping("/projects")
public class ProjectController {

	private IProjectService projectService;
	private ITaskService taskService;
	private UserDetailsService userDetailsService;
	private IEmployeeService employeeService;
	private ProjectMapper projectMapper;
	private TaskMapper taskMapper;
	private EmployeeMapper employeeMapper;

	public ProjectController(IProjectService projectService, ITaskService taskService,
			UserDetailsService userDetailsService, IEmployeeService employeeService, ProjectMapper projectMapper,
			TaskMapper taskMapper, EmployeeMapper employeeMapper) {
		this.projectService = projectService;
		this.taskService = taskService;
		this.userDetailsService = userDetailsService;
		this.employeeService = employeeService;
		this.projectMapper = projectMapper;
		this.taskMapper = taskMapper;
		this.employeeMapper = employeeMapper;
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
		projects.forEach(p -> projectDtos.add(projectMapper.toDto(p)));
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
		model.addAttribute("project", projectMapper.toDto(project));
		return "edit-project";
	}

	@GetMapping("/{id}")
	public String getProject(@PathVariable Long id, Model model) {
		Project project = projectService.findById(id).orElseThrow(NotFoundExcetion::new);
		model.addAttribute("project", projectMapper.toDto(project));
		return "project";
	}

	@PostMapping
	public String createProject(@Valid @ModelAttribute("project") ProjectDto projectDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "new-project";
		}

		projectService.create(projectMapper.toEntity(projectDto));
		return "redirect:/projects";
	}

	@PostMapping("/{id}")
	public String updateProject(@PathVariable Long id, @Valid @ModelAttribute("project") ProjectDto projectDto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "project";
		}

		projectService.update(id, projectMapper.toEntity(projectDto));
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

		model.addAttribute("task", taskMapper.toDto(task));
		model.addAttribute("project", projectMapper.toDto(project));
		return "task-detail";
	}

	@GetMapping("/{id}/tasks/new")
	public String newTask(Model model, @PathVariable(name = "id") Long projectId) {
		Project project = projectService.findById(projectId).orElseThrow(NotFoundExcetion::new);

		model.addAttribute("task", new TaskDto());
		model.addAttribute("project", projectMapper.toDto(project));
		return "new-task";
	}

	@GetMapping("{id}/tasks/{taskId}/edit")
	public String editTask(Model model, @PathVariable Long taskId, @PathVariable("id") Long projectId) {
		Project project = projectService.findById(projectId).orElseThrow(NotFoundExcetion::new);
		Task task = taskService.findById(taskId).orElseThrow(NotFoundExcetion::new);

		Set<EmployeeDto> employeeDtos = employeeMapper
				.toDto(employeeService.findAll().stream().collect(Collectors.toSet()));
		model.addAttribute("task", taskMapper.toDto(task));
		model.addAttribute("project", projectMapper.toDto(project));
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
		Task task = taskMapper.toEntity(taskDto);
		task.setProject(project);
		task.setEmployee(null);
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
		Task task = taskMapper.toEntity(taskDto);
		task.setProject(project);
		if (taskDto.getAssignedId() == null) {
			task.setEmployee(null);
		} else {
			Employee assignedEmployee = employeeService.findById(taskDto.getAssignedId())
					.orElseThrow(NotFoundExcetion::new);
			task.setEmployee(assignedEmployee);
		}
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
		// Task task = taskService.findById(taskId).orElse(null);
		// if (task == null) {
		// return "not-found";
		// }
		//
		// TaskDto taskDto = convertToDto(task);
		// if (taskDto.isCompleted()) {
		// throw new
		// }
		taskService.delete(taskId);
		return "redirect:/projects/" + id;
	}
}
