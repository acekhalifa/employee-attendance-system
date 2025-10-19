package com.attendancemgmt.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(
        name = "attendance",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "attendance_date"})

)
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @Column(name = "marked_at")
    private LocalDateTime markedAt;

    public Attendance() {
    }

    public Attendance(Long id, User user, LocalDate attendanceDate, LocalDateTime markedAt) {
        this.id = id;
        this.user = user;
        this.attendanceDate = attendanceDate;
        this.markedAt = markedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public LocalDateTime getMarkedAt() {
        return markedAt;
    }

    public void setMarkedAt(LocalDateTime markedAt) {
        this.markedAt = markedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Attendance that = (Attendance) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getUser(), that.getUser()) && Objects.equals(getAttendanceDate(), that.getAttendanceDate()) && Objects.equals(getMarkedAt(), that.getMarkedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getAttendanceDate(), getMarkedAt());
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", user=" + user +
                ", attendanceDate=" + attendanceDate +
                ", markedAt=" + markedAt +
                '}';
    }
}
