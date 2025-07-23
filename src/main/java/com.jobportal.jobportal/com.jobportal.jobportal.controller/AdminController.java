package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.model.Job;
import com.jobportal.jobportal.service.JobService;
import com.jobportal.jobportal.dto.SettingsDTO;
import com.jobportal.jobportal.model.Users;
import com.jobportal.jobportal.model.Admin;
import com.jobportal.jobportal.repository.UserRepository;
import com.jobportal.jobportal.repository.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private JobService jobService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    // === ADMIN ROUTES ===

    @GetMapping
    public String adminRoot() {
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        long totalJobs = jobService.countJobs();
        long totalUsers = userRepository.count();
        long pendingApprovals = 0; // Placeholder if needed later

        model.addAttribute("totalJobs", totalJobs);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("pendingApprovals", pendingApprovals);

        model.addAttribute("jobCategoryLabels", List.of("IT", "Finance", "HR", "Marketing"));
        model.addAttribute("jobCategoryData", List.of(10, 7, 5, 8));

        model.addAttribute("userDemoLabels", List.of("Students", "Professionals", "Recruiters"));
        model.addAttribute("userDemoData", List.of(30, 20, 15));

        return "admin-dashboard";
    }

    @GetMapping("/jobs")
    public String showJobsPage(Model model) {
        model.addAttribute("job", new Job());
        model.addAttribute("jobs", jobService.findAll());
        return "jobs";
    }

    @PostMapping("/jobs")
    public String postJob(@ModelAttribute Job job) {
        job.setDatePosted(LocalDate.now()); 
        jobService.save(job);
        return "redirect:/admin/jobs";
    }

    @GetMapping("/jobs/all")
    @ResponseBody
    public List<Job> getAllJobs() {
        return jobService.findAll();
    }

    @PostMapping("/jobs/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        jobService.delete(id);  
        return ResponseEntity.ok("Deleted");
    }     
    // === SETTINGS PAGE ===
    @GetMapping("/settings")
    public String showSettingsPage(Model model) {
        
       
        model.addAttribute("settings", new SettingsDTO());
        return "admin-settings";
    }
    @PostMapping("/settings/save")
    public String saveSettings(@ModelAttribute("settings") SettingsDTO settings, Model model) {
        System.out.println("Received settings: " + settings);
        
       
        model.addAttribute("successMessage", "Settings saved successfully!");

        
        model.addAttribute("settings", settings);
        return "admin-settings";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }

    @GetMapping("/admin-profile")
    public String adminProfilePage(Model model) {
        Admin admin = adminRepository.findById(1L).orElse(new Admin());
        model.addAttribute("admin", admin);
        return "admin-profile";
    }

    @PostMapping("/admin-profile")
    public String saveAdminProfile(@ModelAttribute Admin admin) {
        if (admin.getId() == null) {
            admin.setId(1L);
        }
        adminRepository.save(admin);
        return "redirect:/admin/admin-profile";
    }

    @GetMapping("/users")
    public String usersPage(Model model) {
        List<Users> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }
}
