package com.auunes.service.impl;

import com.auunes.entity.Admin;
import com.auunes.exception.BusinessException;
import com.auunes.mapper.AdminMapper;
import com.auunes.service.AdminService;
import com.auunes.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理员服务实现类
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String username, String password) {
        // 查询管理员
        Admin admin = adminMapper.selectByUsername(username);
        if (admin == null) {
            throw new BusinessException("用户名或密码错误");
        }
        
        // 验证密码 - 对明文密码进行MD5加密后再比较
        String encryptedPassword = MD5Util.encrypt(password);
        if (!admin.getPassword().equals(encryptedPassword)) {
            throw new BusinessException("用户名或密码错误");
        }
        
        return admin;
    }

    @Override
    public Admin getById(Integer id) {
        return adminMapper.selectById(id);
    }
} 