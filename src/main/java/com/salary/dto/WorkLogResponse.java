package com.salary.dto;

import com.salary.domain.WorkLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WorkLogResponse {
    private Long id;
    private LocalDate workDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private Long totalHours; // 근무당 총 시간
    private Integer totalMinutes; // 근무당 총 분
    private Long dailyWage; // 근무 당 총 급여

    public WorkLogResponse(WorkLog workLog) {
        this.id = workLog.getId();
        this.workDate = workLog.getWorkDate();
        this.startTime = workLog.getStartTime();
        this.endTime = workLog.getEndTime();
        this.totalHours = workLog.getTotalTime().toHours();
        this.totalMinutes = workLog.getTotalTime().toMinutesPart();
        this.dailyWage = workLog.getDailyWage();
    }
}
