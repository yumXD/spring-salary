package com.salary.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salary.dto.WageRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wage")
@Getter
@Setter
@ToString
public class Wage {
    @Id
    @Column(name = "wage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long hourlyRate; // 시급

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @JsonIgnore
    @OneToMany(mappedBy = "wage", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<WorkLog> workLogs = new ArrayList<>();

    public Wage create(Long hourlyWage, Employee employee) {
        this.hourlyRate = hourlyWage;
        this.setEmployee(employee);
        employee.setWage(this);
        return this;
    }

    public void update(WageRequest wageRequest) {
        this.hourlyRate = wageRequest.getHourlyRate();
    }

    public int getTotalWorkCount() {
        return workLogs.size();
    }

    public Long getTotalSalary() {
        return workLogs.stream().mapToLong(WorkLog::getDailyWage).sum();
    }
}
