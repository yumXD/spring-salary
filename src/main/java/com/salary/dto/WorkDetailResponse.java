package com.salary.dto;

import com.salary.domain.WorkDetail;
import lombok.Getter;

@Getter
public class WorkDetailResponse {
    private Long id;
    private Long hourlyRate;

    public WorkDetailResponse(WorkDetail workDetail) {
        this.id = workDetail.getId();
        this.hourlyRate = workDetail.getHourlyRate();
    }
}
