package com.jobportal.jobportal.service;

import com.jobportal.jobportal.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.jobportal.jobportal.model.Users; 
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public long countAll() {
        return userRepository.count(); 
    }

    @Override
    public long countPending() {
        return userRepository.countByApprovedFalse(); 
    }
    
    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
    

    
}
