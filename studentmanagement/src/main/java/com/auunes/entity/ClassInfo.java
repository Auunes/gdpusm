package com.auunes.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 班级实体类
 */
@Data
public class ClassInfo {
    /**
     * 班级ID
     */
    private Integer id;
    
    /**
     * 班级名称
     */
    private String className;
    
    /**
     * 年级
     */
    private String grade;
    
    /**
     * 专业
     */
    private String major;
    
    /**
     * 班主任ID
     */
    private Integer teacherId;
    
    /**
     * 学生人数（非数据库字段）
     */
    private Integer studentCount;
    
    /**
     * 学生列表（非数据库字段）
     */
    private List<Student> students;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 