package com.jobportal.jobportal.dto;

public class SettingsDTO {
    private String fullName;
    private String email;
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
    private boolean emailNotifications;
    private boolean smsAlerts;
    private boolean weeklyReports;
    private String theme;


    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCurrentPassword() { return currentPassword; }
    public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }

    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }

    public String getConfirmNewPassword() { return confirmNewPassword; }
    public void setConfirmNewPassword(String confirmNewPassword) { this.confirmNewPassword = confirmNewPassword; }

    public boolean isEmailNotifications() { return emailNotifications; }
    public void setEmailNotifications(boolean emailNotifications) { this.emailNotifications = emailNotifications; }

    public boolean isSmsAlerts() { return smsAlerts; }
    public void setSmsAlerts(boolean smsAlerts) { this.smsAlerts = smsAlerts; }

    public boolean isWeeklyReports() { return weeklyReports; }
    public void setWeeklyReports(boolean weeklyReports) { this.weeklyReports = weeklyReports; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    @Override
    public String toString() {
        return "SettingsDTO{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", currentPassword='" + currentPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", confirmNewPassword='" + confirmNewPassword + '\'' +
                ", emailNotifications=" + emailNotifications +
                ", smsAlerts=" + smsAlerts +
                ", weeklyReports=" + weeklyReports +
                ", theme='" + theme + '\'' +
                '}';
    }
}

