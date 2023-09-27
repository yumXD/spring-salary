package com.salary.service;

import com.salary.domain.Employee;
import com.salary.dto.EmployeeRequest;
import com.salary.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Employee update(Long id, EmployeeRequest request) {
        Employee employee = findById(id);
        employee.update(request);
        return employee;
    }

    public void deleteById(Long id) {
        // findById 메소드를 사용하여 해당 ID의 엔터티가 존재하는지 확인
        Employee employee = findById(id);

        // 엔터티가 존재하면 삭제
        employeeRepository.delete(employee);
    }

    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Page<Employee> findByName(String name, Pageable pageable) {
        return employeeRepository.findByNameContaining(name, pageable);
    }
}
