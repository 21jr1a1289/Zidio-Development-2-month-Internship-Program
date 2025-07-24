package com.jobportal.jobportal.service;

import com.jobportal.jobportal.model.Job;
import com.jobportal.jobportal.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public long countJobs() {
        return jobRepository.count();
    }
    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job save(Job job) {
        return jobRepository.save(job);
    }
    
    @Override
    public void delete(Long id) {
        jobRepository.deleteById(id);
    }

}
