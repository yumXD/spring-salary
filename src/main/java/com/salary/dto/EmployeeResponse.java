package com.salary.dto;

import com.salary.domain.Employee;
import lombok.Getter;

@Getter
public class EmployeeResponse {
    private Long id;
    private String name;

    private String position;

    private String department;

    public EmployeeResponse(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.position = employee.getPosition();
        this.department = employee.getDepartment();
    }
}
