package com.auunes.config;

import com.auunes.utils.JwtTokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT认证拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtConfig jwtConfig;
    private final JwtTokenUtil jwtTokenUtil;

    public JwtInterceptor(JwtConfig jwtConfig, JwtTokenUtil jwtTokenUtil) {
        this.jwtConfig = jwtConfig;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String token = request.getHeader(jwtConfig.getHeader());
        if (!StringUtils.hasText(token)) {
            sendErrorResponse(response, "请先登录");
            return false;
        }

        try {
            if (!jwtTokenUtil.validateToken(token)) {
                sendErrorResponse(response, "token已过期或无效");
                return false;
            }
            return true;
        } catch (Exception e) {
            sendErrorResponse(response, "token验证异常");
            return false;
        }
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"error\":401,\"message\":\"" + message + "\",\"body\":null}");
    }
}