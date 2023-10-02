package com.salary.controller;

import com.salary.domain.WorkLog;
import com.salary.dto.WorkLogRequest;
import com.salary.dto.WorkLogResponse;
import com.salary.service.WorkLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employees/{employeeId}/work-detail")
public class WorkLogApiController {
    private final WorkLogService workLogService;

    // 근무 기록 추가
    @PostMapping("/work-log")
    public ResponseEntity<WorkLog> createWorkLog(@PathVariable Long employeeId, @Valid @RequestBody WorkLogRequest workLogRequest) {
        WorkLog savedWorkLog = workLogService.createWorkLog(employeeId, workLogRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedWorkLog.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedWorkLog);
    }

    // 모든 근무 기록 조회
    @GetMapping("/work-log")
    public ResponseEntity<Page<WorkLogResponse>> findAllWorkLogs(@PathVariable Long employeeId,
                                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                                 @RequestParam(value = "size", defaultValue = "3") int size) {
        Page<WorkLog> workLogPage = workLogService.findAll(employeeId, PageRequest.of(page, size));

        Page<WorkLogResponse> workLogs = workLogPage.map(WorkLogResponse::new);
        return ResponseEntity.ok().body(workLogs);
    }

    // 특정 근무 기록 조회
    @GetMapping("/work-log/{workLogId}")
    public ResponseEntity<WorkLogResponse> findWorkLog(@PathVariable Long employeeId, @PathVariable Long workLogId) {
        WorkLog workLog = workLogService.findById(employeeId, workLogId);
        return ResponseEntity.ok().body(new WorkLogResponse(workLog));
    }

    //특정 근무 기록 수정
    @PutMapping("/work-log/{workLogId}")
    public ResponseEntity<WorkLog> updateWorkLog(@PathVariable Long employeeId, @PathVariable Long workLogId, @Valid @RequestBody WorkLogRequest workLogRequest) {
        WorkLog workLog = workLogService.update(employeeId, workLogId, workLogRequest);
        return ResponseEntity.ok().body(workLog);
    }

    //특정 근무 기록 삭제
    @DeleteMapping("/work-log/{workLogId}")
    public ResponseEntity<Void> deleteWorkLog(@PathVariable Long employeeId, @PathVariable Long workLogId) {
        workLogService.delete(employeeId, workLogId);
        return ResponseEntity.ok()
                .build();
    }
}
