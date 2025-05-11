package com.auunes.service;

import com.auunes.entity.Teacher;

import java.util.List;

/**
 * 教师服务接口
 */
public interface TeacherService {
    /**
     * 查询教师列表
     * @param teacher 查询条件
     * @return 教师列表
     */
    List<Teacher> list(Teacher teacher);
    
    /**
     * 新增教师
     * @param teacher 教师信息
     * @return 教师ID
     */
    int add(Teacher teacher);
    
    /**
     * 更新教师
     * @param teacher 教师信息
     * @return 是否成功
     */
    boolean update(Teacher teacher);
    
    /**
     * 删除教师
     * @param id 教师ID
     * @return 是否成功
     */
    boolean delete(Integer id);
    
    /**
     * 根据ID查询教师
     * @param id 教师ID
     * @return 教师信息
     */
    Teacher getById(Integer id);

    /**
     * 获取教师总数
     */
    int getCount();
} 