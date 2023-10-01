package com.salary.controller;

import com.salary.domain.WorkDetail;
import com.salary.dto.WorkDetailRequest;
import com.salary.dto.WorkDetailResponse;
import com.salary.service.WorkDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employees/{employeeId}/work-detail")
public class WorkDetailApiController {

    private final WorkDetailService workDetailService;

    // 근무표 추가
    @PostMapping
    public ResponseEntity<WorkDetail> createWorkDetail(@PathVariable Long employeeId, @Valid @RequestBody WorkDetailRequest workDetailRequest) {
        WorkDetail savedWorkDetail = workDetailService.createWorkDetail(employeeId, workDetailRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedWorkDetail.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedWorkDetail);
    }

    //근무표 조회
    @GetMapping
    public ResponseEntity<WorkDetailResponse> findWorkDetail(@PathVariable Long employeeId) {
        WorkDetail workDetail = workDetailService.findWorkDetailByEmployeeId(employeeId);
        return ResponseEntity.ok().body(new WorkDetailResponse(workDetail));
    }

    //근무표 수정 (시급 만)
    @PutMapping
    public ResponseEntity<WorkDetail> updateWorkDetail(@PathVariable Long employeeId, @Valid @RequestBody WorkDetailRequest workDetailRequest) {
        WorkDetail workDetail = workDetailService.update(employeeId, workDetailRequest);
        return ResponseEntity.ok().body(workDetail);
    }

    //근무표 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteWorkDetail(@PathVariable Long employeeId) {
        workDetailService.deleteByEmployeeId(employeeId);
        return ResponseEntity.ok()
                .build();
    }
}
