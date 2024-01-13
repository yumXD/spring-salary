package com.salary.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salary.dto.WorkDetailRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnore
    @OneToMany(mappedBy = "workDetail", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<WorkLog> workLogs = new ArrayList<>();

//    public WorkDetail create(WorkDetailRequest workDetailRequest, Employee employee) {
//        this.hourlyRate = workDetailRequest.getHourlyRate();
//        this.setEmployee(employee);
//        employee.setWorkDetail(this);
//        return this;
//    }

    public WorkDetail create(Long hourlyWage, Employee employee) {
        this.hourlyRate = hourlyWage;
        this.setEmployee(employee);
        employee.setWorkDetail(this);
        return this;
    }

    public void update(WorkDetailRequest workDetailRequest) {
        this.hourlyRate = workDetailRequest.getHourlyRate();
    }

    public int getTotalWorkCount() {
        return workLogs.size();
    }

    public Long getTotalSalary() {
        return workLogs.stream().mapToLong(WorkLog::getDailyWage).sum();
    }
}
