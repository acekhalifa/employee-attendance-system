package com.encentral.attendance.model;

import com.attendancemgmt.entities.Attendance;
import com.encentral.user.model.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "jsr330", uses = {UserMapper.class})
public interface AttendanceMapper {

    AttendanceMapper INSTANCE = Mappers.getMapper(AttendanceMapper.class);

    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(expression = "java(attendance.getUser().getFirstName() + \" \" + attendance.getUser().getLastName())", target = "userFullName")
    AttendanceResponse toDTO(Attendance attendance);

    List<AttendanceResponse> toDTOList(List<Attendance> attendances);


}
