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
      <el-table-column prop="id" label="学号" width="100"></el-table-column>
      <el-table-column prop="name" label="姓名" width="120"></el-table-column>
      <el-table-column prop="gender" label="性别" width="80">
        <template slot-scope="scope">
          {{ scope.row.gender === 1 ? '男' : '女' }}
        </template>
      </el-table-column>
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
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">女</el-radio>
          </el-radio-group>
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
        name: '',
        gender: 1,
        classId: null
      },
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'change' }
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
        studentName: this.searchName
      }
      this.$axios.post('/api/student/list', { params }).then(res => {
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
        name: '',
        gender: 1,
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
        this.$axios.delete(`/api/student/${row.id}`).then(res => {
          if (res.data.code === 200) {
            this.$message.success('删除成功')
            this.getList()
          }
        })
      }).catch(() => {})
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const request = this.form.id
            ? this.$axios.put('/api/student', this.form)
            : this.$axios.post('/api/student', this.form)
          
          request.then(res => {
            if (res.data.code === 200) {
              this.$message.success(this.form.id ? '修改成功' : '添加成功')
              this.dialogVisible = false
              this.getList()
            }
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