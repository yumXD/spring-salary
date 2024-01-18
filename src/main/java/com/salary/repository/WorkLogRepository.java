package com.salary.repository;

import com.salary.domain.WorkLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
    Page<WorkLog> findAllByWageId(Long wageId, Pageable pageable);
}
