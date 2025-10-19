package com.encentral.attendance.impl;

import com.attendancemgmt.entities.Attendance;
import com.attendancemgmt.entities.User;
import com.encentral.attendance.api.IAttendance;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AttendanceImpl implements IAttendance {
    private final JPAApi jpaApi;

    @Inject
    public AttendanceImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override
    public Optional<Attendance> findByUserAndAttendanceDate(User user, LocalDate date) {

        return Optional.empty();
    }

    @Override
    public List<Attendance> findByAttendanceDate(LocalDate date) {
        return List.of();
    }

    @Override
    public List<Attendance> findByUser(User user) {
        return List.of();
    }
}
