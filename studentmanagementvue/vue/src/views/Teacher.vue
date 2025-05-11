<template>
  <div class="teacher-container">
    <div class="operation-bar">
      <el-button type="primary" @click="handleAdd">添加教师</el-button>
      <el-input
        v-model="searchName"
        placeholder="请输入教师姓名"
        style="width: 200px; margin-left: 10px"
        @keyup.enter.native="handleSearch"
      >
        <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
      </el-input>
    </div>

    <el-table :data="tableData" border style="width: 100%; margin-top: 20px">
      <el-table-column prop="id" label="教师ID" width="100"></el-table-column>
      <el-table-column prop="name" label="姓名" width="120"></el-table-column>
      <el-table-column prop="gender" label="性别" width="80">
        <template slot-scope="scope">
          {{ scope.row.gender === 1 ? '男' : '女' }}
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
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
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email"></el-input>
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
  name: 'Teacher',
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
        phone: '',
        email: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ],
        phone: [
          { required: true, message: '请输入电话', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      const params = {
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        className: this.searchName
      }
      this.$axios.post('/api/teacher/list', { params }).then(res => {
        if (res.error === 0) {
          this.tableData = res.body.list
          this.total = res.body.total
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
      this.dialogTitle = '添加教师'
      this.form = {
        id: null,
        name: '',
        gender: 1,
        phone: '',
        email: ''
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑教师'
      this.form = { ...row }
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该教师?', '提示', {
        type: 'warning'
      }).then(() => {
        this.$axios.delete(`/api/teacher/${row.id}`).then(res => {
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
            ? this.$axios.put('/api/teacher', this.form)
            : this.$axios.post('/api/teacher', this.form)
          
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
.teacher-container {
  padding: 20px;
}

.operation-bar {
  margin-bottom: 20px;
}
</style> 