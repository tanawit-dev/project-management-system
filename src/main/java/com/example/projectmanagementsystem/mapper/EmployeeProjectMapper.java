package com.example.projectmanagementsystem.mapper;

import com.example.projectmanagementsystem.dto.EmployeeProjectDto;
import com.example.projectmanagementsystem.model.EmployeeProject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = {EmployeeMapper.class, ProjectMapper.class})
public interface EmployeeProjectMapper extends EntityMapper<EmployeeProjectDto, EmployeeProject> {

    @Mappings({@Mapping(source = "employee.id", target = "employeeId"),
            @Mapping(source = "project.id", target = "projectId")})
    EmployeeProjectDto toDto(EmployeeProject employeeProject);

    @Mappings({@Mapping(source = "employeeId", target = "employee"),
            @Mapping(source = "projectId", target = "project")})
    EmployeeProject toEntity(EmployeeProjectDto employeeProjectDto);

    default EmployeeProject fromId(Long id) {
        if (id == null) {
            return null;
        }

        EmployeeProject employeeProject = new EmployeeProject();
        employeeProject.setId(id);
        return employeeProject;
    }
}
