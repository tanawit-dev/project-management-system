package com.example.projectmanagementsystem.mapper;

import com.example.projectmanagementsystem.dto.TaskDto;
import com.example.projectmanagementsystem.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = {ProjectMapper.class, EmployeeMapper.class})
public interface TaskMapper extends EntityMapper<TaskDto, Task> {

    @Mappings({@Mapping(source = "project.id", target = "projectId"),
            @Mapping(source = "employee.id", target = "assignedId")})
    TaskDto toDto(Task task);

    @Mappings({@Mapping(source = "projectId", target = "project"),
            @Mapping(source = "assignedId", target = "employee.id")})
    Task toEntity(TaskDto taskDto);

    default Task fromId(Long id) {
        if (id == null) {
            return null;
        }

        Task task = new Task();
        task.setId(id);
        return task;
    }
}
