package com.auunes.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 */
public class MD5Util {
    
    /**
     * 使用MD5加密字符串
     * @param input 待加密的字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        
        try {
            // 创建MessageDigest实例并指定算法为MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            // 计算MD5摘要
            byte[] messageDigest = md.digest(input.getBytes());
            
            // 转换为16进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5加密失败", e);
        }
    }
    
    /**
     * 验证明文密码是否与MD5加密字符串匹配
     * @param plaintext 明文密码
     * @param md5 MD5加密字符串
     * @return 是否匹配
     */
    public static boolean verify(String plaintext, String md5) {
        if (plaintext == null || md5 == null) {
            return false;
        }
        
        String encryptedText = encrypt(plaintext);
        return encryptedText != null && encryptedText.equals(md5);
    }
} 