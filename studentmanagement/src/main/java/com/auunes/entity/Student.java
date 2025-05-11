package com.auunes.entity;

import lombok.Data;

import java.util.Date;

/**
 * 学生实体类
 */
@Data
public class Student {
    /**
     * 学生ID
     */
    private Integer id;
    
    /**
     * 学号
     */
    private String studentId;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 性别
     */
    private String gender;
    
    /**
     * 年龄
     */
    private Integer age;
    
    /**
     * 班级ID
     */
    private Integer classId;
    
    /**
     * 班级名称（非数据库字段）
     */
    private String className;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 地址
     */
    private String address;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 