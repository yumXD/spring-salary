package com.salary.service;

import com.salary.domain.Employee;
import com.salary.dto.EmployeeRequest;
import com.salary.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public Employee createEmployee(EmployeeRequest employeeRequest) {
        return employeeRepository.save(employeeRequest.toEntity());
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 직원입니다"));
    }
}
