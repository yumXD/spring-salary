package com.salary.dto;

import com.salary.domain.Wage;
import lombok.Getter;

@Getter
public class WageResponse {
    private Long id;
    private Long hourlyRate;
    private Long totalWorkCount; // 근무 총 횟수
    private Long totalSalary;       // 근무 총 급여


    public WageResponse(Wage wage) {
        this.id = wage.getId();
        this.hourlyRate = wage.getHourlyRate();
        this.totalWorkCount = (long) wage.getTotalWorkCount();
        this.totalSalary = wage.getTotalSalary();
    }
}
