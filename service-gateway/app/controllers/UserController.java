package controllers;

import com.encentral.scaffold.commons.ApiUtils.ApiResponse;
import com.encentral.user.api.IUserService;
import com.encentral.user.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;

public class UserController extends HomeController {
    private final IUserService userService;
    private final FormFactory formFactory;

    @Inject
    public UserController(IUserService userService, FormFactory formFactory) {
        this.userService = userService;
        this.formFactory = formFactory;
    }

    @Operation(summary = "Sign in", description = "Sign in for both Admin and Employee")
    public Result signIn(Http.Request request) {
        Form<LoginRequest> userLoginForm = formFactory.form(LoginRequest.class);
        Form<LoginRequest> boundForm = userLoginForm.bind(request.body().asJson());
        if (boundForm.hasErrors()) {
            return badRequest(boundForm.errorsAsJson());
        } else {
            LoginRequest loginRequest = boundForm.get();
            LoginResponse response = userService.signIn(loginRequest);
            if (response.getToken() == null) {
                return unauthorized(Json.toJson(response));
            }

            return ok(Json.toJson(response));
        }
    }

    @Operation(summary = "Add Employee", description = "Admin adds a new employee (requires admin token)")
    public Result addEmployee(Http.Request request) {

        JsonNode json = request.body().asJson();
        if (json == null || !json.has("token"))
            return badRequest(Json.toJson(new ApiResponse(false, "token required")));
        String token = json.get("token").asText();

        Form<UserRequest> userForm = formFactory.form(UserRequest.class);
        Form<UserRequest> boundForm = userForm.bind(request.body().asJson());
        if (boundForm.hasErrors()) {
            return badRequest(boundForm.errorsAsJson());
        } else {
            UserRequest userRequest = boundForm.get();
            UserResponse response = userService.addEmployee(token, userRequest);
            return created(Json.toJson(new ApiResponse(true, "Created User Succesfully", response)));
        }
    }

    @Operation(summary = "Remove Employee", description = "Admin removes an employee (requires admin token)")
    public Result removeEmployee(Http.Request request, Long employeeId) {

        JsonNode json = request.body().asJson();
        if (json == null || !json.has("token"))
            return badRequest(Json.toJson(new ApiResponse(false, "token required")));

        String token = json.get("token").asText();
        ApiResponse response = userService.removeEmployee(token, employeeId);

        return ok(Json.toJson(response));
    }

    @Operation(summary = "Get Employees", description = "Admin gets all employees (requires admin token)")
    public Result getEmployees(Http.Request request) {

        JsonNode json = request.body().asJson();
        if (json == null || !json.has("token"))
            return badRequest(Json.toJson(new ApiResponse(false, "token required")));

        String token = json.get("token").asText();
        List<UserResponse> employees = userService.getEmployees(token);
        return ok(Json.toJson(new ApiResponse(true, "Employees retrieved successfully", employees)));
    }

    public Result updatePassword(Http.Request request) {

        Form<UpdatePasswordRequest> userLoginForm = formFactory.form(UpdatePasswordRequest.class);
        Form<UpdatePasswordRequest> boundForm = userLoginForm.bind(request.body().asJson());
        if (boundForm.hasErrors()) {
            return badRequest(boundForm.errorsAsJson());
        } else {
            UpdatePasswordRequest req = boundForm.get();
            ApiResponse resp = userService.updatePassword(req);
            return ok(Json.toJson(resp));
        }

    }
}
