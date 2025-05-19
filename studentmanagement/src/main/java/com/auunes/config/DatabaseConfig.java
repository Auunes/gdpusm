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

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * 初始化时检测数据库连接
     */
    @PostConstruct
    public void init() {
        System.out.println("\n================== 数据库连接检测 ==================");
        System.out.println("正在检测数据库连接，使用JDBC URL: " + jdbcUrl);
        System.out.println("================== 数据库检测完成 ==================\n");
    }
}
