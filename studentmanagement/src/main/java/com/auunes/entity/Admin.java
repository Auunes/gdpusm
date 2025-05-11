package com.auunes.entity;

import lombok.Data;

import java.util.Date;

/**
 * 管理员实体类
 */
@Data
public class Admin {
    /**
     * 管理员ID
     */
    private Integer id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
} 