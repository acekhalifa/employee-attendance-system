package com.encentral.attendance.api;

import com.encentral.attendance.model.AttendanceResponse;

import java.time.LocalDate;
import java.util.List;

public interface IAttendanceService {
    boolean markAttendance(String userToken);
    List<AttendanceResponse> getDailyAttendance(String adminToken, LocalDate date);
}
