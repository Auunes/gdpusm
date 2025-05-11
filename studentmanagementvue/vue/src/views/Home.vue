<template>
  <div class="home-container">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card shadow="hover">
          <div class="card-item">
            <div class="card-icon student">
              <i class="el-icon-user"></i>
            </div>
            <div class="card-info">
              <div class="card-title">学生总数</div>
              <div class="card-number">{{ studentCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <div class="card-item">
            <div class="card-icon class">
              <i class="el-icon-office-building"></i>
            </div>
            <div class="card-info">
              <div class="card-title">班级总数</div>
              <div class="card-number">{{ classCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <div class="card-item">
            <div class="card-icon teacher">
              <i class="el-icon-s-custom"></i>
            </div>
            <div class="card-info">
              <div class="card-title">教师总数</div>
              <div class="card-number">{{ teacherCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      studentCount: 0,
      classCount: 0,
      teacherCount: 0
    }
  },
  created() {
    this.getCounts()
  },
  methods: {
  getCounts() {
    const requests = [
      this.$axios.get('/api/student/count'),
      this.$axios.get('/api/class/count'),
      this.$axios.get('/api/teacher/count')
    ]
    Promise.all(requests)
      .then(([studentRes, classRes, teacherRes]) => {
        if (studentRes.error === 0) this.studentCount = studentRes.body
        if (classRes.error === 0) this.classCount = classRes.body
        if (teacherRes.error === 0) this.teacherCount = teacherRes.body
      })
      .catch(err => {
        if (err.response && err.response.status === 401) {
          this.$message.error('登录已过期，请重新登录')
          this.$router.push('/login')
        }
      })
  }
}
}
</script>

<style scoped>
.home-container {
  padding: 20px;
}

.card-item {
  display: flex;
  align-items: center;
}

.card-icon {
  width: 80px;
  height: 80px;
  border-radius: 8px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 20px;
}

.card-icon i {
  font-size: 40px;
  color: #fff;
}

.student {
  background-color: #409EFF;
}

.class {
  background-color: #67C23A;
}

.teacher {
  background-color: #E6A23C;
}

.card-info {
  flex: 1;
}

.card-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 10px;
}

.card-number {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}
</style> 