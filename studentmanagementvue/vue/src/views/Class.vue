<template>
  <div class="class-container">
    <div class="operation-bar">
      <el-button type="primary" @click="handleAdd">添加班级</el-button>
      <el-input
        v-model="searchName"
        placeholder="请输入班级名称"
        style="width: 200px; margin-left: 10px"
        @keyup.enter.native="handleSearch"
      >
        <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
      </el-input>
    </div>

    <el-table :data="tableData" border style="width: 100%; margin-top: 20px">
      <el-table-column prop="id" label="班级ID" width="100"></el-table-column>
      <el-table-column prop="className" label="班级名称" width="180"></el-table-column>
      <el-table-column prop="grade" label="年级" width="100"></el-table-column>
      <el-table-column prop="major" label="专业"></el-table-column>
      <el-table-column prop="teacherName" label="班主任" width="120"></el-table-column>
      <el-table-column prop="studentCount" label="学生人数" width="100"></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
      <el-table-column label="操作" width="200">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[10, 20, 30, 50]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      style="margin-top: 20px; text-align: right"
    >
    </el-pagination>

    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="500px">
      <el-form :model="form" :rules="rules" ref="form" label-width="80px">
        <el-form-item label="班级名称" prop="className">
          <el-input v-model="form.className"></el-input>
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <el-input v-model="form.grade"></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="form.major"></el-input>
        </el-form-item>
        <el-form-item label="班主任" prop="teacherId">
          <el-select v-model="form.teacherId" placeholder="请选择班主任">
            <el-option
              v-for="item in teacherOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Class',
  data() {
    return {
      searchName: '',
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      dialogTitle: '',
      form: {
        id: null,
        className: '',
        grade: '',
        major: '',
        teacherId: null
      },
      rules: {
        className: [
          { required: true, message: '请输入班级名称', trigger: 'blur' }
        ],
        grade: [
          { required: true, message: '请输入年级', trigger: 'blur' }
        ],
        major: [
          { required: true, message: '请输入专业', trigger: 'blur' }
        ],
        teacherId: [
          { required: true, message: '请选择班主任', trigger: 'change' }
        ]
      },
      teacherOptions: []
    }
  },
  created() {
    this.getList()
    this.getTeacherOptions()
  },
  methods: {
    getList() {
      const params = {
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        className: this.searchName
      }
      this.$axios.post('/api/class/list', params).then(res => {
        if (res.error === 0) {
          const classList = res.body.list;
          this.$axios.get('/api/teacher/list/all').then(teacherRes => {
            if (teacherRes.error === 0) {
              const teacherMap = {};
              teacherRes.body.forEach(teacher => {
                teacherMap[teacher.id] = teacher.name;
              });
              this.$axios.post('/api/student/list', { pageSize: 9999 }).then(studentRes => {
                if (studentRes.error === 0) {
                  const studentList = studentRes.body.list;
                  const studentCountMap = {};
                  studentList.forEach(student => {
                    if (student.classId) {
                      studentCountMap[student.classId] = (studentCountMap[student.classId] || 0) + 1;
                    }
                  });
                  classList.forEach(item => {
                    item.teacherName = teacherMap[item.teacherId] || '未分配';
                    item.studentCount = studentCountMap[item.id] || 0;
                  });
                  this.tableData = classList;
                  this.total = res.body.total;
                }
              });
            }
          });
        }
      })
    },
    getTeacherOptions() {
      this.$axios.get('/api/teacher/list/all').then(res => {
        if (res.error === 0) {
          this.teacherOptions = res.body
        }
      })
    },
    handleSearch() {
      this.currentPage = 1
      this.getList()
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.getList()
    },
    handleAdd() {
      this.dialogTitle = '添加班级'
      this.form = {
        id: null,
        className: '',
        grade: '',
        major: '',
        teacherId: null
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑班级'
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该班级?', '提示', {
        type: 'warning'
      }).then(() => {
        this.$axios.post('/api/class/delete', { id: row.id }).then(res => {
          if (res.error === 0) {
            this.$message.success('删除成功');
            this.getList();
          } else {
            this.$message.error(res.message || '删除失败');
          }
        }).catch(error => {
          this.$message.error('删除失败');
        });
      }).catch(() => {
        // 取消删除
      });
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const submitData = {
            id: this.form.id,
            className: this.form.className,
            grade: this.form.grade,
            major: this.form.major,
            teacherId: this.form.teacherId
          };
          
          const request = this.form.id
            ? this.$axios.post('/api/class/update', submitData)
            : this.$axios.post('/api/class/add', submitData);
          
          request.then(res => {
            if (res.error === 0) {
              this.$message.success(this.form.id ? '修改成功' : '添加成功');
              this.dialogVisible = false;
              this.getList();
            } else {
              this.$message.error(res.message || (this.form.id ? '修改失败' : '添加失败'));
            }
          }).catch(error => {
            this.$message.error(this.form.id ? '修改失败' : '添加失败');
          });
        }
      });
    }
  }
}
</script>

<style scoped>
.class-container {
  padding: 20px;
}

.operation-bar {
  margin-bottom: 20px;
}
</style> 