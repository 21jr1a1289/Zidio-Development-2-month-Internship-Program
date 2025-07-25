package com.jobportal.jobportal.repository;



import com.jobportal.jobportal.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
    long countByApprovedFalse();
  
   Optional<Users> findByEmail(String email);
}
