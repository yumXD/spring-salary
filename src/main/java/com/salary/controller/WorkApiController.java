package com.salary.controller;

import com.salary.domain.Work;
import com.salary.dto.WorkRequest;
import com.salary.dto.WorkResponse;
import com.salary.service.WorkService;
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
public class WorkApiController {
    private final WorkService workService;

    @PostMapping
    public ResponseEntity<Work> createWorkLog(@PathVariable Long employeeId, @Valid @RequestBody WorkRequest workRequest) {
        Work savedWork = workService.createWorkLog(employeeId, workRequest);
        log.info("{} 님 근무 기록 추가 완료", savedWork.getWage().getEmployee().getName());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedWork.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedWork);
    }

    @GetMapping
    public ResponseEntity<Page<WorkResponse>> findAllWorkLogs(@PathVariable Long employeeId,
                                                              @RequestParam(value = "page", defaultValue = "0") int page,
                                                              @RequestParam(value = "size", defaultValue = "3") int size) {
        Page<Work> workLogPage = workService.findAll(employeeId, PageRequest.of(page, size));
        log.info("{}의 모든 근무 기록 조회 완료", employeeId);
        Page<WorkResponse> workLogs = workLogPage.map(WorkResponse::new);
        return ResponseEntity.ok().body(workLogs);
    }

    @GetMapping("/{workLogId}")
    public ResponseEntity<WorkResponse> findWorkLog(@PathVariable Long employeeId, @PathVariable Long workLogId) {
        Work work = workService.findById(employeeId, workLogId);
        log.info("{} 님 근무 기록 상세 조회 완료", work.getWage().getEmployee().getName());
        return ResponseEntity.ok().body(new WorkResponse(work));
    }

    @PutMapping("/{workLogId}")
    public ResponseEntity<Work> updateWorkLog(@PathVariable Long employeeId, @PathVariable Long workLogId, @Valid @RequestBody WorkRequest workRequest) {
        Work work = workService.update(employeeId, workLogId, workRequest);
        log.info("{} 님 근무 기록 수정 완료", work.getWage().getEmployee().getName());
        return ResponseEntity.ok().body(work);
    }

    @DeleteMapping("/{workLogId}")
    public ResponseEntity<Void> deleteWorkLog(@PathVariable Long employeeId, @PathVariable Long workLogId) {
        workService.delete(employeeId, workLogId);
        log.info("근무 기록 삭제 완료");
        return ResponseEntity.ok()
                .build();
    }
}
