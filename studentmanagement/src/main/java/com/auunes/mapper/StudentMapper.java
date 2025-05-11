package com.auunes.mapper;

import com.auunes.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生Mapper接口
 */
public interface StudentMapper {
    /**
     * 查询学生列表
     * @param student 查询条件
     * @return 学生列表
     */
    List<Student> selectStudentList(Student student);
    
    /**
     * 新增学生
     * @param student 学生信息
     * @return 影响行数
     */
    int insert(Student student);
    
    /**
     * 更新学生
     * @param student 学生信息
     * @return 影响行数
     */
    int update(Student student);
    
    /**
     * 删除学生
     * @param id 学生ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Integer id);
    
    /**
     * 根据ID查询学生
     * @param id 学生ID
     * @return 学生信息
     */
    Student selectById(@Param("id") Integer id);
    
    /**
     * 根据班级ID查询学生列表
     * @param classId 班级ID
     * @return 学生列表
     */
    List<Student> selectByClassId(@Param("classId") Integer classId);
    
    /**
     * 统计班级学生人数
     * @param classId 班级ID
     * @return 学生人数
     */
    int countByClassId(@Param("classId") Integer classId);

    /**
     * 统计学生总数
     * @return 学生总数
     */
    int count();
} 