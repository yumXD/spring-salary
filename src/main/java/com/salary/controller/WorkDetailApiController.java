package com.salary.controller;

import com.salary.domain.WorkDetail;
import com.salary.dto.WorkDetailRequest;
import com.salary.dto.WorkDetailResponse;
import com.salary.service.WorkDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employees/{employeeId}/work-detail")
@Slf4j
public class WorkDetailApiController {

    private final WorkDetailService workDetailService;

    @GetMapping
    public ResponseEntity<WorkDetailResponse> findWorkDetail(@PathVariable Long employeeId) {
        WorkDetail workDetail = workDetailService.findWorkDetailByEmployeeId(employeeId);
        log.info("{} 님 근무표 조회 완료", workDetail.getEmployee().getName());
        return ResponseEntity.ok().body(new WorkDetailResponse(workDetail));
    }

    @PutMapping
    public ResponseEntity<WorkDetail> updateWorkDetail(@PathVariable Long employeeId, @Valid @RequestBody WorkDetailRequest workDetailRequest) {
        WorkDetail workDetail = workDetailService.update(employeeId, workDetailRequest);
        log.info("{} 님 근무표 수정 (시급) 완료", workDetail.getEmployee().getName());
        return ResponseEntity.ok().body(workDetail);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteWorkDetail(@PathVariable Long employeeId) {
        workDetailService.deleteByEmployeeId(employeeId);
        log.info("{} 의 근무표 삭제 완료", employeeId);
        return ResponseEntity.ok()
                .build();
    }
}
