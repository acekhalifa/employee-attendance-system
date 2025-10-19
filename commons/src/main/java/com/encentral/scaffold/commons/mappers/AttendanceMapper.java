package com.encentral.scaffold.commons.mappers;

import com.attendancemgmt.entities.Attendance;
import com.encentral.attendance.model.AttendanceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "jsr330", uses = {UserMapper.class})
public interface AttendanceMapper {

    AttendanceMapper INSTANCE = Mappers.getMapper(AttendanceMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(expression = "java(attendance.getUser().getFirstName() + \" \" + attendance.getUser().getLastName())", target = "userName")
    AttendanceResponse toDTO(Attendance attendance);

    List<AttendanceResponse> toDTOList(List<Attendance> attendances);

}
