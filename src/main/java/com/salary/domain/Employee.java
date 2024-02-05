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

    private String dob;
    private String phone;

    @JsonIgnore
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Wage wage;

    public void update(EmployeeRequest request) {
        this.name = request.getName();
        this.position = request.getPosition();
        this.department = request.getDepartment();
        this.dob = request.getDob();
        this.phone = request.getPhone();
    }
}
