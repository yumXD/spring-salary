package com.salary.domain;

import com.salary.dto.WorkRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "work")
@Getter
@Setter
public class Work {
    @Id
    @Column(name = "work_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate workDate; // 일하는 날짜

    @Column(nullable = false)
    private LocalTime startTime; // 시작 시간

    @Column(nullable = false)
    private LocalTime endTime; // 종료 시간

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wage_id")
    private Wage wage;

    public Duration getTotalTime() {
        if (endTime.isBefore(startTime)) {
            int hoursUntilMidnight = 24 - startTime.getHour() - 1; // -1 to account for the 0 hour
            int totalHours = hoursUntilMidnight + endTime.getHour();

            int minutesUntilMidnight = 60 - startTime.getMinute();
            int totalMinutes = minutesUntilMidnight + endTime.getMinute();

            if (totalMinutes >= 60) {
                totalHours += 1;
                totalMinutes -= 60;
            }

            return Duration.ofHours(totalHours).plusMinutes(totalMinutes);
        }
        return Duration.between(startTime, endTime);
    }

    public Long getDailyWage() {
        Double hoursWorked = this.getTotalTime().toMinutes() / 60.0;
        return Math.round(wage.getHourlyRate() * hoursWorked); // 반올림
    }

    public void update(WorkRequest workRequest) {
        this.workDate = workRequest.getWorkDate();
        this.startTime = workRequest.getStartTime();
        this.endTime = workRequest.getEndTime();
    }
}
