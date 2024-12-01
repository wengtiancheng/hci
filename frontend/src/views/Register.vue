<template>
  <div class="register-form">
    <form @submit.prevent="handleSubmit">
      <h2>注册新账号</h2>
      <div>
        <label for="phone">手机号:</label>
        <input id="phone" v-model="phoneNumber" type="text" placeholder="手机号" required />
      </div>
      <div>
        <label for="username">用户名:</label>
        <input id="username" v-model="username" type="text" placeholder="用户名" required />
      </div>
      <div>
        <label for="password">密码:</label>
        <input id="password" v-model="password" type="password" placeholder="密码" required />
      </div>
      <div>
        <label for="confirmPassword">确认密码:</label>
        <input id="confirmPassword" v-model="confirmPassword" type="password" placeholder="确认密码" required />
      </div>
      <button type="submit">注册</button>
      <div class="login-link">
        <span>已有帐号？</span>
      </div>
      <div class="login-button">
        <button  @click="goToLogin">登录</button>
      </div>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import { register } from '../api/User.ts';
import router from '../router/index';

const phoneNumber = ref('');
const username = ref('');
const password = ref('');
const confirmPassword = ref('');

const handleSubmit = () => {
  if (phoneNumber.value && username.value && password.value) {
    // 处理表单提交逻辑
    console.log('Phone Number:', phoneNumber.value);
    console.log('Username:', username.value);
    console.log('Password:', password.value);
  } else {
    ElMessage.error({
      message: '请填写完整信息',
      type: 'error',
      center: true,
      offset: 50, // 设置消息显示在屏幕上方中间
    });
  }
  if (password.value !== confirmPassword.value) {
    ElMessage.error({
      message: '两次输入的密码不一致',
      type: 'error',
      center: true,
      offset: 50, // 设置消息显示在屏幕上方中间
    });
  }

  register({
    name: username.value as string,
    phone: phoneNumber.value as string,
    password: password.value as string
  }).then(res => {
    if (res.data.code === '000') {
      ElMessage.success({
        message: '注册成功',
        type: 'success',
        center: true,
        offset: 50, // 设置消息显示在屏幕上方中间
      });
      router.push('/login');
    } else {
      ElMessage.error({
        message: res.data.msg,
        type: 'error',
        center: true,
        offset: 50, // 设置消息显示在屏幕上方中间
      });
    }
  });
};

const goToLogin = () => {
  router.push('/login');
};
</script>

<style scoped>
.register-form {
  width: 400px;
  height: auto;
  margin: 0 auto;
  padding: 1em;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.register-form input::placeholder {
  font-family: 'Arial Rounded MT Bold', 'Helvetica Rounded', Arial, sans-serif;
}

.register-form h2 {
  margin-bottom: 1em;
  text-align: left;
}

.register-form div {
  margin-bottom: 1em;
}

.register-form label {
  display: block;
  margin-bottom: 0.5em;
}

.register-form input {
  width: 100%;
  height: 45px;
  margin-top: 0px;
  padding: 0.5em;
  box-sizing: border-box;
}

.register-form label {
  display: block;
  text-align: left;
  margin-bottom: 0px;
  color: gray;
  font-size: 16px;
}

.register-form button {
  width: 100%;
  padding: 0.7em;
  background-color: #5e00ff;
  margin-top: 20px;
  margin-bottom: 20px;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.register-form button:hover {
  background-color: #0056b3;
}

.login-link {
  display: flex;
  align-items: center;
  margin-top: 1em;
}

.login-link span {
  color: gray;
}

.login-button {
  display: flex;
  justify-content: center;
  margin-top: 0.5em;
}

.login-button button {
  width: 100%;
  padding: 0.7em;
  background-color: #5e00ff;
  margin-top: 20px;
  margin-bottom: 20px;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.login-button button:hover {
  background-color: #0056b3;
}
</style>