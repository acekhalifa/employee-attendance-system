package controllers;

import com.encentral.scaffold.commons.ApiUtils.ApiResponse;
import com.encentral.user.api.IUserService;
import com.encentral.user.model.*;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class UserController extends HomeController{
    private final IUserService userService;

    @Inject
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Sign in", description = "Sign in for both Admin and Employee")
    public Result signIn(Http.Request request) {
        try {
           LoginRequest loginRequest  = Json.fromJson(request.body().asJson(),LoginRequest.class);
            if (loginRequest == null) {
                return badRequest(Json.toJson(new ApiResponse(false, "Invalid JSON")));
            }

            LoginResponse response = userService.signIn(loginRequest);

            if (response.getToken() == null) {
                return unauthorized(Json.toJson(response));
            }

            return ok(Json.toJson(response));
        } catch (Exception e) {
            return badRequest(Json.toJson(new ApiResponse(false, e.getMessage())));
        }
    }

    @Operation(summary = "Add Employee", description = "Admin adds a new employee (requires admin token)")
    public Result addEmployee(Http.Request request) {
        try {
            String token = request.header("Authorization").orElse("");
            if (token.isEmpty()) {
                return badRequest(Json.toJson(new ApiResponse(false, "Invalid Token")));
            }

            UserRequest userRequest  = Json.fromJson(request.body().asJson(),UserRequest.class);
            if (userRequest == null) {
                return badRequest(Json.toJson(new ApiResponse(false, "Invalid JSON")));
            }


            UserResponse response = userService.addEmployee(token, userRequest);

            return created(Json.toJson(new ApiResponse(true, "Created User Succesfully", response)));
        } catch (IllegalArgumentException e) {
            return badRequest(Json.toJson(new ApiResponse(false, e.getMessage())));
        } catch (Exception e) {
            return internalServerError(Json.toJson(new ApiResponse(false, "An error occurred")));
        }
    }

    @Operation(summary = "Remove Employee", description = "Admin removes an employee (requires admin token)")
    public Result removeEmployee(Http.Request request, Long employeeId) {
        try {
            String token = request.header("Authorization").orElse("");
            if (token.isEmpty()) {
                return badRequest(Json.toJson(new ApiResponse(false, "Invalid Token")));
            }
            ApiResponse response = userService.removeEmployee(token, employeeId);

            return ok(Json.toJson(response));
        } catch (IllegalArgumentException e) {
            return badRequest(Json.toJson(new ApiResponse(false, e.getMessage())));
        } catch (Exception e) {
            return internalServerError(Json.toJson(new ApiResponse(false, "An error occurred")));
        }
    }

    @Operation(summary = "Get Employees", description = "Admin gets all employees (requires admin token)")
    public Result getEmployees(Http.Request request) {
        try {
            String token = request.header("Authorization").orElse("");
            if (token.isEmpty()) {
                return badRequest(Json.toJson(new ApiResponse(false, "Invalid Token")));
            }

            List<UserResponse> employees = userService.getEmployees(token);

            return ok(Json.toJson(new ApiResponse(true, "Employees retrieved successfully", employees)));
        } catch (IllegalArgumentException e) {
            return badRequest(Json.toJson(new ApiResponse(false, e.getMessage())));
        } catch (Exception e) {
            return internalServerError(Json.toJson(new ApiResponse(false, "An error occurred")));
        }
    }

    public Result updatePassword(Http.Request request) {
        UpdatePasswordRequest req = Json.fromJson(request.body().asJson(), UpdatePasswordRequest.class);
        if (req == null || req.getToken() == null || req.getPassword() == null) {
            return badRequest(Json.toJson(new ApiResponse(false,"token and new password required")));
        }
        ApiResponse resp = userService.updatePassword(req);
        return ok(Json.toJson(resp));
    }
}
