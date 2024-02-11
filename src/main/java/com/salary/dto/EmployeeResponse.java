package com.salary.dto;

import com.salary.domain.Employee;
import lombok.Getter;

@Getter
public class EmployeeResponse {
    private Long id;
    private String name;
    private String dob;
    private String phone;

    private String imageUrl;

    public EmployeeResponse(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.dob = employee.getDob();
        this.phone = employee.getPhone();
        this.imageUrl = employee.getFileEntity().getSavedNm();
    }
}
