package com.auunes.controller;

import com.auunes.common.R;
import com.auunes.entity.ClassInfo;
import com.auunes.entity.Student;
import com.auunes.service.ClassInfoService;
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
 * 班级管理控制器
 */
@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClassInfoService classInfoService;
    
    @Autowired
    private StudentService studentService;

    /**
     * 获取班级列表
     * @param params 查询参数
     * @return 班级列表
     */
    @PostMapping("/list")
    public R<Map<String, Object>> list(@RequestBody Map<String, Object> params) {
        Integer pageNum = (Integer) params.getOrDefault("pageNum", 1);
        Integer pageSize = (Integer) params.getOrDefault("pageSize", 10);
        
        // 构建查询条件
        ClassInfo classInfo = new ClassInfo();
        if (params.containsKey("className")) {
            classInfo.setClassName((String) params.get("className"));
        }
        
        // 分页查询
        PageHelper.startPage(pageNum, pageSize);
        List<ClassInfo> list = classInfoService.list(classInfo);
        PageInfo<ClassInfo> pageInfo = new PageInfo<>(list);
        
        // 封装结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        
        return R.success(result);
    }
    
    /**
     * 添加班级
     * @param classInfo 班级信息
     * @return 添加结果
     */
    @PostMapping("/add")
    public R<Map<String, Object>> add(@RequestBody ClassInfo classInfo) {
        int id = classInfoService.add(classInfo);
        
        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        
        return R.success(result, "添加成功");
    }
    
    /**
     * 更新班级
     * @param classInfo 班级信息
     * @return 更新结果
     */
    @PostMapping("/update")
    public R<Object> update(@RequestBody ClassInfo classInfo) {
        boolean success = classInfoService.update(classInfo);
        
        if (success) {
            return R.success(null, "更新成功");
        } else {
            return R.businessError("更新失败");
        }
    }
    
    /**
     * 删除班级
     * @param params 参数（包含id）
     * @return 删除结果
     */
    @PostMapping("/delete")
    public R<Object> delete(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        if (id == null) {
            return R.businessError("ID不能为空");
        }
        
        boolean success = classInfoService.delete(id);
        
        if (success) {
            return R.success(null, "删除成功");
        } else {
            return R.businessError("删除失败");
        }
    }
    
    /**
     * 获取班级详情
     * @param params 参数（包含id）
     * @return 班级详情
     */
    @PostMapping("/detail")
    public R<ClassInfo> detail(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        if (id == null) {
            return R.businessError("ID不能为空");
        }
        
        // 获取班级详细信息（包含学生列表）
        ClassInfo classInfo = classInfoService.getDetail(id);
        if (classInfo == null) {
            return R.businessError("班级不存在");
        }
        
        return R.success(classInfo);
    }

    /**
     * 获取班级总数
     * @return 班级总数
     */
    @GetMapping("/count")
    public R<Integer> getCount() {
        return R.success(classInfoService.getCount());
    }

    /**
     * 获取所有班级列表（不分页）
     * @return 班级列表
     */
    @GetMapping("/list/all")
    public R<List<ClassInfo>> listAll() {
        List<ClassInfo> list = classInfoService.list(new ClassInfo());
        return R.success(list);
    }
} 