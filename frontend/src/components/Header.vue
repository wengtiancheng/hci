<script setup lang="ts">
// 你可以在这里添加导航栏的逻辑，如果需要的话
</script>

<template>
  <header class="navbar">
    <div class="logo">
      <img src="../assets/logo.png" alt="Logo" />
    </div>
    <nav class="nav-links">
      <ul>
        <li><a href="/custom-build">自定义装机</a></li>
        <li><a href="/builds">装机广场</a></li>
        <li><a href="/my-builds">我的装机</a></li>
      </ul>
    </nav>
  </header>
</template>

<style scoped>
/* 整体导航栏样式 */
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
  background-color: #2c3e50;
  color: white;
}

/* Logo 样式 */
.logo img {
  height: 40px;
  width: auto;
}

/* 导航链接容器 */
.nav-links ul {
  list-style: none;
  display: flex;
  gap: 20px;
  margin: 0;
  padding: 0;
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
