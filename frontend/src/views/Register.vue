<template>
  <div class="register-container">
    <div class="register-form">
      <form @submit.prevent="handleSubmit">
        <h2>注册新账号</h2>
        <div>
          <label for="phone">手机号:</label>
          <input style="width: 360px" id="phone" v-model="phoneNumber" type="text" placeholder="手机号" required />
        </div>
        <div>
          <label for="username">用户名:</label>
          <input style="width: 360px" id="username" v-model="username" type="text" placeholder="用户名" required />
        </div>
        <div>
          <label for="password">密码:</label>
          <input style="width: 360px" id="password" v-model="password" type="password" placeholder="密码" required />
        </div>
        <div>
          <label for="confirmPassword">确认密码:</label>
          <input style="width: 360px" id="confirmPassword" v-model="confirmPassword" type="password" placeholder="确认密码" required />
        </div>
        <button class="registerButton" type="submit">注册</button>
        <div class="login-link">
          <span>已有帐号？</span>
          <button class="loginButton" @click="goToLogin">登录</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { register } from '../api/User.ts';

const phoneNumber = ref('');
const username = ref('');
const password = ref('');
const confirmPassword = ref('');
const router = useRouter();

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
    });
  }
  if (password.value !== confirmPassword.value) {
    ElMessage.error({
      message: '两次输入的密码不一致',
      type: 'error',
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
        duration: 800,
      });
      router.push('/login');
    } else {
      ElMessage.error({
        message: res.data.msg,
        type: 'error',
        duration: 1000,
      });
    }
  });
};

const goToLogin = () => {
  router.push('/login');
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Orbitron:wght@400;500;700&display=swap');

.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: url('../assets/images/Loginbg.jpg') no-repeat center center;
  background-size: cover;
  font-family: 'Orbitron', sans-serif;
}

.register-form {
  background: rgba(255, 255, 255, 0.8);
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  width: 400px;
  text-align: center;
}

.register-form h2 {
  margin-bottom: 20px;
  color: #333;
  font-size: 24px;
  font-weight: 700;
}

.register-form div {
  margin-bottom: 20px;
  text-align: left;
}

.register-form label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #555;
  font-size: 16px;
}

.register-form input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
  transition: border-color 0.3s ease;
}

.register-form input:focus {
  border-color: #6e8efb;
  outline: none;
}

.registerButton {
  width: 90%;
  padding: 12px;
  background: linear-gradient(135deg, #6e8efb, #a777e3);
  color: #fff;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.registerButton:hover {
  background-color: #5a7de1;
}

.loginButton {
  margin-left: 5px;
  font-size: 14px;
  color: #6e8efb;
  background: none;
  border: none;
  cursor: pointer;
  text-decoration: underline;
}

.loginButton:hover {
  color: #5a7de1;
}

.login-link {
  display: flex;
  align-items: center;
  margin-top: 1em;
}

.login-link span {
  color: gray;
}
</style>