package com.salary.service;

import com.salary.domain.Wage;
import com.salary.domain.Work;
import com.salary.dto.WorkRequest;
import com.salary.repository.WorkRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class WorkService {
    private final WageService wageService;
    private final WorkRepository workRepository;

    public Work createWorkLog(Long employeeId, WorkRequest workRequest) {
        Wage wage = wageService.findWorkDetailByEmployeeId(employeeId);
        Work work = workRequest.toEntity();
        work.setWage(wage);
        wage.getWorks().add(work);
        return workRepository.save(work);
    }

    public Page<Work> findAll(Long employeeId, Pageable pageable) {
        Wage wage = wageService.findWorkDetailByEmployeeId(employeeId);
        return workRepository.findAllByWageId(wage.getId(), pageable);
    }


    public Work findById(Long employeeId, Long workLogId) {
        wageService.findWorkDetailByEmployeeId(employeeId);
        return workRepository.findById(workLogId).orElseThrow(() -> new EntityNotFoundException("근무 기록이 존재하지 않습니다."));
    }

    public Work update(Long employeeId, Long workLogId, WorkRequest workRequest) {
        wageService.findWorkDetailByEmployeeId(employeeId);
        Work work = workRepository.findById(workLogId).orElseThrow(() -> new EntityNotFoundException("근무 기록이 존재하지 않습니다."));
        work.update(workRequest);
        return work;
    }

    public void delete(Long employeeId, Long workLogId) {
        Wage wage = wageService.findWorkDetailByEmployeeId(employeeId);
        Work work = workRepository.findById(workLogId).orElseThrow(() -> new EntityNotFoundException("근무 기록이 존재하지 않습니다."));

        //workDetail.getWorkLogs().remove(workLog);

        workRepository.delete(work);
    }
}
