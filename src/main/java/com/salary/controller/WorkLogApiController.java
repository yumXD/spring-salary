package com.salary.controller;

import com.salary.domain.WorkLog;
import com.salary.dto.WorkLogRequest;
import com.salary.dto.WorkLogResponse;
import com.salary.service.WorkLogService;
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
@RequestMapping("/api/employees/{employeeId}/work-detail/work-log")
@Slf4j
public class WorkLogApiController {
    private final WorkLogService workLogService;

    @PostMapping
    public ResponseEntity<WorkLog> createWorkLog(@PathVariable Long employeeId, @Valid @RequestBody WorkLogRequest workLogRequest) {
        WorkLog savedWorkLog = workLogService.createWorkLog(employeeId, workLogRequest);
        log.info("{} 님 근무 기록 추가 완료", savedWorkLog.getWage().getEmployee().getName());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedWorkLog.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedWorkLog);
    }

    @GetMapping
    public ResponseEntity<Page<WorkLogResponse>> findAllWorkLogs(@PathVariable Long employeeId,
                                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                                 @RequestParam(value = "size", defaultValue = "3") int size) {
        Page<WorkLog> workLogPage = workLogService.findAll(employeeId, PageRequest.of(page, size));
        log.info("{}의 모든 근무 기록 조회 완료", employeeId);
        Page<WorkLogResponse> workLogs = workLogPage.map(WorkLogResponse::new);
        return ResponseEntity.ok().body(workLogs);
    }

    @GetMapping("/{workLogId}")
    public ResponseEntity<WorkLogResponse> findWorkLog(@PathVariable Long employeeId, @PathVariable Long workLogId) {
        WorkLog workLog = workLogService.findById(employeeId, workLogId);
        log.info("{} 님 근무 기록 상세 조회 완료", workLog.getWage().getEmployee().getName());
        return ResponseEntity.ok().body(new WorkLogResponse(workLog));
    }

    @PutMapping("/{workLogId}")
    public ResponseEntity<WorkLog> updateWorkLog(@PathVariable Long employeeId, @PathVariable Long workLogId, @Valid @RequestBody WorkLogRequest workLogRequest) {
        WorkLog workLog = workLogService.update(employeeId, workLogId, workLogRequest);
        log.info("{} 님 근무 기록 수정 완료", workLog.getWage().getEmployee().getName());
        return ResponseEntity.ok().body(workLog);
    }

    @DeleteMapping("/{workLogId}")
    public ResponseEntity<Void> deleteWorkLog(@PathVariable Long employeeId, @PathVariable Long workLogId) {
        workLogService.delete(employeeId, workLogId);
        log.info("근무 기록 삭제 완료");
        return ResponseEntity.ok()
                .build();
    }
}
