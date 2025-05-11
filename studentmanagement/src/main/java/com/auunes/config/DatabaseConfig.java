package com.auunes.config;

import com.auunes.utils.NetworkUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * 数据库配置
 */
@Configuration
@PropertySource("classpath:jdbc.properties")
public class DatabaseConfig {

    @Value("${jdbc.driver}")
    private String jdbcDriver;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String jdbcUsername;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    // 虚拟机IP地址，这里使用硬编码，实际项目中可以通过配置文件读取
    private static final String VM_IP = "192.168.81.131";

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * 初始化时检测网络并设置数据库连接
     */
    @PostConstruct
    public void init() {
        System.out.println("\n================== 数据库连接检测 ==================");
        System.out.println("正在检测数据库连接，数据库虚拟机IP: " + VM_IP);
        
        // 检测是否能够直接连接到虚拟机数据库
        boolean vmReachable = NetworkUtil.isVmReachable(VM_IP);
        
        if (vmReachable) {
            System.out.println("✓ 成功连接到虚拟机数据库: " + VM_IP);
            System.out.println("使用JDBC URL: " + jdbcUrl);
        } else {
            System.out.println("✗ 无法直接连接到虚拟机数据库: " + VM_IP);
            System.out.println("检查网络适配器...");
        
        }
        System.out.println("================== 数据库检测完成 ==================\n");
    }
}
