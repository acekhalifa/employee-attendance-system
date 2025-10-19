package com.encentral.attendance.api;

import com.attendancemgmt.entities.Attendance;
import com.attendancemgmt.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IAttendance {
    Optional<Attendance> findByUserAndAttendanceDate(User user, LocalDate date);
    List<Attendance> findByAttendanceDate(LocalDate date);
    List<Attendance> findByUser(User user);
}
