package com.example.projectmanagementsystem.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
public class TaskDto {
	private Long id;
	@NotBlank
	private String name;
	private LocalDateTime beginAt;
	private LocalDateTime finishAt;
	private String detail;
	private Long assignedId;
	@Getter(AccessLevel.NONE)
	private boolean isCompleted;

	public boolean isCompleted() {
		return beginAt != null && finishAt != null && detail != null && !detail.isBlank();
	}

}
