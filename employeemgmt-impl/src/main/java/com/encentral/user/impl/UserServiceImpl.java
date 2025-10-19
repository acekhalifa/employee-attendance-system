package com.encentral.user.impl;

import com.attendancemgmt.entities.User;
import com.encentral.attendance.model.AttendanceResponse;
import com.encentral.scaffold.commons.ApiUtils.ApiResponse;
import com.encentral.scaffold.commons.util.DefaultPinGenerator;
import com.encentral.scaffold.commons.util.TokenValidator;
import com.encentral.user.api.IUser;
import com.encentral.user.api.IUserService;
import com.encentral.user.model.LoginResponse;
import com.encentral.user.model.UserMapper;
import com.encentral.user.model.UserRequest;
import com.encentral.user.model.UserResponse;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements IUserService {
    private final IUser iUser;
    private final UserMapper userMapper;
    private final TokenValidator tokenValidator;

    @Inject
    public UserServiceImpl(IUser iUser, UserMapper userMapper, TokenValidator tokenValidator) {
        this.iUser = iUser;
        this.userMapper = userMapper;
        this.tokenValidator = tokenValidator;
    }

    @Override
    public LoginResponse signIn(String email, String password) {
        return null;
    }

    @Override
    public UserResponse addEmployee(String adminToken, UserRequest request) {
        if(iUser.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("User with provided email exists");

        }

        User adminUser = tokenValidator.verifyAdmin(adminToken);

        String pin = DefaultPinGenerator.generatePin();

        User employee = userMapper.toEntity(request);
        employee.setRole(User.Role.EMPLOYEE);
        employee.setPassword(pin);
        employee.setToken(UUID.randomUUID().toString());

        User savedEmployee = iUser.save(employee);

        return null;
    }

    @Override
    public ApiResponse removeEmployee(String adminToken, User employee) {
        return null;
    }

    @Override
    public List<UserResponse> getEmployees(String adminToken) {
        return List.of();
    }

    @Override
    public List<AttendanceResponse> getDailyAttendance(String adminToken, LocalDate date) {
        return List.of();
    }

    @Override
    public ApiResponse updatePassword(String userToken, String newPassword) {
        return null;
    }
}
