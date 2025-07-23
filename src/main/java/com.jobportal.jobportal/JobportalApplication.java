package com.jobportal.jobportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.Desktop;
import java.net.URI;

@SpringBootApplication
public class JobportalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobportalApplication.class, args);

        // Automatically open the browser to /jobs
        try {
            String url = "http://localhost:8076/admin/dashboard";
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                System.out.println("Desktop is not supported. Please open " + url + " manually.");
            }
        } catch (Exception e) {
            System.out.println("Failed to open browser automatically.");
            e.printStackTrace();
        }
    }
}
