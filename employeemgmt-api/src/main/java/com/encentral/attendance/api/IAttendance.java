package com.encentral.attendance.api;

import com.attendancemgmt.entities.Attendance;
import com.attendancemgmt.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IAttendance {

    Optional<Attendance> findByUserAndDate(User user, LocalDate date);
    List<Attendance> findByDate(LocalDate date);
    Attendance save(Attendance attendance);
}
