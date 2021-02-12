package com.example.projectmanagementsystem.mapper;

import com.example.projectmanagementsystem.dto.EmployeeDto;
import com.example.projectmanagementsystem.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface EmployeeMapper extends EntityMapper<EmployeeDto, Employee> {

    @Mappings({@Mapping(target = "tasks", ignore = true), @Mapping(target = "employeeProjects", ignore = true)})
    Employee toEntity(EmployeeDto employeeDto);

    default Employee fromId(Long id) {
        if (id == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
