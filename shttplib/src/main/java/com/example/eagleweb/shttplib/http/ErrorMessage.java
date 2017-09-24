package com.example.eagleweb.shttplib.http;

/**
 * @创建者 帅子
 * @创建时间 17/9/23.
 * @描述
 */

public class ErrorMessage {
    public static final int ERROR_NETWORK = 0;
    public static final int ERROR_PARSE   = 1;
    public static final int ERROR_OTHER   = 2;
    private int    errorType;
    private String msg;

    public int getErrorType() {
        return errorType;
    }

    public void setErrorType(int errorType) {
        this.errorType = errorType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static ErrorMessage createByReqError(Throwable e) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.errorType = ERROR_PARSE;
        errorMessage.msg = e.toString();
        return errorMessage;
    }

    public static ErrorMessage createByNotNetwork(Throwable e) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.errorType = ERROR_NETWORK;
        errorMessage.msg = e.toString();
        return errorMessage;
    }

    public static ErrorMessage create(String s) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.errorType = ERROR_OTHER;
        errorMessage.msg = s;
        return errorMessage;
    }

    public static ErrorMessage create(String s, int errorType) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.errorType = errorType;
        errorMessage.msg = s;
        return errorMessage;
    }

    @Override
    public String toString() {
        return "[errorType:" + errorType + " msg:" + msg + "]";
    }
}
