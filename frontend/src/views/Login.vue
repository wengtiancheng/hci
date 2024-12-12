<template>
  <div class="login-container">
    <Toast ref="toastRef" />
    <div class="login-form">
      <h2>登录</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="phone">手机号</label>
          <input style="width: 320px" type="text" id="phone" v-model="phone" required />
        </div>
        <div class="form-group">
          <label for="password">密码</label>
          <input style="width: 320px" type="password" id="password" v-model="password" required />
        </div>
        <button class="loginButton" type="submit">登录</button>
      </form>
      <button class="registerButton" @click="goToRegister">注册</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { login } from '../api/User';
import Toast from '../components/Toast.vue';

import 'element-plus/dist/index.css';

const phone = ref('');
const password = ref('');
const router = useRouter();
const toastRef = ref();

const handleLogin = async () => {
  try {
    const response = await login(phone.value, password.value);
    sessionStorage.setItem('token', response.result);
    console.log('token:', response.result);
    if (response.code == "000") {


      toastRef.value.show('登录成功');
      setTimeout(() => {
        router.push('/solution');
      }, 1000);
    } else {
      toastRef.value.show('手机号或密码错误', 'error');
    }
  } catch (error) {
    console.error('登录失败:', error);
    toastRef.value.show('登录失败', 'error');

  }
};

const goToRegister = () => {
  router.push('/register');
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Orbitron:wght@400;500;700&display=swap');

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: url('../assets/images/Loginbg.jpg') no-repeat center center;
  background-size: cover;
  font-family: 'Orbitron', sans-serif;
}

.login-form {
  background: rgba(255, 255, 255, 0.8);
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  width: 400px;
  text-align: center;
}

.login-form h2 {
  margin-bottom: 20px;
  color: #333;
  font-size: 24px;
  font-weight: 700;
}

.form-group {
  margin-bottom: 20px;
  text-align: left;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: #555;
  font-size: 16px;
}

.form-group input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
  transition: border-color 0.3s ease;
}

.form-group input:focus {
  border-color: #6e8efb;
  outline: none;
}

.loginButton {
  width: 95%;
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

.loginButton:hover {
  background-color: #5a7de1;
}

.registerButton {
  margin-top: 10px;
  font-size: 14px;
  color: #6e8efb;
  background: none;
  border: none;
  cursor: pointer;
  text-decoration: underline;
}

.registerButton:hover {
  color: #5a7de1;
}
</style>