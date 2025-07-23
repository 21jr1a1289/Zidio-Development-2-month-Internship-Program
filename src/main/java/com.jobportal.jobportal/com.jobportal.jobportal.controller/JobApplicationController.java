package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.model.JobApplication;
import com.jobportal.jobportal.repository.JobApplicationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    private final JobApplicationRepository applicationRepository;

    public JobApplicationController(JobApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @PostMapping
    public ResponseEntity<?> applyForJob(@RequestBody JobApplication application) {
        applicationRepository.save(application);
        return ResponseEntity.ok("Application submitted successfully.");
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> applyForJob(
            @RequestParam Long jobId,
            @RequestParam String applicantName,
            @RequestParam String email,
            @RequestParam("resume") MultipartFile resumeFile
    ) {
        JobApplication application = new JobApplication(jobId, applicantName, email);
        applicationRepository.save(application);

       
        System.out.println("Received resume: " + resumeFile.getOriginalFilename());

        return ResponseEntity.ok("Application submitted successfully.");
    }

}
