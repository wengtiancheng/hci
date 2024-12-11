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
    <div class="slogan">
      得机，让一切触手可及
    </div>
    <!-- 顶部搜索框 -->
<!--    <div class="search-box">-->
<!--      <div class="search-icon">-->
<!--        🔍-->
<!--      </div>-->
<!--      <input-->
<!--          v-model="userInput"-->
<!--          type="text"-->
<!--          class="search-input"-->
<!--          placeholder="例：我想要配置一台10000以内的电脑，用来玩 3A 游戏"-->
<!--          @keydown.enter="handleClick"-->
<!--      />-->
<!--    </div>-->
    <div class="group">
      <svg class="icon" aria-hidden="true" viewBox="0 0 24 24"><g><path d="M21.53 20.47l-3.66-3.66C19.195 15.24 20 13.214 20 11c0-4.97-4.03-9-9-9s-9 4.03-9 9 4.03 9 9 9c2.215 0 4.24-.804 5.808-2.13l3.66 3.66c.147.146.34.22.53.22s.385-.073.53-.22c.295-.293.295-.767.002-1.06zM3.5 11c0-4.135 3.365-7.5 7.5-7.5s7.5 3.365 7.5 7.5-3.365 7.5-7.5 7.5-7.5-3.365-7.5-7.5z"></path></g></svg>
      <input placeholder="例：我想要配置一台10000以内的电脑，用来玩 3A 游戏"
             type="search" class="input"
             @keydown.enter="handleClick"
      >
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
        <img src="../assets/images/landing.jpg" alt="Complex Documentation Example" class="example-image" />
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

.input_container {
  /* 使用 Flexbox 将元素居中 */
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80vh;
  flex-direction: column;
  text-align: center; /* 文字居中 */
  margin-top: 2rem; /* 顶部留白 */
  position: relative; /* 使子元素的绝对定位相对于父元素 */

  /* 添加背景图片 */
  background-image: url('../assets/images/triangle.png');
  background-size: cover; /* 图片铺满容器 */
  background-repeat: no-repeat;
  background-position: center; /* 居中显示背景图片 */
  /* 背景虚化效果 */
  backdrop-filter: blur(20px); /* 背景模糊程度 */
  -webkit-backdrop-filter: blur(20px); /* Safari 浏览器兼容性 */
}

.input_container::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.4); /* 40% 黑色透明度覆盖 */
  z-index: 1; /* 确保覆盖层在背景图片之上 */
}

.input_container > * {
  position: relative;
  z-index: 2; /* 确保内容在覆盖层之上 */
}

/* 标语样式 */
.slogan {
  font-size: 4rem; /* 标语文字大小 */
  color: white; /* 白色文字 */
  margin-bottom: 1rem; /* 与搜索框的距离 */
  font-weight: bold; /* 加粗文字 */
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3); /* 增加文字阴影，让文字更加清晰 */
}
/* 搜索框样式 */

.group {
  display: flex;
  line-height: 40px;
  align-items: center;
  position: relative;
  width: 700px;
}

.input {
  width: 100%;
  height: 50px;
  left: 2rem;
  line-height: 40px;
  padding: 0 1rem;
  padding-left: 2.5rem;
  border: 2px solid transparent;
  border-radius: 8px;
  outline: none;
  background-color: #dcdcdc;
  transition: .3s ease;
}

.input::placeholder {
  color: #60626c;
}

.input:focus, input:hover {
  outline: none;
  border-color: rgba(103, 161, 227, 0.4);
  background-color: #0a0a0a;
  box-shadow: 0 0 0 4px rgba(184, 161, 196, 0.1);
  color: #d1dae3;
}

.icon {
  position: absolute;
  left: 1rem;
  fill: #9e9ea7;
  width: 1.4rem;
  height: 1.4rem;
}


/* 样式介绍部分 */
.intro-section {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 8rem 4rem;
  background-color: #0b0b0b;
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
  color: #ffffff;
  margin-bottom: 1rem;
}

.description {
  font-size: 1rem;
  color: #ffffff;
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
  color: #ffffff;
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

