package com.helly.jobtracker.dto;

import com.helly.jobtracker.model.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record JobApplicationRequest(
        @NotBlank(message = "Company name is required") String companyName,
        @NotBlank(message = "Job title is required") String jobTitle,
        String location,
        String jobUrl,
        ApplicationStatus status,
        LocalDate appliedDate,
        @Size(max = 2000, message = "Notes must be at most 2000 characters") String notes
) {}