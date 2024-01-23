package com.salary.dto;

import com.salary.domain.Work;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WorkRequest {
    @NotNull(message = "근무 날짜는 필수 입력값 입니다.")
    @PastOrPresent(message = "근무 날짜는 오늘 이후일 수 없습니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate workDate;

    @NotNull(message = "시작일은 필수 입력값 입니다.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull(message = "종료일은 필수 입력값 입니다.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @AssertTrue(message = "종료일은 시작일 이후여야 합니다.")
    public boolean isValidTime() {
        if (startTime == null || endTime == null) {
            return true;
        }

        if (endTime.isAfter(startTime)) {
            return true;
        } else {
            Duration duration = Duration.between(startTime, endTime);
            return duration.toHours() < 24;
        }
    }


    public Work toEntity() {
        Work work = new Work();
        work.setWorkDate(workDate);
        work.setStartTime(startTime);
        work.setEndTime(endTime);
        return work;
    }
}
