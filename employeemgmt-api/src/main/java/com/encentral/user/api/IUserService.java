package com.encentral.user.api;

import com.encentral.attendance.model.AttendanceResponse;
import com.encentral.user.model.UserRequest;
import com.encentral.user.model.UserResponse;

import java.time.LocalDate;
import java.util.List;

public interface IUserService {
    SignInResponse signIn(String email, String password);
    UserResponse addEmployee(UserRequest request);
    ApiResponse removeEmployee(String adminToken, Long employeeId);
    List<UserResponse> getEmployees(String adminToken);
    List<AttendanceResponse> getDailyAttendance(String adminToken, LocalDate date);
    ApiResponse updatePassword(String userToken, String newPassword);
}
