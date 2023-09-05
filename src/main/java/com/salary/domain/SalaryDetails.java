package com.salary.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "salary_details")
@Getter
@Setter
@ToString
public class SalaryDetails {
    @Id
    @Column(name = "salary_details_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double basicSalary;
    private Double total;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @JsonIgnore
    @OneToMany(mappedBy = "salaryDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bonus> bonuses;

    @JsonIgnore
    @OneToMany(mappedBy = "salaryDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Deduction> deductions;
}
