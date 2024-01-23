package com.salary.dto;

import com.salary.domain.Work;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WorkResponse {
    private Long id;
    private LocalDate workDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private Long totalHours; // 근무당 총 시간
    private Integer totalMinutes; // 근무당 총 분
    private Long dailyWage; // 근무 당 총 급여

    public WorkResponse(Work work) {
        this.id = work.getId();
        this.workDate = work.getWorkDate();
        this.startTime = work.getStartTime();
        this.endTime = work.getEndTime();
        this.totalHours = work.getTotalTime().toHours();
        this.totalMinutes = work.getTotalTime().toMinutesPart();
        this.dailyWage = work.getDailyWage();
    }
}
