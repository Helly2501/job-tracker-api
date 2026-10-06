package com.helly.jobtracker.dto;

import com.helly.jobtracker.model.ApplicationStatus;
import com.helly.jobtracker.model.JobApplication;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record JobApplicationResponse(
        Long id,
        String companyName,
        String jobTitle,
        String location,
        String jobUrl,
        ApplicationStatus status,
        LocalDate appliedDate,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static JobApplicationResponse from(JobApplication j) {
        return new JobApplicationResponse(j.getId(), j.getCompanyName(), j.getJobTitle(),
                j.getLocation(), j.getJobUrl(), j.getStatus(), j.getAppliedDate(),
                j.getNotes(), j.getCreatedAt(), j.getUpdatedAt());
    }
}