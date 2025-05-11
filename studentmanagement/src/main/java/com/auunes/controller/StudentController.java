package com.auunes.controller;

import com.auunes.common.R;
import com.auunes.entity.Student;
import com.auunes.service.StudentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生管理控制器
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 获取学生列表
     * @param params 查询参数
     * @return 学生列表
     */
    @PostMapping("/list")
    public R<Map<String, Object>> list(@RequestBody Map<String, Object> params) {
        Integer pageNum = (Integer) params.getOrDefault("pageNum", 1);
        Integer pageSize = (Integer) params.getOrDefault("pageSize", 10);
        
        // 构建查询条件
        Student student = new Student();
        if (params.containsKey("studentId")) {
            student.setStudentId((String) params.get("studentId"));
        }
        if (params.containsKey("name")) {
            student.setName((String) params.get("name"));
        }
        if (params.containsKey("classId")) {
            student.setClassId((Integer) params.get("classId"));
        }
        
        // 分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Student> list = studentService.list(student);
        PageInfo<Student> pageInfo = new PageInfo<>(list);
        
        // 封装结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        
        return R.success(result);
    }
    
    /**
     * 添加学生
     * @param student 学生信息
     * @return 添加结果
     */
    @PostMapping("/add")
    public R<Map<String, Object>> add(@RequestBody Student student) {
        int id = studentService.add(student);
        
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        
        return R.success(result, "添加成功");
    }
    
    /**
     * 更新学生
     * @param student 学生信息
     * @return 更新结果
     */
    @PostMapping("/update")
    public R<Object> update(@RequestBody Student student) {
        boolean success = studentService.update(student);
        
        if (success) {
            return R.success(null, "更新成功");
        } else {
            return R.businessError("更新失败");
        }
    }
    
    /**
     * 删除学生
     * @param params 参数（包含id）
     * @return 删除结果
     */
    @PostMapping("/delete")
    public R<Object> delete(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        if (id == null) {
            return R.businessError("ID不能为空");
        }
        
        boolean success = studentService.delete(id);
        
        if (success) {
            return R.success(null, "删除成功");
        } else {
            return R.businessError("删除失败");
        }
    }
    
    /**
     * 获取学生详情
     * @param params 参数（包含id）
     * @return 学生详情
     */
    @PostMapping("/detail")
    public R<Student> detail(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        if (id == null) {
            return R.businessError("ID不能为空");
        }
        
        Student student = studentService.getById(id);
        
        if (student != null) {
            return R.success(student);
        } else {
            return R.businessError("学生不存在");
        }
    }

    /**
     * 获取学生总数
     * @return 学生总数
     */
    @GetMapping("/count")
    public R<Integer> getCount() {
        return R.success(studentService.getCount());
    }
} 