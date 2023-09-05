package com.salary.repository;

import com.salary.domain.SalaryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryDetailsRepository extends JpaRepository<SalaryDetails, Long> {
}
