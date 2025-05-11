package com.auunes.config;

import lombok.Data;

/**
 * JWT配置类
 */
@Data
public class JwtConfig {
    /**
     * JWT加解密使用的密钥
     */
    private String secret;
    
    /**
     * JWT的过期时间
     */
    private long expiration;
    
    /**
     * JWT的header
     */
    private String header;

} 