package com.encentral.user.api;

import com.attendancemgmt.entities.User;
import com.encentral.attendance.model.AttendanceResponse;
import com.encentral.scaffold.commons.ApiUtils.ApiResponse;
import com.encentral.user.model.LoginResponse;
import com.encentral.user.model.UserRequest;
import com.encentral.user.model.UserResponse;

import java.time.LocalDate;
import java.util.List;

public interface IUserService {
    LoginResponse signIn(String email, String password);
    UserResponse addEmployee(String adminToken, UserRequest request);
    ApiResponse removeEmployee(String adminToken, User employee);
    List<UserResponse> getEmployees(String adminToken);
    List<AttendanceResponse> getDailyAttendance(String adminToken, LocalDate date);
    ApiResponse updatePassword(String userToken, String newPassword);
}
