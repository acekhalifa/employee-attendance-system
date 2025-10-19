package com.encentral.user.impl;

import com.attendancemgmt.entities.User;
import com.encentral.attendance.model.AttendanceResponse;
import com.encentral.scaffold.commons.ApiUtils.ApiResponse;
import com.encentral.scaffold.commons.util.DefaultPinGenerator;
import com.encentral.scaffold.commons.util.TokenValidator;
import com.encentral.user.api.IUser;
import com.encentral.user.api.IUserService;
import com.encentral.user.model.*;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
    public LoginResponse signIn(LoginRequest request) {
        User user = iUser.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("User with Email "+request.getEmail()+" not found"));

        if(!Objects.equals(user.getPassword(), request.getPassword()))
                throw new SecurityException("Wrong password entered");

        return new LoginResponse(user.getEmail(), user.getToken());
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
        return userMapper.toDTO(savedEmployee);
    }

    @Override
    public ApiResponse removeEmployee(String adminToken, Long employeeId) {
        tokenValidator.verifyAdmin(adminToken);

        Optional<User> employeeOpt = iUser.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            return new ApiResponse(false, "Employee not found");
        }

        User employee = employeeOpt.get();
        if (employee.getRole() == User.Role.ADMIN) {
            return new ApiResponse(false, "Cannot remove admin user");
        }

        iUser.deleteUser(employee);
        return new ApiResponse(true, "Employee removed successfully");

    }

    @Override
    public List<UserResponse> getEmployees(String adminToken) {
        tokenValidator.verifyAdmin(adminToken);
        return userMapper.toDTOList(iUser.findAll());
    }

    @Override
    public ApiResponse updatePassword(UpdatePasswordRequest request) {
        User user = tokenValidator.verifyUser(request.getToken());
        user.setPassword(request.getPassword());
        iUser.save(user);

        return new ApiResponse(true, "Password updated successfully");
    }
}
