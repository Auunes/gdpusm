package com.auunes.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 网络工具类，用于检测网络适配器
 */
public class NetworkUtil {
    
    /**
     * 检查虚拟机IP是否可达
     * @param vmIp 虚拟机IP地址
     * @return 是否可达
     */
    public static boolean isVmReachable(String vmIp) {
        try {
            InetAddress address = InetAddress.getByName(vmIp);
            return address.isReachable(3000); // 3秒超时
        } catch (Exception e) {
            System.err.println("检查虚拟机连通性失败: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 获取所有有效网络适配器的IP地址
     * @return 所有网络适配器IP地址列表
     */
    public static List<String> getAllAdapterIps() {
        List<String> adapterIps = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            while (nets.hasMoreElements()) {
                NetworkInterface netInterface = nets.nextElement();
                String name = netInterface.getDisplayName();
                
                // 跳过未启用或环回接口
                if (!netInterface.isUp() || netInterface.isLoopback()) {
                    continue;
                }
                
                // 打印所有网络接口信息，帮助调试
                System.out.println("发现网络适配器: " + name + 
                                  " (虚拟: " + netInterface.isVirtual() + 
                                  ", 点对点: " + netInterface.isPointToPoint() + ")");
                
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (!addr.isLoopbackAddress() && addr.getHostAddress().indexOf(':') == -1) {
                        // 只添加IPv4地址，不添加IPv6地址
                        String ip = addr.getHostAddress();
                        adapterIps.add(ip);
                        System.out.println("  - IP地址: " + ip);
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("获取网络适配器信息失败: " + e.getMessage());
        }
        
        return adapterIps;
    }
    
    /**
     * 获取物理网卡的IP地址（含以太网适配器）
     * @return 物理网卡IP地址列表
     */
    public static List<String> getPhysicalAdapterIps() {
        List<String> adapterIps = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            while (nets.hasMoreElements()) {
                NetworkInterface netInterface = nets.nextElement();
                String name = netInterface.getDisplayName().toLowerCase();
                
                // 跳过未启用或环回接口
                if (!netInterface.isUp() || netInterface.isLoopback()) {
                    continue;
                }
                
                // 过滤掉明确是虚拟的适配器
                if (name.contains("vmware") || name.contains("virtual") || 
                    name.contains("vpn") || name.contains("pseudo") || 
                    name.contains("docker") || name.contains("vethernet") ||
                    netInterface.isVirtual()) {
                    continue;
                }
                
                System.out.println("找到物理网卡: " + netInterface.getDisplayName());
                
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (!addr.isLoopbackAddress() && addr.getHostAddress().indexOf(':') == -1) {
                        // 只添加IPv4地址，不添加IPv6地址
                        String ip = addr.getHostAddress();
                        adapterIps.add(ip);
                        System.out.println("  - IP地址: " + ip);
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("获取网络适配器信息失败: " + e.getMessage());
        }
        
        return adapterIps;
    }
    
    /**
     * 测试与虚拟机的连接
     * @param vmIp 虚拟机IP
     * @return 可以连接的本机IP，如果找不到返回null
     */
    public static String testVmConnectivity(String vmIp) {
        // 首先检查虚拟机是否可达
        if (!isVmReachable(vmIp)) {
            System.err.println("警告: 虚拟机 " + vmIp + " 不可达，请检查网络连接");
            return null;
        }
        
        // 先尝试从虚拟机网段的IP连接（192.168.81.x）
        List<String> allIps = getAllAdapterIps();
        for (String ip : allIps) {
            // 如果找到了同一网段的IP，优先使用
            if (ip.startsWith(vmIp.substring(0, vmIp.lastIndexOf('.')))) {
                System.out.println("找到与虚拟机同一网段的IP地址: " + ip);
                return ip;
            }
        }
        
        // 如果找不到同网段的，尝试获取物理网卡IP
        List<String> physicalIps = getPhysicalAdapterIps();
        if (!physicalIps.isEmpty()) {
            System.out.println("找到物理网卡IP: " + physicalIps);
            return physicalIps.get(0);
        }
        
        // 最后尝试所有适配器
        if (!allIps.isEmpty()) {
            System.out.println("未找到物理网卡，使用所有可用网卡: " + allIps);
            return allIps.get(0);
        }
        
        System.err.println("警告: 未找到任何可用网络适配器");
        return null;
    }
} 