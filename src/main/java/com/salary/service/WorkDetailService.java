package com.salary.service;

import com.salary.domain.Employee;
import com.salary.domain.WorkDetail;
import com.salary.dto.WorkDetailRequest;
import com.salary.repository.WorkDetailRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class WorkDetailService {

    private final EmployeeService employeeService;
    private final WorkDetailRepository workDetailRepository;

    public WorkDetail findByEmployeeId(Long employeeId) {
        return workDetailRepository.findByEmployeeId(employeeId);
    }


    public WorkDetail createWorkDetail(Long employeeId, Long hourlyWage) {
        Employee employee = employeeService.findById(employeeId);

        WorkDetail workDetail = new WorkDetail();
        workDetail.create(hourlyWage, employee);

        return workDetailRepository.save(workDetail);
    }

    public WorkDetail findWorkDetailByEmployeeId(Long employeeId) {
        employeeService.findById(employeeId);

        WorkDetail workDetail = findByEmployeeId(employeeId);
        if (workDetail == null) {
            throw new EntityNotFoundException("근무표가 존재하지 않습니다.");
        }
        return workDetail;
    }

    public WorkDetail update(Long employeeId, WorkDetailRequest workDetailRequest) {
        employeeService.findById(employeeId);

        WorkDetail workDetail = findByEmployeeId(employeeId);
        if (workDetail == null) {
            throw new EntityNotFoundException("근무표가 존재하지 않습니다.");
        }
        workDetail.update(workDetailRequest);
        return workDetail;
    }

    public void deleteByEmployeeId(Long employeeId) {
        Employee employee = employeeService.findById(employeeId);

        WorkDetail workDetail = findByEmployeeId(employeeId);
        if (workDetail == null) {
            throw new EntityNotFoundException("근무표가 존재하지 않습니다.");
        }

        // 관계 끊기
        employee.setWorkDetail(null);

        workDetailRepository.delete(workDetail);
    }
}
