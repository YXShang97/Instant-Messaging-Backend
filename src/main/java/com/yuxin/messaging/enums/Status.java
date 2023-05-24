package com.yuxin.messaging.enums;

public enum Status {
    OK(1000, "SUCCESS"),
    PASSWORD_NOT_MATCH(1001, "Password are not matched."),
    EMPTY_USERNAME(1002, "Username is empty."),
    EMAIL_EXISTS_ALREADY(1003, "Email already exists."),
    USERNAME_EXISTS_ALREADY(1004, "Username already exists."),
    UNKNOWN_EXCEPTION(9999, "Unknown exception.");

    private int code;
    private String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
