package com.encentral.user.api;

import com.encentral.attendance.model.AttendanceResponse;
import com.encentral.scaffold.commons.ApiUtils.ApiResponse;
import com.encentral.user.model.*;

import java.time.LocalDate;
import java.util.List;

public interface IUserService {
    LoginResponse signIn(LoginRequest request);
    UserResponse addEmployee(String adminToken, UserRequest request);
    ApiResponse removeEmployee(String adminToken, Long employeeId);
    List<UserResponse> getEmployees(String adminToken);
    ApiResponse updatePassword(UpdatePasswordRequest request);
}
