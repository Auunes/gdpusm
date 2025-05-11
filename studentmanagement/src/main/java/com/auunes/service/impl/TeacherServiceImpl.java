package com.auunes.service.impl;

import com.auunes.entity.Teacher;
import com.auunes.exception.BusinessException;
import com.auunes.mapper.TeacherMapper;
import com.auunes.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 教师服务实现类
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public List<Teacher> list(Teacher teacher) {
        return teacherMapper.selectTeacherList(teacher);
    }

    @Override
    public int add(Teacher teacher) {
        // 校验教师信息
        validateTeacher(teacher);
        
        // 新增教师
        int rows = teacherMapper.insert(teacher);
        if (rows <= 0) {
            throw new BusinessException("添加教师失败");
        }
        
        return teacher.getId();
    }

    @Override
    public boolean update(Teacher teacher) {
        // 校验教师信息
        validateTeacher(teacher);
        
        // 检查ID是否存在
        if (teacher.getId() == null) {
            throw new BusinessException("教师ID不能为空");
        }
        
        // 查询教师是否存在
        Teacher existingTeacher = teacherMapper.selectById(teacher.getId());
        if (existingTeacher == null) {
            throw new BusinessException("教师不存在");
        }
        
        // 更新教师
        int rows = teacherMapper.update(teacher);
        return rows > 0;
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null) {
            throw new BusinessException("教师ID不能为空");
        }
        
        // 删除教师
        int rows = teacherMapper.deleteById(id);
        return rows > 0;
    }

    @Override
    public Teacher getById(Integer id) {
        if (id == null) {
            throw new BusinessException("教师ID不能为空");
        }
        
        return teacherMapper.selectById(id);
    }

    @Override
    public int getCount() {
        return teacherMapper.count();
    }
    
    /**
     * 校验教师信息
     * @param teacher 教师信息
     */
    private void validateTeacher(Teacher teacher) {
        if (teacher == null) {
            throw new BusinessException("教师信息不能为空");
        }
        
        if (teacher.getTeacherId() == null || teacher.getTeacherId().trim().isEmpty()) {
            throw new BusinessException("教师工号不能为空");
        }
        
        if (teacher.getName() == null || teacher.getName().trim().isEmpty()) {
            throw new BusinessException("教师姓名不能为空");
        }
        
        if (teacher.getGender() == null || teacher.getGender().trim().isEmpty()) {
            throw new BusinessException("性别不能为空");
        }
        
        if (teacher.getDepartment() == null || teacher.getDepartment().trim().isEmpty()) {
            throw new BusinessException("所属院系不能为空");
        }
    }
} 