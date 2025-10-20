package com.encentral.attendance.impl;

import com.encentral.attendance.api.IAttendance;
import com.encentral.attendance.api.IAttendanceService;
import com.encentral.attendance.model.AttendanceMapper;
import com.google.inject.AbstractModule;

public class AttendanceModule extends AbstractModule {
    @Override
    protected void configure(){
        bind(IAttendance.class).to(AttendanceImpl.class);
        bind(IAttendanceService.class).to(AttendanceServiceImpl.class);
        bind(AttendanceMapper.class).toInstance(AttendanceMapper.INSTANCE);

    }
}
