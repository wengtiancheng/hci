<script setup lang="ts">

import { ref } from 'vue';
import { parseUserNeed, } from '../api/Solution';
import { useRouter } from 'vue-router';
import Header from '../components/Header.vue';

// 定义用户输入的响应式变量
const userInput = ref("");
const router = useRouter(); // 获取路由实例
const documentation = ref("../assets/images/documentation.png"); // 替换为实际路径

// 定义按钮点击事件的处理函数
const handleClick = async () => {
  console.log(userInput.value) // 获取并输出输入框的文本
  const filters = await parseUserNeed(userInput.value); // 获取解析后的结果
  console.log(filters) // 输出解析后的结果

  if (filters) {
    // 跳转到 AllSolutions，并传递 filters 作为路由参数
    router.push({ path: '/solution', query: { filters: JSON.stringify(filters) } });
  }
}




</script>

<template>
  <Header />
  <!--  <div class="input_container">-->
  <!--    &lt;!&ndash; 输入框 &ndash;&gt;-->
  <!--    <input v-model="userInput" type="text" placeholder="例：我想要配置一台10000以内的电脑，用来玩 3A 游戏" class="input-box" style="width: 500px;" />-->
  <!--    &lt;!&ndash; 按钮 &ndash;&gt;-->
  <!--    <button @click="handleClick" class="submit-btn">获取文本</button>-->
  <!--  </div>-->
  <section class="input_container">
    <!-- 顶部搜索框 -->
    <div class="search-box">
      <div class="search-icon">
        🔍
      </div>
      <input
          v-model="userInput"
          type="text"
          class="search-input"
          placeholder="例：我想要配置一台10000以内的电脑，用来玩 3A 游戏"
          @keydown.enter="handleClick"
      />
    </div>
  </section>
  <section class="intro-section">
    <div class="container">
      <!-- 左侧文字区域 -->
      <div class="text-content">
        <h2 class="title">Complex Documentation</h2>
        <p class="description">
          This extension comes with a lot of fully coded examples that help you get started faster.
          You can adjust the colors and also the programming language.
          You can change the text and images and you're good to go.
        </p>
        <ul class="features">
          <li><span>🚀</span> Built by Developers for Developers</li>
          <li><span>🛠️</span> Carefully crafted code for Components</li>
          <li><span>⚡</span> Dynamic Javascript Components</li>
        </ul>
      </div>

      <!-- 右侧图片区域 -->
      <div class="image-content">
        <img src="../assets/images/documentation.png" alt="Complex Documentation Example" class="example-image" />
      </div>
    </div>
  </section>
  <section class="intro-section">
    <div class="container">
      <!-- 左侧图片区域 -->
      <div class="image-content">
        <img src="../assets/images/documentation.png" alt="Complex Documentation Example" class="example-image" />
      </div>
      <!-- 右侧文字区域 -->
      <div class="text-content">
        <h2 class="title">Complex Documentation</h2>
        <p class="description">
          This extension comes with a lot of fully coded examples that help you get started faster.
          You can adjust the colors and also the programming language.
          You can change the text and images and you're good to go.
        </p>
        <ul class="features">
          <li><span>🚀</span> Built by Developers for Developers</li>
          <li><span>🛠️</span> Carefully crafted code for Components</li>
          <li><span>⚡</span> Dynamic Javascript Components</li>
        </ul>
      </div>
    </div>
  </section>
  <!-- 联系方式部分 -->
  <footer class="footer-section">
    <div class="footer-container">
      <p class="footer-text">You've reached the bottom of the page.</p>
      <p class="footer-contact">
        For inquiries, reach us at:
        <a href="mailto:contact@yourdomain.com" class="contact-link">contact@yourdomain.com</a>
      </p>
      <div class="social-icons">
        <a href="https://twitter.com/yourhandle" target="_blank" class="social-link">🐦 Twitter</a>
        <a href="https://github.com/yourprofile" target="_blank" class="social-link">💻 GitHub</a>
        <a href="https://linkedin.com/in/yourprofile" target="_blank" class="social-link">🔗 LinkedIn</a>
      </div>
    </div>
  </footer>

</template>

<style scoped>
/* 使用 Flexbox 将元素居中 */
.input_container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80vh;
  flex-direction: column;
}
/* 搜索框样式 */
.search-box {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 2rem auto;
  width: 50%; /* 搜索框宽度为屏幕的一半 */
  border-radius: 25px; /* 圆角 */
  border: 1px solid #ddd;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 0.5rem 1rem;
}

.search-icon {
  font-size: 1.5rem; /* 图标大小 */
  margin-right: 0.5rem;
  color: #888; /* 图标颜色 */
}

.search-input {
  width: 100%;
  border: none;
  outline: none;
  font-size: 1rem;
  color: #333;
}

/* 样式输入框 */
.input-box {
  padding: 10px;
  font-size: 16px;
  margin-bottom: 20px;
  width: 200px;
  text-align: center;
}

/* 样式按钮 */
.submit-btn {
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
}

.submit-btn:hover {
  background-color: #0056b3;
}

/* 样式介绍部分 */
.intro-section {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 4rem 2rem;
  //background-color: #b6f1de;
}

.container {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.text-content {
  flex: 1;
  max-width: 600px;
}

.title {
  font-size: 2rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 1rem;
}

.description {
  font-size: 1rem;
  color: #555;
  line-height: 1.6;
  margin-bottom: 1.5rem;
}

.features {
  list-style: none;
  padding: 0;
  margin: 0;
}

.features li {
  font-size: 0.9rem;
  color: #666;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 0.5rem;
}

.image-content {
  flex: 1;
  max-width: 600px;
  text-align: center;
}

.example-image {
  max-width: 100%;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

/* Footer 样式 */
.footer-section {
  background-color: #333;
  color: #fff;
  text-align: center;
  padding: 2rem 1rem;
}

.footer-container {
  max-width: 1200px;
  margin: 0 auto;
}

.footer-text {
  font-size: 1.2rem;
  margin-bottom: 0.5rem;
}

.footer-contact {
  font-size: 1rem;
  margin-bottom: 1rem;
}

.contact-link {
  color: #4a90e2;
  text-decoration: none;
}

.contact-link:hover {
  text-decoration: underline;
}

.social-icons {
  display: flex;
  justify-content: center;
  gap: 1.5rem;
  margin-top: 1rem;
}

.social-link {
  color: #4a90e2;
  text-decoration: none;
  font-size: 1rem;
}

.social-link:hover {
  text-decoration: underline;
}

</style>

