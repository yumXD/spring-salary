package com.salary.controller;

import com.salary.domain.WorkDetail;
import com.salary.dto.WorkDetailRequest;
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
}
