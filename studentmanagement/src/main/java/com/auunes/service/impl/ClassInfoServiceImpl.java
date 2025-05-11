package com.auunes.service.impl;

import com.auunes.entity.ClassInfo;
import com.auunes.entity.Student;
import com.auunes.exception.BusinessException;
import com.auunes.mapper.ClassInfoMapper;
import com.auunes.mapper.StudentMapper;
import com.auunes.service.ClassInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 班级服务实现类
 */
@Service
public class ClassInfoServiceImpl implements ClassInfoService {
    @Autowired
    private ClassInfoMapper classInfoMapper;
    
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<ClassInfo> list(ClassInfo classInfo) {
        return classInfoMapper.selectClassList(classInfo);
    }

    @Override
    public int add(ClassInfo classInfo) {
        // 校验班级信息
        validateClassInfo(classInfo);
        
        // 新增班级
        int rows = classInfoMapper.insert(classInfo);
        if (rows <= 0) {
            throw new BusinessException("添加班级失败");
        }
        
        return classInfo.getId();
    }

    @Override
    public boolean update(ClassInfo classInfo) {
        // 校验班级信息
        validateClassInfo(classInfo);
        
        // 检查ID是否存在
        if (classInfo.getId() == null) {
            throw new BusinessException("班级ID不能为空");
        }
        
        // 查询班级是否存在
        ClassInfo existingClass = classInfoMapper.selectById(classInfo.getId());
        if (existingClass == null) {
            throw new BusinessException("班级不存在");
        }
        
        // 更新班级
        int rows = classInfoMapper.update(classInfo);
        return rows > 0;
    }

    @Override
    public boolean delete(Integer id) {
        if (id == null) {
            throw new BusinessException("班级ID不能为空");
        }
        
        // 检查班级是否存在学生
        int studentCount = studentMapper.countByClassId(id);
        if (studentCount > 0) {
            throw new BusinessException("班级下存在学生，无法删除");
        }
        
        // 删除班级
        int rows = classInfoMapper.deleteById(id);
        return rows > 0;
    }

    @Override
    public ClassInfo getById(Integer id) {
        if (id == null) {
            throw new BusinessException("班级ID不能为空");
        }
        
        return classInfoMapper.selectById(id);
    }
    
    @Override
    public List<ClassInfo> listAll() {
        return classInfoMapper.selectAll();
    }
    
    @Override
    public ClassInfo getDetail(Integer id) {
        // 获取班级基本信息
        ClassInfo classInfo = this.getById(id);
        if (classInfo == null) {
            return null;
        }
        
        // 获取班级学生列表
        List<Student> students = studentMapper.selectByClassId(id);
        classInfo.setStudents(students);
        classInfo.setStudentCount(students.size());
        
        return classInfo;
    }
    
    @Override
    public int getCount() {
        return classInfoMapper.count();
    }
    
    /**
     * 校验班级信息
     * @param classInfo 班级信息
     */
    private void validateClassInfo(ClassInfo classInfo) {
        if (classInfo == null) {
            throw new BusinessException("班级信息不能为空");
        }
        
        if (classInfo.getClassName() == null || classInfo.getClassName().trim().isEmpty()) {
            throw new BusinessException("班级名称不能为空");
        }
        
        if (classInfo.getGrade() == null || classInfo.getGrade().trim().isEmpty()) {
            throw new BusinessException("年级不能为空");
        }
        
        if (classInfo.getMajor() == null || classInfo.getMajor().trim().isEmpty()) {
            throw new BusinessException("专业不能为空");
        }

        if (classInfo.getTeacherId() == null) {
            throw new BusinessException("班主任不能为空");
        }
    }
} 