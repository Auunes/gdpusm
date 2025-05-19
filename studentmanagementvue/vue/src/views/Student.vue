<template>
  <div class="student-container">
    <div class="operation-bar">
      <el-button type="primary" @click="handleAdd">添加学生</el-button>
      <el-input
        v-model="searchName"
        placeholder="请输入学生姓名"
        style="width: 200px; margin-left: 10px"
        @keyup.enter.native="handleSearch"
      >
        <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
      </el-input>
    </div>

    <el-table :data="tableData" border style="width: 100%; margin-top: 20px">
      <el-table-column prop="id" label="学生ID" width="100"></el-table-column>
      <el-table-column prop="studentId" label="学号" width="120"></el-table-column>
      <el-table-column prop="name" label="姓名" width="120"></el-table-column>
      <el-table-column prop="gender" label="性别" width="80">
        <template slot-scope="scope">
          {{ scope.row.gender }}
        </template>
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="80"></el-table-column>
      <el-table-column prop="phone" label="电话" width="120"></el-table-column>
      <el-table-column prop="className" label="班级">
        <template slot-scope="scope">
          {{ scope.row.className || '未分配班级' }}
        </template>
      </el-table-column>
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
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="form.studentId"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input-number v-model="form.age" :min="1" :max="100" :precision="0"></el-input-number>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号码"></el-input>
        </el-form-item>
        <el-form-item label="班级" prop="classId">
          <el-select v-model="form.classId" placeholder="请选择班级">
            <el-option
              v-for="item in classOptions"
              :key="item.id"
              :label="item.className"
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
  name: 'Student',
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
        studentId: '',
        name: '',
        gender: '男',
        age: null,
        phone: '',
        classId: null
      },
      rules: {
        studentId: [
          { required: true, message: '请输入学号', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ],
        age: [
          { type: 'number', message: '年龄必须为数字', trigger: 'blur' }
        ],
        phone: [
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
        ],
        classId: [
          { required: true, message: '请选择班级', trigger: 'change' }
        ]
      },
      classOptions: []
    }
  },
  created() {
    this.getList()
    this.getClassOptions()
  },
  methods: {
    getList() {
      const params = {
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        name: this.searchName
      }
      this.$axios.post('/api/student/list', params).then(res => {
        if (res.error === 0) {
          this.tableData = res.body.list
          this.total = res.body.total
        }
      })
    },
    getClassOptions() {
      this.$axios.get('/api/class/list/all').then(res => {
        if (res.error === 0) {
          this.classOptions = res.body
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
      this.dialogTitle = '添加学生'
      this.form = {
        id: null,
        studentId: '',
        name: '',
        gender: '男',
        age: null,
        phone: '',
        classId: null
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑学生'
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该学生?', '提示', {
        type: 'warning'
      }).then(() => {
        this.$axios.post('/api/student/delete', { id: row.id }).then(res => {
          if (res.error === 0) {
            this.$message.success('删除成功')
            this.getList()
          } else {
            this.$message.error(res.message || '删除失败')
          }
        }).catch(error => {
          this.$message.error('删除失败')
        })
      }).catch(() => {})
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const submitData = {
            id: this.form.id,
            studentId: this.form.studentId,
            name: this.form.name,
            gender: this.form.gender,
            age: this.form.age,
            phone: this.form.phone,
            classId: this.form.classId
          }
          
          const request = this.form.id
            ? this.$axios.post('/api/student/update', submitData)
            : this.$axios.post('/api/student/add', submitData)
          
          request.then(res => {
            if (res.error === 0) {
              this.$message.success(this.form.id ? '修改成功' : '添加成功')
              this.dialogVisible = false
              this.getList()
            } else {
              this.$message.error(res.message || (this.form.id ? '修改失败' : '添加失败'))
            }
          }).catch(error => {
            this.$message.error(this.form.id ? '修改失败' : '添加失败')
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.student-container {
  padding: 20px;
}

.operation-bar {
  margin-bottom: 20px;
}
</style> 