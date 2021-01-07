package com.example.projectmanagementsystem.dto;

import java.util.Set;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

@Data
public class ProjectDto {

	private Long id;

	@NotBlank
	private String name;

	private Set<TaskDto> tasks;

	@Getter(AccessLevel.NONE)
	private long progress;

	public long getProgress() {
		long count = tasks.stream().filter(t -> t.isCompleted()).count();
		if (count == 0) {
			return 0;
		}
		return (count * 100) / tasks.size();
	}

}
