package com.auunes.controller;

import com.auunes.common.R;
import com.auunes.entity.Teacher;
import com.auunes.service.TeacherService;
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
 * 教师管理控制器
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 获取教师列表
     * @param params 查询参数
     * @return 教师列表
     */
    @PostMapping("/list")
    public R<Map<String, Object>> list(@RequestBody Map<String, Object> params) {
        Integer pageNum = (Integer) params.getOrDefault("pageNum", 1);
        Integer pageSize = (Integer) params.getOrDefault("pageSize", 10);
        
        // 构建查询条件
        Teacher teacher = new Teacher();
        if (params.containsKey("teacherId")) {
            teacher.setTeacherId((String) params.get("teacherId"));
        }
        if (params.containsKey("teacherName")) {
            teacher.setName((String) params.get("teacherName"));
        }
        if (params.containsKey("department")) {
            teacher.setDepartment((String) params.get("department"));
        }
        
        // 分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<Teacher> list = teacherService.list(teacher);
        PageInfo<Teacher> pageInfo = new PageInfo<>(list);
        
        // 封装结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        
        return R.success(result);
    }
    
    /**
     * 添加教师
     * @param teacher 教师信息
     * @return 添加结果
     */
    @PostMapping("/add")
    public R<Map<String, Object>> add(@RequestBody Teacher teacher) {
        int id = teacherService.add(teacher);
        
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        
        return R.success(result, "添加成功");
    }
    
    /**
     * 更新教师
     * @param teacher 教师信息
     * @return 更新结果
     */
    @PostMapping("/update")
    public R<Object> update(@RequestBody Teacher teacher) {
        boolean success = teacherService.update(teacher);
        
        if (success) {
            return R.success(null, "更新成功");
        } else {
            return R.businessError("更新失败");
        }
    }
    
    /**
     * 删除教师
     * @param params 参数（包含id）
     * @return 删除结果
     */
    @PostMapping("/delete")
    public R<Object> delete(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        if (id == null) {
            return R.businessError("ID不能为空");
        }
        
        boolean success = teacherService.delete(id);
        
        if (success) {
            return R.success(null, "删除成功");
        } else {
            return R.businessError("删除失败");
        }
    }
    
    /**
     * 获取教师详情
     * @param params 参数（包含id）
     * @return 教师详情
     */
    @PostMapping("/detail")
    public R<Teacher> detail(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        if (id == null) {
            return R.businessError("ID不能为空");
        }
        
        Teacher teacher = teacherService.getById(id);
        
        if (teacher != null) {
            return R.success(teacher);
        } else {
            return R.businessError("教师不存在");
        }
    }

    /**
     * 获取教师总数
     * @return 教师总数
     */
    @GetMapping("/count")
    public R<Integer> getCount() {
        return R.success(teacherService.getCount());
    }

    /**
     * 获取所有教师列表（不分页）
     * @return 教师列表
     */
    @GetMapping("/list/all")
    public R<List<Teacher>> listAll() {
        List<Teacher> list = teacherService.list(new Teacher());
        return R.success(list);
    }
} 