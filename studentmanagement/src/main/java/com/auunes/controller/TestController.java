package com.auunes.controller;

import com.auunes.common.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 测试控制器
 */
@RestController
public class TestController {

    /**
     * GET测试接口
     * @return 测试结果
     */
    @GetMapping("/test")
    public R<String> testGet() {
        return R.success("GET请求成功");
    }

    /**
     * POST测试接口
     * @param params 请求参数
     * @return 测试结果
     */
    @PostMapping("/test")
    public R<Map<String, Object>> testPost(@RequestBody(required = false) Map<String, Object> params) {
        return R.success(params, "POST请求成功");
    }
} 