
package com.jobportal.jobportal.service;

import com.jobportal.jobportal.model.Users;
import java.util.List;

public interface UserService {
    long countAll();
    long countPending();
    List<Users> getAllUsers(); // Ensure it's 'Users' not 'User'
}
