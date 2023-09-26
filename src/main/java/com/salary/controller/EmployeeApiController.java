package com.salary.controller;

import com.salary.domain.Employee;
import com.salary.dto.EmployeeRequest;
import com.salary.dto.EmployeeResponse;
import com.salary.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    // 모든 직원 조회
    @GetMapping("/api/employees")
    public ResponseEntity<Page<EmployeeResponse>> findAllEmployees(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "size", defaultValue = "3") int size) {
        Page<Employee> employeePage = employeeService.findAll(PageRequest.of(page, size));
        Page<EmployeeResponse> employees = employeePage.map(EmployeeResponse::new);
        return ResponseEntity.ok().body(employees);
    }

    // 특정 직원 조회
    @GetMapping("/api/employees/{id}")
    public ResponseEntity<EmployeeResponse> findEmployee(@PathVariable("id") Long id) {
        Employee employee = employeeService.findById(id);
        return ResponseEntity.ok().body(new EmployeeResponse(employee));
    }

    // 특정 직원 수정
    @PutMapping("/api/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id, @Valid @RequestBody EmployeeRequest request) {
        Employee updatedEmployee = employeeService.update(id, request);
        return ResponseEntity.ok()
                .body(updatedEmployee);
    }

    // 특정 직원 삭제
    @DeleteMapping("/api/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.ok()
                .build();
    }
}
