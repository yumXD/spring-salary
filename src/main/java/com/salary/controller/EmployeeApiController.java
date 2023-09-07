package com.salary.controller;

import com.salary.domain.Employee;
import com.salary.dto.EmployeeRequest;
import com.salary.dto.EmployeeResponse;
import com.salary.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

    // 모든 직원 조회
    @GetMapping("/api/employees")
    public ResponseEntity<List<EmployeeResponse>> findAllEmployees() {
        List<EmployeeResponse> employees = employeeService.findAll()
                .stream()
                .map(EmployeeResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(employees);
    }

    // 특정 직원 조회
    @GetMapping("/api/employees/{id}")
    public ResponseEntity<EmployeeResponse> findEmployee(@PathVariable("id") Long id) {
        Employee employee = employeeService.findById(id);
        return ResponseEntity.ok().body(new EmployeeResponse(employee));
    }
}
