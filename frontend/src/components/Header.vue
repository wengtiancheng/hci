<script setup lang="ts">
import { ref, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import Toast from '../components/Toast.vue';

const toastRef = ref();
const token = sessionStorage.getItem('token');
const isModalVisible = ref(false);
const router = useRouter();
const route = useRoute();

const logout = () => {
  sessionStorage.removeItem('token');
  toastRef.value.show('登出成功', 'success');
  setTimeout(() => {
    location.reload();
  }, 1000);
};

const checkLoginStatus = (path: string) => {
  if (token) {
    router.push(path);
  } else {
    isModalVisible.value = true;
  }
};

const redirectToLogin = () => {
  router.push('/login');
};

const redirectToRegister = () => {
  router.push('/register');
};

const isMySolutionsSelected = computed(() => route.path === '/mySolutions');
</script>

<template>
  <header class="navbar">
    <Toast ref="toastRef" />
    <div class="logo">
      <router-link to="/">
        <img src="../assets/logo.png" alt="Logo" />
      </router-link>
    </div>
    <nav class="nav-links">
      <li><router-link to="/custom-build" active-class="selected">自定义装机</router-link></li>
      <li><router-link to="/solution" active-class="selected">装机广场</router-link></li>
      <li><a @click.prevent="checkLoginStatus('/mySolutions')">我的装机</a></li>
    </nav>
    <nav class="login">
      <li><a v-if="token" @click="logout">登出</a>
        <a v-else href="/login">登录</a>
      </li>
    </nav>
  </header>

  <div v-if="isModalVisible" class="modal">
    <div class="modal-content">
      <button class="close-button" @click="isModalVisible = false">&times;</button>
      <p style="margin-top: 20px">请先登录后查看我的装机方案</p>
      <button class="modal-button" @click="redirectToLogin">登录</button>
      <button class="modal-button" @click="redirectToRegister">注册</button>
    </div>
  </div>
</template>

<style scoped>
/* 整体导航栏样式 */
.navbar {
  top: 0; /* 贴近顶部 */
  left: 0; /* 贴近左边 */
  right: 0; /* 贴近右边 */
  width: 100%; /* 宽度与整个网页一样宽 */
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 6px 0; /* 移除左右的padding */
  background-color: black;
  color: white;
  z-index: 1000; /* 确保导航栏在最上层 */
}

/* Logo 样式 */
.logo img {
  margin-left: 20px;
  height: 43px;
  width: 43px;
}

/* 导航链接容器 */
.nav-links {
  list-style: none;
  display: flex;
  gap: 80px;
  margin: 0 auto; /* Center align the list */
  padding: 0;
  justify-content: center; /* Center align items */
}

.nav-links li {
  background-color: black; /* Match the header background color */
  border: none; /* Remove the border */
  border-radius: 4px; /* Rounded corners */
  padding: 8px 16px; /* Padding for the button */
  transition: background-color 0.3s ease; /* Smooth transition for background color */
}

.nav-links a {
  text-decoration: none;
  color: white;
  font-size: 16px;
  font-weight: 500;
  padding: 8px 16px;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

.nav-links a:hover {
  background-color: #404040;
}

.nav-links a:active, .nav-links a.selected {
  background-color: #404040; /* Change background color when active or selected */
  color: #ecf0f1; /* Change text color when active or selected */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Add a shadow effect */
}

.login {
  list-style: none;
  display: flex;
  gap: 20px;
  margin-right: 20px;
  padding: 0;
}

.login li {
  background-color: black;
  border: none;
  border-radius: 4px;
  padding: 8px 16px;
  transition: background-color 0.3s ease;
}

.login a {
  text-decoration: none;
  color: white;
  font-size: 16px;
  font-weight: 500;
  padding: 8px 16px;
  border-radius: 4px;
  transition: background-color 0.3s ease;
}

.login a:hover {
  background-color: #34495e;
}

/* Modal 样式 */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2000;
}

.modal-content {
  position: relative;
  background: white;
  padding: 40px; /* Increased padding */
  font-size: 20px;
  border-radius: 8px;
  text-align: center;
  width: 400px; /* Increased width */
  height: 300px; /* Increased height */
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 24px;
  cursor: pointer;
}

.modal-button {
  display: block;
  width: 100%;
  margin: 10px 0;
  padding: 10px 20px;
  font-size: 20px;
  cursor: pointer;
  border: none;
  border-radius: 4px;
  background: linear-gradient(135deg, #6e8efb, #a777e3);
  color: white;
  transition: background-color 0.3s ease;
  margin-top: 20px
}

.modal-button:hover {
  background-color: #5a7de1;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .navbar {
    flex-direction: column;
    align-items: flex-start;
  }

  .nav-links ul {
    flex-direction: column;
    gap: 10px;
  }
}
</style>