package com.auunes.utils;

import lombok.Data;

/**
 * 统一API响应结果封装
 */
@Data
public class Result<T> {
    private int error;
    private T body;
    private String message;

    public static <T> Result<T> success(T data) {
        return success(data, "");
    }

    public static <T> Result<T> success(T data, String message) {
        Result<T> result = new Result<>();
        result.setError(0);
        result.setBody(data);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> error(int error, String message) {
        Result<T> result = new Result<>();
        result.setError(error);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> systemError(String message) {
        return error(500, message);
    }

    public static <T> Result<T> businessError(String message) {
        return error(400, message);
    }
} 