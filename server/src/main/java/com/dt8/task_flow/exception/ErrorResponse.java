package com.dt8.task_flow.exception;

public class ErrorResponse {
    private int status;
    private String message;

    public ErrorResponse() {}

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
