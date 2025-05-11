package com.auunes.entity;

import lombok.Data;

import java.util.Date;

/**
 * 教师实体类
 */
@Data
public class Teacher {
    /**
     * 教师ID
     */
    private Integer id;
    
    /**
     * 教师工号
     */
    private String teacherId;
    
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
     * 所属院系
     */
    private String department;
    
    /**
     * 职称
     */
    private String title;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 