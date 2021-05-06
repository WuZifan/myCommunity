package com.roland.community.community.Exception;

public class CustomizeException extends RuntimeException {

    private Integer code;
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code=errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
