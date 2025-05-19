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
      <el-table-column prop="teacherId" label="教师工号" width="120"></el-table-column>
      <el-table-column prop="name" label="姓名" width="120"></el-table-column>
      <el-table-column prop="gender" label="性别" width="80">
        <template slot-scope="scope">
          {{ scope.row.gender }}
        </template>
      </el-table-column>
      <el-table-column prop="department" label="所属院系" width="150"></el-table-column>
      <el-table-column prop="phone" label="手机号" width="120"></el-table-column>
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
        <el-form-item label="工号" prop="teacherId">
          <el-input v-model="form.teacherId" placeholder="请输入教师工号"></el-input>
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
        <el-form-item label="院系" prop="department">
          <el-input v-model="form.department" placeholder="请输入所属院系"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号"></el-input>
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
        teacherId: '',
        name: '',
        gender: '男',
        department: '',
        phone: '',
        email: ''
      },
      rules: {
        teacherId: [
          { required: true, message: '请输入教师工号', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ],
        department: [
          { required: true, message: '请输入所属院系', trigger: 'blur' }
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
        teacherName: this.searchName
      }
      this.$axios.post('/api/teacher/list', params).then(res => {
        if (res.error === 0) {
          this.tableData = res.body.list
          this.total = res.body.total
        } else {
          this.$message.error(res.message || '获取列表失败')
        }
      }).catch(error => {
        this.$message.error('获取列表失败')
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
        teacherId: '',
        name: '',
        gender: '男',
        department: '',
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
        this.$axios.post('/api/teacher/delete', { id: row.id }).then(res => {
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
          const request = this.form.id
            ? this.$axios.post('/api/teacher/update', this.form)
            : this.$axios.post('/api/teacher/add', this.form)
          
          request.then(res => {
            if (res.error === 0) {
              this.$message.success(res.message || (this.form.id ? '修改成功' : '添加成功'))
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
.teacher-container {
  padding: 20px;
}

.operation-bar {
  margin-bottom: 20px;
}
</style> 