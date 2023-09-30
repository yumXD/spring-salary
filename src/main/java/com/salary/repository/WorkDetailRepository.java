package com.salary.repository;

import com.salary.domain.WorkDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkDetailRepository extends JpaRepository<WorkDetail, Long> {
    WorkDetail findByEmployeeId(Long employeeId);
}
