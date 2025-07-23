package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.model.UserProfile;
import com.jobportal.jobportal.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    @Autowired
    private UserProfileRepository profileRepository;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveProfile(
        @RequestParam("userId") Long userId,
        @RequestParam("fullName") String fullName,
        @RequestParam("phoneNumber") String phone,
        @RequestParam("skills") String skills,
        @RequestParam("experience") String experience,
        @RequestParam("education") String education,
        @RequestParam(value = "resume", required = false) MultipartFile resumeFile,
        @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto
    )
                                {

        try {
            Optional<UserProfile> existingProfileOpt = profileRepository.findByUserId(userId);
            UserProfile profile = existingProfileOpt.orElse(new UserProfile());

            profile.setUserId(userId);
            profile.setFullName(fullName);
            profile.setPhone(phone);
            profile.setSkills(skills);
            profile.setExperience(experience);
            profile.setEducation(education);

            if (resumeFile != null && !resumeFile.isEmpty()) {
                String fileName = saveResumeFile(resumeFile);
                profile.setResumeFileName(fileName);
            }
            
            if (profilePhoto != null && !profilePhoto.isEmpty()) {
                String profilePhotoFileName = saveResumeFile(profilePhoto); // reuse method
                profile.setProfilePhoto(profilePhotoFileName);
            }


            profileRepository.save(profile);
            return ResponseEntity.ok("Profile saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving profile: " + e.getMessage());
        }
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getProfile(@PathVariable Long userId) {
        Optional<UserProfile> profileOpt = profileRepository.findByUserId(userId);
        return profileOpt.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profile not found."));
    }

    private String saveResumeFile(MultipartFile file) throws IOException {
        String uploadDir = "uploads/";
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
    
    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<byte[]> getFile(@PathVariable String filename) throws IOException {
        Path path = Paths.get("uploads").resolve(filename);
        byte[] fileBytes = Files.readAllBytes(path);

        String contentType = Files.probeContentType(path);
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .body(fileBytes);
    
    
    }
    


}
