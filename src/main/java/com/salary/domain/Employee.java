package com.salary.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salary.dto.EmployeeRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "employee")
@Getter
@Setter
@ToString
public class Employee {
    @Id
    @Column(name = "employee_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String position;
    private String department;

    @JsonIgnore
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private SalaryDetails salaryDetails;

    public void update(EmployeeRequest request) {
        this.name = request.getName();
        this.position = request.getPosition();
        this.department = request.getDepartment();
    }
}
