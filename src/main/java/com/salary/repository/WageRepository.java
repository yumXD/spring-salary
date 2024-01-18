package com.salary.repository;

import com.salary.domain.Wage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WageRepository extends JpaRepository<Wage, Long> {
    Wage findByEmployeeId(Long employeeId);
}
