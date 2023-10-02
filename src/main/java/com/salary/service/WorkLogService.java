package com.salary.service;

import com.salary.domain.WorkDetail;
import com.salary.domain.WorkLog;
import com.salary.dto.WorkLogRequest;
import com.salary.repository.WorkLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class WorkLogService {
    private final WorkDetailService workDetailService;
    private final WorkLogRepository workLogRepository;

    public WorkLog createWorkLog(Long employeeId, WorkLogRequest workLogRequest) {
        WorkDetail workDetail = workDetailService.findWorkDetailByEmployeeId(employeeId);
        WorkLog workLog = workLogRequest.toEntity();
        workLog.setWorkDetail(workDetail);
        workDetail.getWorkLogs().add(workLog);
        return workLogRepository.save(workLog);
    }
}
