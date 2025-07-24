package com.jobportal.jobportal.repository;

import com.jobportal.jobportal.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("SELECT j FROM Job j WHERE " +
           "LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "AND (:location = 'All Regions' OR LOWER(j.location) = LOWER(:location))")
    List<Job> searchJobs(@Param("keyword") String keyword, @Param("location") String location);
}
