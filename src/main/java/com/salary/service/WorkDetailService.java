package com.salary.service;

import com.salary.domain.Employee;
import com.salary.domain.WorkDetail;
import com.salary.dto.WorkDetailRequest;
import com.salary.repository.WorkDetailRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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

    public WorkDetail createWorkDetail(Long employeeId, WorkDetailRequest workDetailRequest) {
        Employee employee = employeeService.findById(employeeId);

        if (findByEmployeeId(employeeId) != null) {
            throw new DataIntegrityViolationException("이미 근무표가 생성된 상태입니다.[무결성 위반]");
        }

        WorkDetail workDetail = new WorkDetail();
        workDetail.create(workDetailRequest, employee);

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
        workDetail.update(workDetailRequest);
        return workDetail;
    }
}
