package com.example.projectmanagementsystem.dto;

import com.example.projectmanagementsystem.enumeration.EmployeeRole;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EmployeeDto {

    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private EmployeeRole role;

    private String username;

    @Getter(AccessLevel.NONE)
    private String fullName;

    public String getFullName() {
        return String.join(" ", firstName, lastName);
    }

}
