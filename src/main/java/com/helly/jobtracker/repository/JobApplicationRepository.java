package com.helly.jobtracker.repository;

import com.helly.jobtracker.model.ApplicationStatus;
import com.helly.jobtracker.model.JobApplication;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    Page<JobApplication> findByStatus(ApplicationStatus status, Pageable pageable);

    Page<JobApplication> findByCompanyNameContainingIgnoreCase(String company, Pageable pageable);

    Page<JobApplication> findByStatusAndCompanyNameContainingIgnoreCase(
            ApplicationStatus status, String company, Pageable pageable);

    @Query("select j.status, count(j) from JobApplication j group by j.status")
    List<Object[]> countByStatus();
}