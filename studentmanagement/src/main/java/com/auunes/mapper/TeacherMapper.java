package com.auunes.mapper;

import com.auunes.entity.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教师Mapper接口
 */
public interface TeacherMapper {
    /**
     * 查询教师列表
     * @param teacher 查询条件
     * @return 教师列表
     */
    List<Teacher> selectTeacherList(Teacher teacher);
    
    /**
     * 新增教师
     * @param teacher 教师信息
     * @return 影响行数
     */
    int insert(Teacher teacher);
    
    /**
     * 更新教师
     * @param teacher 教师信息
     * @return 影响行数
     */
    int update(Teacher teacher);
    
    /**
     * 删除教师
     * @param id 教师ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Integer id);
    
    /**
     * 根据ID查询教师
     * @param id 教师ID
     * @return 教师信息
     */
    Teacher selectById(@Param("id") Integer id);

    /**
     * 统计教师总数
     * @return 教师总数
     */
    int count();
} 