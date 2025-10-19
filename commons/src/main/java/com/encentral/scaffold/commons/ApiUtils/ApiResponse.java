package com.encentral.scaffold.commons.ApiUtils;

public class ApiResponse<T> {
    private boolean status;
    private String message;
    private T data;
    public ApiResponse(boolean status, String message)
    {
        this.status = status;
        this.message = message;
        data = null;
    }

    public ApiResponse(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
