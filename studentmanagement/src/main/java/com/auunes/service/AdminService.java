package com.auunes.service;

import com.auunes.entity.Admin;

/**
 * 管理员服务接口
 */
public interface AdminService {
    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @return 管理员信息
     */
    Admin login(String username, String password);
    
    /**
     * 根据ID查询管理员
     * @param id 管理员ID
     * @return 管理员信息
     */
    Admin getById(Integer id);
} 