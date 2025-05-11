package com.auunes.service;

import com.auunes.entity.ClassInfo;

import java.util.List;

/**
 * 班级服务接口
 */
public interface ClassInfoService {
    /**
     * 查询班级列表
     * @param classInfo 查询条件
     * @return 班级列表
     */
    List<ClassInfo> list(ClassInfo classInfo);
    
    /**
     * 新增班级
     * @param classInfo 班级信息
     * @return 班级ID
     */
    int add(ClassInfo classInfo);
    
    /**
     * 更新班级
     * @param classInfo 班级信息
     * @return 是否成功
     */
    boolean update(ClassInfo classInfo);
    
    /**
     * 删除班级
     * @param id 班级ID
     * @return 是否成功
     */
    boolean delete(Integer id);
    
    /**
     * 根据ID查询班级
     * @param id 班级ID
     * @return 班级信息
     */
    ClassInfo getById(Integer id);
    
    /**
     * 查询所有班级
     * @return 班级列表
     */
    List<ClassInfo> listAll();
    
    /**
     * 获取班级详细信息（包含学生列表）
     * @param id 班级ID
     * @return 班级信息
     */
    ClassInfo getDetail(Integer id);

    /**
     * 获取班级总数
     * @return 班级总数
     */
    int getCount();
} 