package com.jobportal.jobportal.service;

import com.jobportal.jobportal.model.Job;
import java.util.List;


public interface JobService {
	  long countJobs();
    List<Job> findAll();
    Job save(Job job);
   
    void delete(Long id);

}

