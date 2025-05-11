package com.auunes.mapper;

import com.auunes.entity.ClassInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 班级Mapper接口
 */
public interface ClassInfoMapper {
    /**
     * 查询班级列表
     * @param classInfo 查询条件
     * @return 班级列表
     */
    List<ClassInfo> selectClassList(ClassInfo classInfo);
    
    /**
     * 新增班级
     * @param classInfo 班级信息
     * @return 影响行数
     */
    int insert(ClassInfo classInfo);
    
    /**
     * 更新班级
     * @param classInfo 班级信息
     * @return 影响行数
     */
    int update(ClassInfo classInfo);
    
    /**
     * 删除班级
     * @param id 班级ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Integer id);
    
    /**
     * 根据ID查询班级
     * @param id 班级ID
     * @return 班级信息
     */
    ClassInfo selectById(@Param("id") Integer id);
    
    /**
     * 查询所有班级
     * @return 班级列表
     */
    List<ClassInfo> selectAll();

    /**
     * 统计班级总数
     * @return 班级总数
     */
    int count();
} 