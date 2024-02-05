package com.salary.dto;

import com.salary.domain.Employee;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank(message = "생년월일을 입력하세요")
    @Pattern(regexp = "\\d{8}", message = "생년월일은 8자리 숫자여야 합니다")
    private String dob;

    @NotBlank(message = "전화번호를 입력하세요")
    @Pattern(regexp = "\\d{10,11}", message = "전화번호는 10자리 또는 11자리 숫자여야 합니다")
    private String phone;
    public Employee toEntity() {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setPosition(position);
        employee.setDepartment(department);
        employee.setDob(dob);
        employee.setPhone(phone);
        return employee;
    }
}
