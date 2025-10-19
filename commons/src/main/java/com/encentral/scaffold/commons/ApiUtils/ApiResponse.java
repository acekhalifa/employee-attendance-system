package com.encentral.scaffold.commons.ApiUtils;

public class ApiResponse {
    private boolean status;
    private String message;
    private Object data;
    public ApiResponse(boolean status, String message)
    {
        this.status = status;
        this.message = message;
        data = null;
    }

    public ApiResponse(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
