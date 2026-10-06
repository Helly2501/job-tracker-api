package com.helly.jobtracker.service;

import com.helly.jobtracker.dto.JobApplicationRequest;
import com.helly.jobtracker.dto.JobApplicationResponse;
import com.helly.jobtracker.exception.ResourceNotFoundException;
import com.helly.jobtracker.model.ApplicationStatus;
import com.helly.jobtracker.model.JobApplication;
import com.helly.jobtracker.repository.JobApplicationRepository;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JobApplicationService {

    private final JobApplicationRepository repository;

    public JobApplicationService(JobApplicationRepository repository) {
        this.repository = repository;
    }

    public JobApplicationResponse create(JobApplicationRequest req) {
        JobApplication job = new JobApplication();
        apply(job, req);
        if (job.getAppliedDate() == null) job.setAppliedDate(LocalDate.now());
        return JobApplicationResponse.from(repository.save(job));
    }

    @Transactional(readOnly = true)
    public Page<JobApplicationResponse> search(ApplicationStatus status, String company, Pageable pageable) {
        boolean hasCompany = company != null && !company.isBlank();
        Page<JobApplication> page;
        if (status != null && hasCompany) {
            page = repository.findByStatusAndCompanyNameContainingIgnoreCase(status, company, pageable);
        } else if (status != null) {
            page = repository.findByStatus(status, pageable);
        } else if (hasCompany) {
            page = repository.findByCompanyNameContainingIgnoreCase(company, pageable);
        } else {
            page = repository.findAll(pageable);
        }
        return page.map(JobApplicationResponse::from);
    }

    @Transactional(readOnly = true)
    public JobApplicationResponse getById(Long id) {
        return JobApplicationResponse.from(find(id));
    }

    public JobApplicationResponse update(Long id, JobApplicationRequest req) {
        JobApplication job = find(id);
        apply(job, req);
        return JobApplicationResponse.from(repository.save(job));
    }

    public JobApplicationResponse updateStatus(Long id, ApplicationStatus status) {
        JobApplication job = find(id);
        job.setStatus(status);
        return JobApplicationResponse.from(repository.save(job));
    }

    public void delete(Long id) {
        repository.delete(find(id));
    }

    @Transactional(readOnly = true)
    public Map<ApplicationStatus, Long> stats() {
        Map<ApplicationStatus, Long> result = new EnumMap<>(ApplicationStatus.class);
        for (ApplicationStatus s : ApplicationStatus.values()) result.put(s, 0L);
        for (Object[] row : repository.countByStatus()) {
            result.put((ApplicationStatus) row[0], (Long) row[1]);
        }
        return result;
    }

    private JobApplication find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found with id " + id));
    }

    private void apply(JobApplication job, JobApplicationRequest req) {
        job.setCompanyName(req.companyName());
        job.setJobTitle(req.jobTitle());
        job.setLocation(req.location());
        job.setJobUrl(req.jobUrl());
        if (req.status() != null) job.setStatus(req.status());
        if (req.appliedDate() != null) job.setAppliedDate(req.appliedDate());
        job.setNotes(req.notes());
    }
}