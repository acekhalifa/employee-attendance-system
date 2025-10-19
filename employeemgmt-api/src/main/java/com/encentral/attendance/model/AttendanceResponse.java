package com.encentral.attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class AttendanceResponse {
    private Long id;
    private String userEmail;
    private String userFullName;
    private LocalDate attendanceDate;
    private LocalDateTime markedAt;

    public AttendanceResponse() {
    }

    public AttendanceResponse(Long id, String userEmail, String userFullName, LocalDate attendanceDate, LocalDateTime markedAt) {
        this.id = id;
        this.userEmail = userEmail;
        this.userFullName = userFullName;
        this.attendanceDate = attendanceDate;
        this.markedAt = markedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserFullName() { return userFullName; }
    public void setUserFullName(String userFullName) { this.userFullName = userFullName; }

    public LocalDate getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(LocalDate attendanceDate) { this.attendanceDate = attendanceDate; }

    public LocalDateTime getMarkedAt() { return markedAt; }
    public void setMarkedAt(LocalDateTime markedAt) { this.markedAt = markedAt; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AttendanceResponse that = (AttendanceResponse) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUserEmail(), that.getUserEmail()) && Objects.equals(getUserFullName(), that.getUserFullName()) && Objects.equals(getAttendanceDate(), that.getAttendanceDate()) && Objects.equals(getMarkedAt(), that.getMarkedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserEmail(), getUserFullName(), getAttendanceDate(), getMarkedAt());
    }

    @Override
    public String toString() {
        return "AttendanceResponse{" +
                "id=" + id +
                ", userEmail='" + userEmail + '\'' +
                ", userFullName='" + userFullName + '\'' +
                ", attendanceDate=" + attendanceDate +
                ", markedAt=" + markedAt +
                '}';
    }
}
