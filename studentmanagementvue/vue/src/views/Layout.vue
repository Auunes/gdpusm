<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <div class="logo">学生管理系统</div>
      <el-menu
        :default-active="$route.path"
        class="el-menu-vertical"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409EFF"
        router
      >
        <el-menu-item index="/home">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
        </el-menu-item>
        <el-menu-item index="/student">
          <i class="el-icon-user"></i>
          <span slot="title">学生管理</span>
        </el-menu-item>
        <el-menu-item index="/class">
          <i class="el-icon-office-building"></i>
          <span slot="title">班级管理</span>
        </el-menu-item>
        <el-menu-item index="/teacher">
          <i class="el-icon-s-custom"></i>
          <span slot="title">教师管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="el-dropdown-link">
              管理员<i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: 'Layout',
  methods: {
    handleCommand(command) {
      if (command === 'logout') {
        localStorage.removeItem('token')
        this.$router.push('/login')
        this.$message.success('退出成功')
      }
    }
  }
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-size: 20px;
  font-weight: bold;
  background-color: #2b2f3a;
}

.el-aside {
  background-color: #304156;
}

.el-menu {
  border-right: none;
}

.el-header {
  background-color: #fff;
  color: #333;
  line-height: 60px;
  border-bottom: 1px solid #e6e6e6;
}

.header-right {
  float: right;
  cursor: pointer;
}

.el-dropdown-link {
  color: #409EFF;
}

.el-main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style> 