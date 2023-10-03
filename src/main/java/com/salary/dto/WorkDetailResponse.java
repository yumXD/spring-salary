package com.salary.dto;

import com.salary.domain.WorkDetail;
import lombok.Getter;

@Getter
public class WorkDetailResponse {
    private Long id;
    private Long hourlyRate;
    private Long totalWorkCount; // 근무 총 횟수
    private Long totalSalary;       // 근무 총 급여


    public WorkDetailResponse(WorkDetail workDetail) {
        this.id = workDetail.getId();
        this.hourlyRate = workDetail.getHourlyRate();
        this.totalWorkCount = (long) workDetail.getTotalWorkCount();
        this.totalSalary = workDetail.getTotalSalary();
    }
}
