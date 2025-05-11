# 学生管理系统

## 项目简介
这是一个基于 Spring Boot + Vue.js 开发的学生管理系统，用于管理学校的学生、班级和教师信息。系统提供了完整的用户界面和后台管理功能，支持数据的增删改查等基本操作。

## 技术栈
### 后端
- Spring Boot 2.x
- MyBatis
- MySQL 8.0
- Maven

### 前端
- Vue.js
- Element UI
- Axios

## 功能特性
1. 用户认证
   - 管理员登录
   - Token 认证

2. 学生管理
   - 学生信息的增删改查
   - 学生列表分页显示
   - 按姓名、学号、班级搜索
   - 学生详细信息查看

3. 班级管理
   - 班级信息的增删改查
   - 班级列表分页显示
   - 按班级名称搜索
   - 显示班级学生人数
   - 班主任分配
   - 班级详细信息查看

4. 教师管理
   - 教师信息的增删改查
   - 教师列表分页显示
   - 按姓名、院系搜索
   - 教师详细信息查看

5. 数据统计
   - 学生总数统计
   - 班级总数统计
   - 教师总数统计

## 系统要求
- JDK 1.8+
- MySQL 8.0+
- Node.js 12+
- Maven 3.6+

## 快速开始

### 1. 数据库配置
1. 创建数据库
```sql
CREATE DATABASE gdpusm DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

2. 执行数据库脚本
```sql
source [数据库脚本路径]/gdpusm.sql
```

3. 修改数据库配置
编辑 `studentmanagement/src/main/resources/application.yml` 文件，配置数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/gdpusm?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
```

### 2. 后端启动
1. 进入后端项目目录
```bash
cd studentmanagement
```

2. 编译打包
```bash
mvn clean package
```

3. 运行项目
```bash
java -jar target/studentmanagement.jar
```

### 3. 前端启动
1. 进入前端项目目录
```bash
cd studentmanagementvue/vue
```

2. 安装依赖
```bash
npm install
```

3. 启动开发服务器
```bash
npm run serve
```

4. 构建生产环境
```bash
npm run build
```

## 默认账号
- 用户名：admin
- 密码：123456

## 项目结构
```
studentmanagement/          # 后端项目
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/auunes/
│   │   │       ├── controller/    # 控制器
│   │   │       ├── service/       # 服务层
│   │   │       ├── mapper/        # 数据访问层
│   │   │       ├── entity/        # 实体类
│   │   │       └── common/        # 公共组件
│   │   └── resources/
│   │       ├── mapper/            # MyBatis映射文件
│   │       └── application.yml    # 配置文件
│   └── test/                      # 测试代码
└── pom.xml                        # Maven配置

studentmanagementvue/      # 前端项目
└── vue/
    ├── src/
    │   ├── views/         # 页面组件
    │   ├── components/    # 公共组件
    │   ├── router/        # 路由配置
    │   ├── api/           # API接口
    │   └── assets/        # 静态资源
    ├── public/            # 公共文件
    └── package.json       # 项目配置
```

## API文档
详细的API接口文档请参考 [接口文档.md](接口文档.md)

## 数据库文档
数据库设计文档请参考 [数据库文档.md](数据库文档.md)
