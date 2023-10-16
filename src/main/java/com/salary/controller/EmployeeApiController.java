package com.salary.controller;

import com.salary.domain.Employee;
import com.salary.dto.EmployeeRequest;
import com.salary.dto.EmployeeResponse;
import com.salary.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employees")
@Slf4j
public class EmployeeApiController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        Employee savedEmployee = employeeService.createEmployee(employeeRequest);
        log.info("{} 직원 추가 완료", savedEmployee.getName());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedEmployee.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedEmployee);
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> findAllEmployees(@RequestParam(value = "name", defaultValue = "") String name,
                                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "size", defaultValue = "3") int size) {
        Page<Employee> employeePage;
        if (name.isEmpty()) {
            employeePage = employeeService.findAll(PageRequest.of(page, size));
            log.info("{} 명의 직원 검색 완료", size);
        } else {
            employeePage = employeeService.findByName(name, PageRequest.of(page, size));
            log.info("{} 직원 검색 완료", name);
        }
        Page<EmployeeResponse> employees = employeePage.map(EmployeeResponse::new);
        return ResponseEntity.ok().body(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> findEmployee(@PathVariable("id") Long id) {
        Employee employee = employeeService.findById(id);
        log.info("{} 직원 조회 완료", employee.getName());
        return ResponseEntity.ok().body(new EmployeeResponse(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id, @Valid @RequestBody EmployeeRequest request) {
        Employee updatedEmployee = employeeService.update(id, request);
        log.info("{} 직원 수정 완료", updatedEmployee.getName());
        return ResponseEntity.ok()
                .body(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteById(id);
        log.info("{} 직원 삭제 완료", id);
        return ResponseEntity.ok()
                .build();
    }
}
