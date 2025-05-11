package com.auunes.service.impl;

import com.auunes.entity.Student;
import com.auunes.exception.BusinessException;
import com.auunes.mapper.StudentMapper;
import com.auunes.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生服务实现类
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Student> list(Student student) {
        return studentMapper.selectStudentList(student);
    }

    @Override
    public int add(Student student) {
        // 校验学生信息
        validateStudent(student);
        
        // 新增学生
        int rows = studentMapper.insert(student);
        if (rows <= 0) {
            throw new BusinessException("添加学生失败");
        }
        
        return student.getId();
    }

    @Override
    public boolean update(Student student) {
        // 校验学生信息
        validateStudent(student);
        
        // 更新学生
        int rows = studentMapper.update(student);
        return rows > 0;
    }

    @Override
    public boolean delete(Integer id) {
        // 删除学生
        int rows = studentMapper.deleteById(id);
        return rows > 0;
    }

    @Override
    public Student getById(Integer id) {
        return studentMapper.selectById(id);
    }

    @Override
    public List<Student> getByClassId(Integer classId) {
        return studentMapper.selectByClassId(classId);
    }

    @Override
    public int getCount() {
        return studentMapper.count();
    }
    
    /**
     * 校验学生信息
     * @param student 学生信息
     */
    private void validateStudent(Student student) {
        if (student == null) {
            throw new BusinessException("学生信息不能为空");
        }
        
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new BusinessException("学生姓名不能为空");
        }
        
        if (student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
            throw new BusinessException("学生学号不能为空");
        }
        
        if (student.getClassId() == null) {
            throw new BusinessException("请选择班级");
        }
    }
} 