package com.yuxin.messaging.enums;

import org.springframework.http.HttpStatus;

public enum Status {
    OK(1000, "SUCCESS", HttpStatus.OK),
    PASSWORD_NOT_MATCH(1001, "Password are not matched.", HttpStatus.BAD_REQUEST),
    EMPTY_USERNAME(1002, "Username is empty.", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTS_ALREADY(1003, "Email already exists.", HttpStatus.BAD_REQUEST),
    USERNAME_EXISTS_ALREADY(1004, "Username already exists.", HttpStatus.BAD_REQUEST),
    UNKNOWN_EXCEPTION(9999, "Unknown exception.", HttpStatus.INTERNAL_SERVER_ERROR);

    private int code;
    private String message;
    private HttpStatus httpStatus;

    Status(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
