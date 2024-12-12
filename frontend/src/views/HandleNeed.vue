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
  isLoading.value = true;
  const filters = await parseUserNeed(userInput.value); // 获取解析后的结果
  console.log(filters) // 输出解析后的结果

  if (filters) {
    // 跳转到 AllSolutions，并传递 filters 作为路由参数
    router.push({ path: '/solution', query: { filters: JSON.stringify(filters) } });
  }
}

const isLoading = ref(false);


</script>

<template>
  <div v-if="isLoading" class="loading-overlay">
    <div class="spinner">
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
    </div>
    <p class="spinner-text"></p>
  </div>
  <div v-else >
    <section class="input_container">
      <div class="slogan">
        得机——智能推荐，轻松装机
      </div>
      <div class="group">
        <svg class="icon" aria-hidden="true" viewBox="0 0 24 24"><g><path d="M21.53 20.47l-3.66-3.66C19.195 15.24 20 13.214 20 11c0-4.97-4.03-9-9-9s-9 4.03-9 9 4.03 9 9 9c2.215 0 4.24-.804 5.808-2.13l3.66 3.66c.147.146.34.22.53.22s.385-.073.53-.22c.295-.293.295-.767.002-1.06zM3.5 11c0-4.135 3.365-7.5 7.5-7.5s7.5 3.365 7.5 7.5-3.365 7.5-7.5 7.5-7.5-3.365-7.5-7.5z"></path></g>
        </svg>
        <input placeholder="输入您的预算、需求等。例：我想要玩3A游戏，预算10000以内。"
               type="search" class="input"
               @keydown.enter="handleClick"
        >
      </div>
    </section>
    <section class="intro-section-1">
      <div class="container">
        <!-- 左侧文字区域 -->
        <div class="text-content">
          <h1 class="title">自定义装机</h1>
          <p class="description">
            在“自定义装机”功能中，您可以自由选择各类硬件配件，按照自己的想法、需求构建专属的PC配置。支持一键保存您的装机方案，轻松进行多次修改和查看。系统还将实时进行兼容性检测，确保每一项配件都能完美匹配，避免不必要的冲突。
          </p>
          <ul class="features">
            <li><span>💡</span> 自由选择各类配件，打造专属配置</li>
            <li><span>🔧</span> 一键保存装机方案，随时查看和修改</li>
            <li><span>⚙️</span> 兼容性检测，确保硬件完美匹配</li>
          </ul>
        </div>


        <!-- 右侧图片区域 -->
        <div class="image-content">
          <img src="../assets/images/documentation.png" alt="Complex Documentation Example" class="example-image" />
        </div>
      </div>
    </section>
    <section class="intro-section-2">
      <div class="container">
        <!-- 左侧图片区域 -->
        <div class="image-content">
          <img src="../assets/images/landing.jpg" alt="Complex Documentation Example" class="example-image" />
        </div>
        <!-- 右侧文字区域 -->
        <div class="text-content">
          <h1 class="title">装机方案</h1>
          <p class="description">
            在装机方案广场，您可以浏览其他用户分享的优质装机方案，获取灵感，参考他人的选择。通过丰富的过滤条件，轻松查找符合您预算、用途和品牌偏好的方案。同时，您还可以根据个人需求对参考方案进行自定义修改，进一步优化配置。
          </p>
          <ul class="features">
            <li><span>🌟</span> 查看他人推荐的装机方案，轻松参考</li>
            <li><span>💬</span> 丰富的过滤条件，精确匹配您的需求</li>
            <li><span>📈</span> 自定义修改方案，打造更符合您的配置</li>
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
          <a href="mailto:contact@yourdomain.com" class="contact-link">https://github.com/1193600423</a>
        </p>
        <div class="social-icons">
          <a href="https://twitter.com/yourhandle" target="_blank" class="social-link">🐦 Twitter</a>
          <a href="https://github.com/yourprofile" target="_blank" class="social-link">💻 GitHub</a>
          <a href="https://linkedin.com/in/yourprofile" target="_blank" class="social-link">🔗 LinkedIn</a>
        </div>
      </div>
    </footer>

  </div>
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
  line-height: 45px;
  align-items: center;
  position: relative;
  width: 900px;
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
  fill: #505054;
  width: 1.4rem;
  height: 1.4rem;
}


/* 样式介绍部分 */
.intro-section-1 {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 8rem 4rem;
  background: linear-gradient(to bottom, #000000, #1f1f1f);
}
.intro-section-2 {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 8rem 4rem;
  background: linear-gradient(to bottom, #1f1f1f, #1e1e28);
}
.footer-section {
  background-color: #000000;
  color: #fff;
  text-align: center;
  padding: 6rem 3rem;
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

/* loading */
.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}

.spinner {
  position: absolute;
  width: 9px;
  height: 9px;
}


.spinner-text {
  font-size: 16px;
  color: #333;
  margin-top: 100px;  /* 控制文字与旋转器之间的距离 */
  text-align: center;
  animation: text-switch 15s infinite;
}

@keyframes text-switch {
  0%, 20% {
    content: "稍等，数据还在穿衣服~";
  }
  21%, 40% {
    content: "加载中，系统正在呼吸~";
  }
  41%, 60% {
    content: "数据正在穿越时间到达~";
  }
  61%, 80% {
    content: "AI 小助手正在全力加速！";
  }
  81%, 100% {
    content: "别担心，这不是蓝屏~";
  }
}

/* 覆盖伪类以实现文字切换 */
.spinner-text::before {
  content: "正在加载中~~~";
  animation: text-switch 15s infinite;
}

.spinner div {
  position: absolute;
  width: 50%;
  height: 150%;
  background: #001657;
  transform: rotate(calc(var(--rotation) * 1deg)) translate(0, calc(var(--translation) * 1%));
  animation: spinner-fzua35 1s calc(var(--delay) * 1s) infinite ease;
}

.spinner div:nth-child(1) {
  --delay: 0.1;
  --rotation: 36;
  --translation: 150;
}

.spinner div:nth-child(2) {
  --delay: 0.2;
  --rotation: 72;
  --translation: 150;
}

.spinner div:nth-child(3) {
  --delay: 0.3;
  --rotation: 108;
  --translation: 150;
}

.spinner div:nth-child(4) {
  --delay: 0.4;
  --rotation: 144;
  --translation: 150;
}

.spinner div:nth-child(5) {
  --delay: 0.5;
  --rotation: 180;
  --translation: 150;
}

.spinner div:nth-child(6) {
  --delay: 0.6;
  --rotation: 216;
  --translation: 150;
}

.spinner div:nth-child(7) {
  --delay: 0.7;
  --rotation: 252;
  --translation: 150;
}

.spinner div:nth-child(8) {
  --delay: 0.8;
  --rotation: 288;
  --translation: 150;
}

.spinner div:nth-child(9) {
  --delay: 0.9;
  --rotation: 324;
  --translation: 150;
}

.spinner div:nth-child(10) {
  --delay: 1;
  --rotation: 360;
  --translation: 150;
}

@keyframes spinner-fzua35 {
  0%, 10%, 20%, 30%, 50%, 60%, 70%, 80%, 90%, 100% {
    transform: rotate(calc(var(--rotation) * 1deg)) translate(0, calc(var(--translation) * 1%));
  }

  50% {
    transform: rotate(calc(var(--rotation) * 1deg)) translate(0, calc(var(--translation) * 1.5%));
  }
}
</style>

