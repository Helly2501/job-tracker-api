package com.helly.jobtracker.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.helly.jobtracker.dto.JobApplicationRequest;
import com.helly.jobtracker.dto.JobApplicationResponse;
import com.helly.jobtracker.exception.ResourceNotFoundException;
import com.helly.jobtracker.model.ApplicationStatus;
import com.helly.jobtracker.model.JobApplication;
import com.helly.jobtracker.repository.JobApplicationRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class JobApplicationServiceTest {

    @Mock
    JobApplicationRepository repository;

    @InjectMocks
    JobApplicationService service;

    @Test
    void createDefaultsStatusAndDate() {
        when(repository.save(any(JobApplication.class))).thenAnswer(i -> i.getArgument(0));
        JobApplicationRequest req =
                new JobApplicationRequest("Acme", "Java Developer", "Toronto", null, null, null, null);

        JobApplicationResponse res = service.create(req);

        assertEquals("Acme", res.companyName());
        assertEquals(ApplicationStatus.APPLIED, res.status());
        assertNotNull(res.appliedDate());
    }

    @Test
    void getByIdThrowsWhenMissing() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getById(99L));
    }

    @Test
    void updateStatusChangesStatus() {
        JobApplication existing = new JobApplication();
        existing.setCompanyName("Globex");
        existing.setJobTitle("Backend Developer");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(JobApplication.class))).thenAnswer(i -> i.getArgument(0));

        JobApplicationResponse res = service.updateStatus(1L, ApplicationStatus.OFFER);

        assertEquals(ApplicationStatus.OFFER, res.status());
    }

    @Test
    void deleteThrowsWhenMissingAndDeletesNothing() {
        when(repository.findById(42L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.delete(42L));
        verify(repository, never()).delete(any(JobApplication.class));
    }
}