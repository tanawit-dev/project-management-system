package com.example.projectmanagementsystem.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class ProjectDto {

    private Long id;

    @NotBlank
    private String name;

    private Set<TaskDto> tasks;

    @Getter(AccessLevel.NONE)
    private long progress;

    public long getProgress() {
        long count = tasks.stream().filter(TaskDto::isCompleted).count();
        if (count == 0) {
            return 0;
        }
        return (count * 100) / tasks.size();
    }

}
