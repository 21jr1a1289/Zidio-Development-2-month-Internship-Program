package com.jobportal.jobportal.repository;

import com.jobportal.jobportal.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {}
