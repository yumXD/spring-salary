package com.salary.controller;

import com.salary.domain.Employee;
import com.salary.dto.EmployeeRequest;
import com.salary.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class EmployeeApiController {
    private final EmployeeService employeeService;

    // 직원 추가
    @PostMapping("/api/employees")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        Employee savedEmployee = employeeService.createEmployee(employeeRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEmployee.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedEmployee);
    }
}
