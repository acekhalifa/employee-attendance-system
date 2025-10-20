package controllers;

import com.encentral.attendance.api.IAttendanceService;
import com.encentral.attendance.model.AttendanceResponse;
import com.encentral.scaffold.commons.ApiUtils.ApiResponse;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Attendance Management", description = "Operations related to marking and retrieving employee attendance.")
public class AttendanceController extends HomeController {
    private final IAttendanceService attendanceService;
    private final FormFactory formFactory;

    @Inject
    public AttendanceController(IAttendanceService attendanceService, FormFactory formFactory) {
        this.attendanceService = attendanceService;
        this.formFactory = formFactory;
    }


    @Operation(
            summary = "Mark employee attendance",
            description = "Allows an employee to mark their daily attendance using a unique token.")
    public Result markAttendance() {
        Http.Request request = request();
        JsonNode json = request.body().asJson();
        if (json == null || !json.has("token"))
            return badRequest(Json.toJson(new ApiResponse(false, "token required")));
        String token = json.get("token").asText();
        try {
            boolean marked = attendanceService.markAttendance(token);
            if (marked) return ok(Json.toJson(new ApiResponse(true, "Attendance marked successfully")));
            else return ok(Json.toJson(new ApiResponse(false, "Attendance already marked")));
        } catch (IllegalArgumentException e) {
            return badRequest(Json.toJson(new ApiResponse(false, e.getMessage())));
        } catch (Exception e) {
            return internalServerError(Json.toJson(new ApiResponse(false, "Internal error")));
        }
    }
    @Operation(
            summary = "Get daily attendance records",
            description = "Retrieves all attendance records for a specific date. Requires an admin token for authorization.")
    public Result getDailyAttendance(String dateStr) {

        Http.Request request = request();
        JsonNode json = request.body().asJson();
        if (json == null || !json.has("token"))
            return badRequest(Json.toJson(new ApiResponse(false,"token required")));
        String adminToken = json.get("token").asText();

        try {
            LocalDate date = LocalDate.parse(dateStr);
            List<AttendanceResponse> list = attendanceService.getDailyAttendance(adminToken, date);
            return ok(Json.toJson(list));
        } catch (IllegalArgumentException e) {
            return badRequest(Json.toJson(new ApiResponse(false, e.getMessage())));
        } catch (Exception e) {
            return internalServerError(Json.toJson(new ApiResponse(false, "Internal error")));
        }
    }

}
