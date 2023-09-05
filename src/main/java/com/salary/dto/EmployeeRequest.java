package com.salary.dto;

import com.salary.domain.Employee;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmployeeRequest {

    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    @NotBlank(message = "직책은 필수 항목입니다.")
    private String position;

    @NotBlank(message = "부서는 필수 항목입니다.")
    private String department;

    public Employee toEntity() {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setPosition(position);
        employee.setDepartment(department);
        return employee;
    }
}
