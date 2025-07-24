package com.jobportal.jobportal.model;

import jakarta.persistence.*;

@Entity
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;
    private String applicantName;
    private String email;

    public JobApplication() {}

    public JobApplication(Long jobId, String applicantName, String email) {
        this.jobId = jobId;
        this.applicantName = applicantName;
        this.email = email;
    }

    
    public Long getId() { return id; }

    public Long getJobId() { return jobId; }

    public void setJobId(Long jobId) { this.jobId = jobId; }

    public String getApplicantName() { return applicantName; }

    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }
}
