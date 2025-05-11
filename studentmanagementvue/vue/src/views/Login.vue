<template>
  <div class="login-container">
    <el-card class="login-card">
      <div class="title">学生管理系统</div>
      <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="0px">
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" prefix-icon="el-icon-user" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" prefix-icon="el-icon-lock" type="password" placeholder="密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" style="width: 100%">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          console.log(valid)
          this.$axios.post('/api/auth/login', this.loginForm)
            .then(res => {
              console.log(res)
              if (res.error === 0) {
                console.log(res)
                localStorage.setItem('token', res.body.token)
                this.$store.dispatch('login', res.body.token)
                this.$message.success('登录成功')
                this.$router.push('/home')
              } else {
                this.$message.error(res.data.message)
              }
            })
            .catch(err => {
              console.error('登录错误:', err)
              this.$message.error('登录失败')
            })
        }
      })
    }
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f3f3f3;
}

.login-card {
  width: 400px;
}

.title {
  text-align: center;
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 30px;
  color: #409EFF;
}
</style> 