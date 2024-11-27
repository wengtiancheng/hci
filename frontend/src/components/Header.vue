<script setup lang="ts">
// 你可以在这里添加导航栏的逻辑，如果需要的话
</script>

<template>
  <header class="navbar">
    <div class="logo">
      <img src="../assets/logo.png" alt="Logo" />
    </div>
    <nav class="nav-links">
      <li><a href="/custom-build">自定义装机</a></li>
      <li><a href="/solution">装机广场</a></li>
      <li><a href="/my-builds">我的装机</a></li>
    </nav>
  </header>
</template>

<style scoped>
/* 整体导航栏样式 */
.navbar {
  position: fixed; /* 固定位置 */
  top: 0; /* 贴近顶部 */
  left: 0; /* 贴近左边 */
  right: 0; /* 贴近右边 */
  width: 100%; /* 宽度与整个网页一样宽 */
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 0; /* 移除左右的padding */
  background-color: black;
  color: white;
  z-index: 1000; /* 确保导航栏在最上层 */
  border-bottom-left-radius: 0; /* 左下圆角 */
  border-bottom-right-radius: 0; /* 右下圆角 */
  border-top-left-radius: 0; /* 保持左上角方形 */
  border-top-right-radius: 0; /* 保持右上角方形 */
}

/* Logo 样式 */
.logo img {
  margin-left: 20px;
  height: 40px;
  width: 40px;
}

/* 导航链接容器 */
.nav-links {
  list-style: none;
  display: flex;
  gap: 20px;
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

.nav-links li:hover {
  background-color: #34495e; /* Change background color on hover */
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
  background-color: #34495e;
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
