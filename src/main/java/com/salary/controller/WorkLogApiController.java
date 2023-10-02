package com.salary.controller;

import com.salary.domain.WorkLog;
import com.salary.dto.WorkLogRequest;
import com.salary.service.WorkLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}
