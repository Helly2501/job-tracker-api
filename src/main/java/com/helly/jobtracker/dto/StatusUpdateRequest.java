package com.helly.jobtracker.dto;

import com.helly.jobtracker.model.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

public record StatusUpdateRequest(@NotNull(message = "Status is required") ApplicationStatus status) {}