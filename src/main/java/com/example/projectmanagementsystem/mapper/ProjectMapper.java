package com.example.projectmanagementsystem.mapper;

import com.example.projectmanagementsystem.dto.ProjectDto;
import com.example.projectmanagementsystem.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProjectMapper extends EntityMapper<ProjectDto, Project> {

    @Mapping(target = "tasks", ignore = true)
    Project toEntity(ProjectDto projectDto);

    default Project fromId(Long id) {
        if (id == null) {
            return null;
        }

        Project project = new Project();
        project.setId(id);
        return project;
    }
}
