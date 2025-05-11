package com.auunes.common;

import lombok.Data;

/**
 * 通用响应对象
 * @param <T> 响应数据类型
 */
@Data
public class R<T> {
    /**
     * 错误码，0表示成功
     */
    private int error;
    
    /**
     * 响应数据
     */
    private T body;
    
    /**
     * 响应消息
     */
    private String message;
    
    /**
     * 构造一个成功的响应
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 响应对象
     */
    public static <T> R<T> success(T data) {
        return success(data, "");
    }
    
    /**
     * 构造一个成功的响应
     * @param data 响应数据
     * @param message 响应消息
     * @param <T> 数据类型
     * @return 响应对象
     */
    public static <T> R<T> success(T data, String message) {
        R<T> result = new R<>();
        result.setError(0);
        result.setBody(data);
        result.setMessage(message);
        return result;
    }
    
    /**
     * 构造一个失败的响应
     * @param error 错误码
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 响应对象
     */
    public static <T> R<T> error(int error, String message) {
        R<T> result = new R<>();
        result.setError(error);
        result.setMessage(message);
        return result;
    }
    
    /**
     * 构造一个系统异常响应
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 响应对象
     */
    public static <T> R<T> systemError(String message) {
        return error(500, message);
    }
    
    /**
     * 构造一个业务异常响应
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 响应对象
     */
    public static <T> R<T> businessError(String message) {
        return error(400, message);
    }
    
    /**
     * 构造一个未认证响应
     * @param <T> 数据类型
     * @return 响应对象
     */
    public static <T> R<T> unauthorized() {
        return error(401, "未登录或登录已过期");
    }
} 