package com.salary.service;

import com.salary.domain.Employee;
import com.salary.domain.Wage;
import com.salary.dto.WageRequest;
import com.salary.repository.WageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class WageService {

    private final EmployeeService employeeService;
    private final WageRepository wageRepository;

    public Wage findByEmployeeId(Long employeeId) {
        return wageRepository.findByEmployeeId(employeeId);
    }


    public Wage createWorkDetail(Long employeeId, Long hourlyWage) {
        Employee employee = employeeService.findById(employeeId);

        Wage wage = new Wage();
        wage.create(hourlyWage, employee);

        return wageRepository.save(wage);
    }

    public Wage findWorkDetailByEmployeeId(Long employeeId) {
        employeeService.findById(employeeId);

        Wage wage = findByEmployeeId(employeeId);
        if (wage == null) {
            throw new EntityNotFoundException("근무표가 존재하지 않습니다.");
        }
        return wage;
    }

    public Wage update(Long employeeId, WageRequest wageRequest) {
        employeeService.findById(employeeId);

        Wage wage = findByEmployeeId(employeeId);
        if (wage == null) {
            throw new EntityNotFoundException("근무표가 존재하지 않습니다.");
        }
        wage.update(wageRequest);
        return wage;
    }

    public void deleteByEmployeeId(Long employeeId) {
        Employee employee = employeeService.findById(employeeId);

        Wage wage = findByEmployeeId(employeeId);
        if (wage == null) {
            throw new EntityNotFoundException("근무표가 존재하지 않습니다.");
        }

        // 관계 끊기
        employee.setWage(null);

        wageRepository.delete(wage);
    }
}
