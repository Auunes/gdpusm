package com.auunes.controller;

import com.auunes.common.R;
import com.auunes.entity.Admin;
import com.auunes.service.AdminService;
import com.auunes.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 登录
     * @param loginParams 登录参数
     * @return 登录结果
     */
    @PostMapping("/login")
    public R<Map<String, Object>> login(@RequestBody Map<String, String> loginParams) {
        String username = loginParams.get("username");
        String password = loginParams.get("password");
        
        // 调用service进行登录验证
        Admin admin = adminService.login(username, password);
        
        // 生成token
        String token = jwtTokenUtil.generateToken(admin.getId(), admin.getUsername());
        
        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("username", admin.getUsername());
        result.put("userId", admin.getId());
        
        return R.success(result);
    }
} 