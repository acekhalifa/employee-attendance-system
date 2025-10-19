package com.encentral.attendance.impl;

import com.attendancemgmt.entities.Attendance;
import com.encentral.attendance.api.IAttendance;
import com.encentral.attendance.api.IAttendanceService;
import com.encentral.attendance.model.AttendanceResponse;
import com.encentral.attendance.model.AttendanceMapper;
import com.encentral.scaffold.commons.util.TokenValidator;

import javax.inject.Inject;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AttendanceServiceImpl implements IAttendanceService {
    private final IAttendance attendanceRepo;
    private final AttendanceMapper attendanceMapper;
    private final TokenValidator tokenValidator;

    @Inject
    public AttendanceServiceImpl(IAttendance attendanceRepo, AttendanceMapper attendanceMapper, TokenValidator tokenValidator) {
        this.attendanceRepo = attendanceRepo;
        this.attendanceMapper = attendanceMapper;
        this.tokenValidator = tokenValidator;
    }


    @Override
    public boolean markAttendance(String userToken) {
        var user = tokenValidator.verifyUser(userToken);
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();
        DayOfWeek dayOfWeek = today.getDayOfWeek();

        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalStateException("Today is not a work day. Attendance cannot be marked on weekends.");
        }

        LocalTime workStart = LocalTime.of(9, 0);
        LocalTime workEnd = LocalTime.of(17, 0);

        if (currentTime.isBefore(workStart)) {
            throw new IllegalStateException("Too early to mark attendance. Work hours are 9:00 AM - 5:00 PM.");
        }

        if (currentTime.isAfter(workEnd) || currentTime.equals(workEnd)) {
            throw new IllegalStateException("Too late to mark attendance. Work hours are 9:00 AM - 5:00 PM.");
        }
        var OptAttendance = attendanceRepo.findByUserAndDate(user, today);
        if(OptAttendance.isPresent()) return false;

        var attendance = new Attendance();
        attendance.setUser(user);
        attendance.setAttendanceDate(today);
        attendance.setMarkedAt(now);
        attendanceRepo.save(attendance);

        return true;
    }

    @Override
    public List<AttendanceResponse> getDailyAttendance(String adminToken, LocalDate date) {
        var adminUser = tokenValidator.verifyAdmin(adminToken);
        var attendances = attendanceRepo.findByDate(date);
        return attendanceMapper.toDTOList(attendances);
    }

}
