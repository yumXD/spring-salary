package com.salary.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WorkDetailRequest {
    @NotNull(message = "시급을 입력해주세요.")
    @Min(value = 0, message = "시급은 0 이상의 값이어야 합니다.")
    @Max(value = 999999, message = "시급은 999999 이하의 값이어야 합니다.")
    private Long hourlyRate;
}
