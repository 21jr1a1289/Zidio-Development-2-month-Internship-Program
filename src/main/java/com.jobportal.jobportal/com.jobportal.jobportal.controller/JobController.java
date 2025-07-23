package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.repository.JobRepository;
import com.jobportal.jobportal.service.JobService;
import com.jobportal.jobportal.service.UserService;
import com.jobportal.jobportal.model.Job;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDate;

@RestController
@RequestMapping("/jobs")

public class JobController {

    private final JobRepository jobRepository;
    private final JobService jobService;
    private final UserService userService;

    public JobController(JobRepository jobRepository, JobService jobService, UserService userService) {
        this.jobRepository = jobRepository;
        this.jobService = jobService;
        this.userService = userService;
    }

    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    
    @GetMapping("/{id}")
    public Job getJobById(@PathVariable Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));
    }

    @PostMapping
    public ResponseEntity<?> createJob(@RequestBody Job job,
                                       @RequestParam(required = false, defaultValue = "ADMIN") String role) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(403).body("Access denied. Only admins can create jobs.");
        }
        job.setDatePosted(LocalDate.now());
        return ResponseEntity.ok(jobRepository.save(job));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJob(@PathVariable Long id,
                                       @RequestBody Job jobDetails,
                                       @RequestParam(required = false, defaultValue = "ADMIN") String role) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(403).body("Access denied. Only admins can update jobs.");
        }

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));

        job.setTitle(jobDetails.getTitle());
        job.setDescription(jobDetails.getDescription());
        job.setLocation(jobDetails.getLocation());
        job.setType(jobDetails.getType());
        job.setCompany(jobDetails.getCompany());
        job.setSalary(jobDetails.getSalary());

        return ResponseEntity.ok(jobRepository.save(job));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id,
                                       @RequestParam(required = false, defaultValue = "ADMIN") String role) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            return ResponseEntity.status(403).body("Access denied. Only admins can delete jobs.");
        }

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));

        jobRepository.delete(job);
        return ResponseEntity.ok("Job deleted successfully!");
    }

    @GetMapping("/search")
    public List<Job> searchJobs(@RequestParam String keyword, @RequestParam String location) {
        return jobRepository.searchJobs(keyword, location);
    }

    @GetMapping("/admin/stats")
    public ResponseEntity<?> getDashboardStats() {
        return ResponseEntity.ok(new DashboardStats(
                jobService.countJobs(),
                userService.countAll(),
                userService.countPending()
        ));
    }   
    
 
    static class DashboardStats {
        public long totalJobs;
        public long totalUsers;
        public long pendingApprovals;

        public DashboardStats(long totalJobs, long totalUsers, long pendingApprovals) {
            this.totalJobs = totalJobs;
            this.totalUsers = totalUsers;
            this.pendingApprovals = pendingApprovals;
        }
    }
}