package com.auunes.service;

import com.auunes.entity.Student;

import java.util.List;

/**
 * 学生服务接口
 */
public interface StudentService {
    /**
     * 查询学生列表
     * @param student 查询条件
     * @return 学生列表
     */
    List<Student> list(Student student);
    
    /**
     * 新增学生
     * @param student 学生信息
     * @return 学生ID
     */
    int add(Student student);
    
    /**
     * 更新学生
     * @param student 学生信息
     * @return 是否成功
     */
    boolean update(Student student);
    
    /**
     * 删除学生
     * @param id 学生ID
     * @return 是否成功
     */
    boolean delete(Integer id);
    
    /**
     * 根据ID查询学生
     * @param id 学生ID
     * @return 学生信息
     */
    Student getById(Integer id);
    
    /**
     * 根据班级ID查询学生列表
     * @param classId 班级ID
     * @return 学生列表
     */
    List<Student> getByClassId(Integer classId);

    /**
     * 获取学生总数
     */
    int getCount();
} 