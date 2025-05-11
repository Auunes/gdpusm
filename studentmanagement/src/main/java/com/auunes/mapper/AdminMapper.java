package com.auunes.mapper;

import com.auunes.entity.Admin;
import org.apache.ibatis.annotations.Param;

/**
 * 管理员Mapper接口
 */
public interface AdminMapper {
    /**
     * 根据用户名查询管理员
     * @param username 用户名
     * @return 管理员信息
     */
    Admin selectByUsername(@Param("username") String username);
    
    /**
     * 新增管理员
     * @param admin 管理员信息
     * @return 影响行数
     */
    int insert(Admin admin);
    
    /**
     * 更新管理员
     * @param admin 管理员信息
     * @return 影响行数
     */
    int update(Admin admin);
    
    /**
     * 删除管理员
     * @param id 管理员ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Integer id);
    
    /**
     * 根据ID查询管理员
     * @param id 管理员ID
     * @return 管理员信息
     */
    Admin selectById(@Param("id") Integer id);
} 