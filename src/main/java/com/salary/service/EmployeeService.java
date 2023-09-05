package com.salary.service;

import com.salary.domain.Employee;
import com.salary.dto.EmployeeRequest;
import com.salary.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee createEmployee(EmployeeRequest employeeRequest) {
        return employeeRepository.save(employeeRequest.toEntity());
    }
}
