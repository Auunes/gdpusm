package com.auunes;

import com.auunes.utils.NetworkUtil;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11NioProtocol;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

/**
 * 学生管理系统启动类
 * 内嵌Tomcat启动Spring MVC应用
 */
public class StudentManagementApplication {
    // 从配置文件读取虚拟机IP地址
    private static String VM_IP;
    
    static {
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream("src/main/resources/jdbc.properties");
            props.load(fis);
            fis.close();
            
            // 从JDBC URL中提取IP地址
            String jdbcUrl = props.getProperty("jdbc.url");
            VM_IP = jdbcUrl.split("//")[1].split(":")[0];
        } catch (IOException e) {
            System.err.println("无法读取配置文件，使用默认IP地址(localhost): " + e.getMessage());
            VM_IP = "localhost"; // 默认值改为localhost
        }
    }
    
    /**
     * 主方法，使用内嵌Tomcat启动应用
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        try {
            // 检测网络适配器
            detectNetworkAdapters();
            
            // 测试8080端口是否可用
            testPort(8080);
            
            // 创建Tomcat实例
            Tomcat tomcat = new Tomcat();
            
            // 设置Tomcat临时目录
            String tomcatBaseDir = "tomcat";
            tomcat.setBaseDir(tomcatBaseDir);
            
            // 添加HTTP连接器，手动配置
            Connector connector = new Connector(Http11NioProtocol.class.getName());
            connector.setPort(8080);
            
            // 获取最适合的绑定地址
            String bindAddress = getPreferredBindAddress();
            System.out.println("使用绑定地址: " + bindAddress);
            connector.setProperty("address", bindAddress);
            
            tomcat.getService().addConnector(connector);
            tomcat.setConnector(connector);
            
            // 设置webapp目录
            String webappDir = new File("studentManagement/src/main/webapp").getAbsolutePath();
            if (!new File(webappDir).exists()) {
                webappDir = new File("src/main/webapp").getAbsolutePath();
            }
            
            System.out.println("使用webapp目录: " + webappDir);
            Context context = tomcat.addWebapp("", webappDir);
            
            // 设置自动部署为false，避免一些问题
            tomcat.getHost().setAutoDeploy(false);
            
            // 启动Tomcat服务器
            tomcat.start();
            
            // 尝试主动进行绑定测试以确认端口可用
            try {
                InetAddress bindAddr = InetAddress.getByName(bindAddress);
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(bindAddr, 8080), 1000);
                System.out.println("端口绑定成功，服务器已启动！");
                socket.close();
            } catch (Exception e) {
                System.err.println("警告：无法连接到服务器端口，但Tomcat启动没有报错：" + e.getMessage());
            }
            
            System.out.println("========================================");
            System.out.println("学生管理系统已启动");
            System.out.println("网络访问地址: http://" + bindAddress + ":8080");
            System.out.println("API接口地址: http://" + bindAddress + ":8080/api");
            System.out.println("========================================");
            
            // 等待请求，不要退出主线程
            tomcat.getServer().await();
            
        } catch (LifecycleException e) {
            System.err.println("Tomcat启动失败: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("应用启动失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 检测网络适配器
     */
    private static void detectNetworkAdapters() {
        System.out.println("\n================ 网络适配器检测 ================");
        System.out.println("正在检测所有网络适配器...");
        
        // 获取所有网络适配器
        List<String> allIps = NetworkUtil.getAllAdapterIps();
        System.out.println("发现 " + allIps.size() + " 个网络适配器IP地址");
        
        // 获取物理适配器
        List<String> physicalIps = NetworkUtil.getPhysicalAdapterIps();
        if (!physicalIps.isEmpty()) {
            System.out.println("发现 " + physicalIps.size() + " 个物理适配器IP地址: " + physicalIps);
        } else {
            System.out.println("未发现物理网络适配器，将使用其他可用适配器");
        }
        
        // 测试与虚拟机的连接
        if (NetworkUtil.isVmReachable(VM_IP)) {
            System.out.println("✓ 虚拟机 " + VM_IP + " 可达");
        } else {
            System.out.println("✗ 无法连接到虚拟机 " + VM_IP + "，请检查网络设置");
        }
        System.out.println("================ 检测完成 ================\n");
    }
    
    /**
     * 获取首选的绑定地址
     * @return 绑定地址
     */
    private static String getPreferredBindAddress() {
        // 首先尝试使用物理网卡
        List<String> physicalIps = NetworkUtil.getPhysicalAdapterIps();
        if (!physicalIps.isEmpty()) {
            return physicalIps.get(0);
        }
        
        // 如果没有找到物理网卡，尝试使用所有可用的适配器
        List<String> allIps = NetworkUtil.getAllAdapterIps();
        if (!allIps.isEmpty()) {
            // 过滤掉虚拟机适配器的IP
            for (String ip : allIps) {
                if (!ip.startsWith("192.168.8") && !ip.startsWith("192.168.10")) {
                    return ip;
                }
            }
            // 如果所有IP都是虚拟网卡的，那么使用第一个IP
            return allIps.get(0);
        }
        
        // 如果找不到任何适配器，回退到本地回环地址
        return "127.0.0.1";
    }
    
    /**
     * 测试端口是否可用
     * @param port 端口号
     */
    private static void testPort(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(getPreferredBindAddress(), port));
            serverSocket.close();
            System.out.println("端口 " + port + " 可用，可以绑定。");
        } catch (Exception e) {
            System.err.println("端口 " + port + " 不可用，可能已被占用：" + e.getMessage());
            e.printStackTrace();
        }
    }
} 