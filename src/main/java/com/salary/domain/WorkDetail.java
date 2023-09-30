package com.salary.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salary.dto.WorkDetailRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "work_detail")
@Getter
@Setter
@ToString
public class WorkDetail {
    @Id
    @Column(name = "work_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long hourlyRate; // 시급

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public WorkDetail create(WorkDetailRequest workDetailRequest, Employee employee) {
        this.hourlyRate = workDetailRequest.getHourlyRate();
        this.setEmployee(employee);
        employee.setWorkDetail(this);
        return this;
    }

    public void update(WorkDetailRequest workDetailRequest) {
        this.hourlyRate = workDetailRequest.getHourlyRate();
    }
}
