package com.encentral.attendance.impl;

import com.attendancemgmt.entities.Attendance;
import com.attendancemgmt.entities.User;
import com.encentral.attendance.api.IAttendance;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.NoResultException;
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
    public Optional<Attendance> findByUserAndDate(User user, LocalDate date) {
        try {
            Attendance attendance = jpaApi.withTransaction(em -> {
                return em.createQuery(
                                "SELECT a FROM Attendance a WHERE a.user.id = :userId AND a.attendanceDate = :date",
                                Attendance.class)
                        .setParameter("userId", user.getId())
                        .setParameter("date", date)
                        .getSingleResult();
            });
            return Optional.of(attendance);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Attendance> findByDate(LocalDate date) {
        return jpaApi.withTransaction(em -> {
            return em.createQuery(
                            "SELECT a FROM Attendance a WHERE a.attendanceDate = :date",
                            Attendance.class)
                    .setParameter("date", date)
                    .getResultList();
        });
    }

    @Override
    public Attendance save(Attendance attendance) {
        return jpaApi.withTransaction(em -> {
            if (attendance.getId() == null) {
                em.persist(attendance);
                return attendance;
            } else {
                return em.merge(attendance);
            }
        });
    }
}
