package com.salary.controller;

import com.salary.domain.Wage;
import com.salary.dto.WageRequest;
import com.salary.dto.WageResponse;
import com.salary.service.WageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employees/{employeeId}/work-detail")
@Slf4j
public class WageApiController {

    private final WageService wageService;

    @GetMapping
    public ResponseEntity<WageResponse> findWorkDetail(@PathVariable Long employeeId) {
        Wage wage = wageService.findWorkDetailByEmployeeId(employeeId);
        log.info("{} 님 근무표 조회 완료", wage.getEmployee().getName());
        return ResponseEntity.ok().body(new WageResponse(wage));
    }

    @PutMapping
    public ResponseEntity<Wage> updateWorkDetail(@PathVariable Long employeeId, @Valid @RequestBody WageRequest wageRequest) {
        Wage wage = wageService.update(employeeId, wageRequest);
        log.info("{} 님 근무표 수정 (시급) 완료", wage.getEmployee().getName());
        return ResponseEntity.ok().body(wage);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteWorkDetail(@PathVariable Long employeeId) {
        wageService.deleteByEmployeeId(employeeId);
        log.info("{} 의 근무표 삭제 완료", employeeId);
        return ResponseEntity.ok()
                .build();
    }
}
