<template>
  <!-- 登录容器：唯一容器，撑满父元素 -->
  <div class="login-container">
    <!-- 透明图层：居中放置 -->
    <div class="transparent-overlay">
      <el-text class="title" size="large">底盘测试系统</el-text>
      <el-form :model="form" label-width="50px">
        <el-form-item label="账号">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" />
        </el-form-item>
        <el-form-item>
          <el-button type="login" >登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script >
import { ElButton, ElInput } from 'element-plus'
export default {
  name: 'LoginView',
  data() {
    return {
      form: {
        name: 'admin',
        password: '123456'
      }

    }
  },
  methods: {

  }
}



</script>

<style scoped>
/* 登录容器：100%继承父元素尺寸，背景铺满 */
.login-container {
  background-image: url('../assets/images/login.jpg');
  /* 核心：背景图拉伸铺满容器，不变形 */
  background-size: cover;
  /* 背景图居中显示，避免偏移 */
  background-position: center center;
  /* 背景图固定，滚动时不跟随 */
  background-attachment: fixed;
  /* 强制撑满父元素（#app） */
  width: 100%;
  height: 100%;
  /* 文字居中（可选） */
  display: flex;
  justify-content: center;
  align-items: center;
  color: black;
  font-size: 30px;
}

/* 透明图层样式 */
.transparent-overlay {
  background-color: rgba(255, 255, 255, 0.5);
  padding: 200px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
}
.title {
  text-align: center;
  font-size: 50px;
  color: black;
  display: flex;
  margin-bottom: 50px;
  margin-top: -100px;
}
.el-button{
  width: 100%;
  display: flex;
  margin-bottom: -100px;
}



</style>
