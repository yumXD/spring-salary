package com.salary.service;

import com.salary.domain.Wage;
import com.salary.domain.WorkLog;
import com.salary.dto.WorkLogRequest;
import com.salary.repository.WorkLogRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class WorkLogService {
    private final WageService wageService;
    private final WorkLogRepository workLogRepository;

    public WorkLog createWorkLog(Long employeeId, WorkLogRequest workLogRequest) {
        Wage wage = wageService.findWorkDetailByEmployeeId(employeeId);
        WorkLog workLog = workLogRequest.toEntity();
        workLog.setWage(wage);
        wage.getWorkLogs().add(workLog);
        return workLogRepository.save(workLog);
    }

    public Page<WorkLog> findAll(Long employeeId, Pageable pageable) {
        Wage wage = wageService.findWorkDetailByEmployeeId(employeeId);
        return workLogRepository.findAllByWageId(wage.getId(), pageable);
    }


    public WorkLog findById(Long employeeId, Long workLogId) {
        wageService.findWorkDetailByEmployeeId(employeeId);
        return workLogRepository.findById(workLogId).orElseThrow(() -> new EntityNotFoundException("근무 기록이 존재하지 않습니다."));
    }

    public WorkLog update(Long employeeId, Long workLogId, WorkLogRequest workLogRequest) {
        wageService.findWorkDetailByEmployeeId(employeeId);
        WorkLog workLog = workLogRepository.findById(workLogId).orElseThrow(() -> new EntityNotFoundException("근무 기록이 존재하지 않습니다."));
        workLog.update(workLogRequest);
        return workLog;
    }

    public void delete(Long employeeId, Long workLogId) {
        Wage wage = wageService.findWorkDetailByEmployeeId(employeeId);
        WorkLog workLog = workLogRepository.findById(workLogId).orElseThrow(() -> new EntityNotFoundException("근무 기록이 존재하지 않습니다."));

        //workDetail.getWorkLogs().remove(workLog);

        workLogRepository.delete(workLog);
    }
}
