<template>
  <div class="login-container">
    <div class="login-form">
      <h2>登录</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="phone">手机号</label>
          <input type="text" id="phone" v-model="phone" required />
        </div>
        <div class="form-group">
          <label for="password">密码</label>
          <input type="password" id="password" v-model="password" required />
        </div>
        <button type="submit">登录</button>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { login } from '../api/User';

const phone = ref('');
const password = ref('');
const router = useRouter();

const handleLogin = async () => {
  try {
    const response = await login(phone.value, password.value);
    console.log('登录结果:', response);
    if (response.code == "000") {
      alert('登录成功');
      router.push('/solution');
    } else {
      alert('手机号或密码错误');
    }
  } catch (error) {
    console.error('登录失败:', error);
    alert('登录失败，请稍后再试');
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #6e8efb, #a777e3);
}

.login-form {
  background: #fff;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  width: 350px;
  text-align: center;
}

.login-form h2 {
  margin-bottom: 20px;
  color: #333;
}

.form-group {
  margin-bottom: 20px;
  text-align: left;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
  color: #555;
}

.form-group input {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1em;
  transition: border-color 0.3s ease;
}

.form-group input:focus {
  border-color: #6e8efb;
  outline: none;
}

button {
  width: 100%;
  padding: 12px;
  background-color: #6e8efb;
  color: #fff;
  border: none;
  border-radius: 5px;
  font-size: 1em;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #5a7de1;
}
</style>