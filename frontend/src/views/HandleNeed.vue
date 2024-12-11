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
      快速搜索，让一切触手可及
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
    /* From Uiverse.io by Lakshay-art */
    <div class="grid"></div>
    <div id="poda">
      <div class="glow"></div>
      <div class="darkBorderBg"></div>
      <div class="darkBorderBg"></div>
      <div class="darkBorderBg"></div>

      <div class="white"></div>

      <div class="border"></div>

      <div id="main">
        <input placeholder="Search..." type="text" name="text" class="input" />
        <div id="input-mask"></div>
        <div id="pink-mask"></div>
        <div class="filterBorder"></div>
        <div id="filter-icon">
          <svg
              preserveAspectRatio="none"
              height="27"
              width="27"
              viewBox="4.8 4.56 14.832 15.408"
              fill="none"
          >
            <path
                d="M8.16 6.65002H15.83C16.47 6.65002 16.99 7.17002 16.99 7.81002V9.09002C16.99 9.56002 16.7 10.14 16.41 10.43L13.91 12.64C13.56 12.93 13.33 13.51 13.33 13.98V16.48C13.33 16.83 13.1 17.29 12.81 17.47L12 17.98C11.24 18.45 10.2 17.92 10.2 16.99V13.91C10.2 13.5 9.97 12.98 9.73 12.69L7.52 10.36C7.23 10.08 7 9.55002 7 9.20002V7.87002C7 7.17002 7.52 6.65002 8.16 6.65002Z"
                stroke="#d6d6e6"
                stroke-width="1"
                stroke-miterlimit="10"
                stroke-linecap="round"
                stroke-linejoin="round"
            ></path>
          </svg>
        </div>
        <div id="search-icon">
          <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              viewBox="0 0 24 24"
              stroke-width="2"
              stroke-linejoin="round"
              stroke-linecap="round"
              height="24"
              fill="none"
              class="feather feather-search"
          >
            <circle stroke="url(#search)" r="8" cy="11" cx="11"></circle>
            <line
                stroke="url(#searchl)"
                y2="16.65"
                y1="22"
                x2="16.65"
                x1="22"
            ></line>
            <defs>
              <linearGradient gradientTransform="rotate(50)" id="search">
                <stop stop-color="#f8e7f8" offset="0%"></stop>
                <stop stop-color="#b6a9b7" offset="50%"></stop>
              </linearGradient>
              <linearGradient id="searchl">
                <stop stop-color="#b6a9b7" offset="0%"></stop>
                <stop stop-color="#837484" offset="50%"></stop>
              </linearGradient>
            </defs>
          </svg>
        </div>
      </div>
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
  /* 添加背景图片 */
  background-image: url('../assets/images/triangle.png');
  background-size: cover; /* 图片铺满容器 */
  background-repeat: no-repeat;
  background-position: center; /* 居中显示背景图片 */
  /* 背景虚化效果 */
  backdrop-filter: blur(10px); /* 背景模糊程度 */
  -webkit-backdrop-filter: blur(10px); /* Safari 浏览器兼容性 */
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
/* From Uiverse.io by Lakshay-art */
.grid {
  height: 800px;
  width: 800px;
  background-image: linear-gradient(to right, #0f0f10 1px, transparent 1px),
  linear-gradient(to bottom, #0f0f10 1px, transparent 1px);
  background-size: 1rem 1rem;
  background-position: center center;
  position: absolute;
  z-index: -1;
  filter: blur(1px);
}
.white,
.border,
.darkBorderBg,
.glow {
  max-height: 70px;
  max-width: 314px;
  height: 100%;
  width: 100%;
  position: absolute;
  overflow: hidden;
  z-index: -1;
  /* Border Radius */
  border-radius: 12px;
  filter: blur(3px);
}
.input {
  background-color: #010201;
  border: none;
  /* padding:7px; */
  width: 602px;  /* 将宽度改为原来的两倍 */
  height: 56px;
  border-radius: 10px;
  color: white;
  padding-inline: 118px;  /* 调整 padding 使内容保持居中 */
  font-size: 18px;
}
#poda {
  display: flex;
  align-items: center;
  justify-content: center;
}
.input::placeholder {
  color: #c0b9c0;
}

.input:focus {
  outline: none;
}

#main:focus-within > #input-mask {
  display: none;
}

#input-mask {
  pointer-events: none;
  width: 100px;
  height: 20px;
  position: absolute;
  background: linear-gradient(90deg, transparent, black);
  top: 18px;
  left: 70px;
}
#pink-mask {
  pointer-events: none;
  width: 30px;
  height: 20px;
  position: absolute;
  background: #cf30aa;
  top: 10px;
  left: 5px;
  filter: blur(20px);
  opacity: 0.8;
  /*animation:leftright 4s ease-in infinite;*/
  transition: all 2s;
}
#main:hover > #pink-mask {
  /*animation: rotate 4s linear infinite;*/
  opacity: 0;
}

.white {
  max-height: 63px;
  max-width: 307px;
  border-radius: 10px;
  filter: blur(2px);
}

.white::before {
  content: "";
  z-index: -2;
  text-align: center;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) rotate(83deg);
  position: absolute;
  width: 600px;
  height: 600px;
  background-repeat: no-repeat;
  background-position: 0 0;
  filter: brightness(1.4);
  background-image: conic-gradient(
      rgba(0, 0, 0, 0) 0%,
      #a099d8,
      rgba(0, 0, 0, 0) 8%,
      rgba(0, 0, 0, 0) 50%,
      #dfa2da,
      rgba(0, 0, 0, 0) 58%
  );
  /*  animation: rotate 4s linear infinite;*/
  transition: all 2s;
}
.border {
  max-height: 59px;
  max-width: 303px;
  border-radius: 11px;
  filter: blur(0.5px);
}
.border::before {
  content: "";
  z-index: -2;
  text-align: center;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) rotate(70deg);
  position: absolute;
  width: 600px;
  height: 600px;
  filter: brightness(1.3);
  background-repeat: no-repeat;
  background-position: 0 0;
  background-image: conic-gradient(
      #1c191c,
      #402fb5 5%,
      #1c191c 14%,
      #1c191c 50%,
      #cf30aa 60%,
      #1c191c 64%
  );
  /* animation: rotate 4s 0.1s linear infinite;*/
  transition: all 2s;
}
.darkBorderBg {
  max-height: 65px;
  max-width: 312px;
}
.darkBorderBg::before {
  content: "";
  z-index: -2;
  text-align: center;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) rotate(82deg);
  position: absolute;
  width: 600px;
  height: 600px;
  background-repeat: no-repeat;
  background-position: 0 0;
  background-image: conic-gradient(
      rgba(0, 0, 0, 0),
      #18116a,
      rgba(0, 0, 0, 0) 10%,
      rgba(0, 0, 0, 0) 50%,
      #6e1b60,
      rgba(0, 0, 0, 0) 60%
  );
  transition: all 2s;
}
#poda:hover > .darkBorderBg::before {
  transform: translate(-50%, -50%) rotate(262deg);
}
#poda:hover > .glow::before {
  transform: translate(-50%, -50%) rotate(240deg);
}
#poda:hover > .white::before {
  transform: translate(-50%, -50%) rotate(263deg);
}
#poda:hover > .border::before {
  transform: translate(-50%, -50%) rotate(250deg);
}

#poda:hover > .darkBorderBg::before {
  transform: translate(-50%, -50%) rotate(-98deg);
}
#poda:hover > .glow::before {
  transform: translate(-50%, -50%) rotate(-120deg);
}
#poda:hover > .white::before {
  transform: translate(-50%, -50%) rotate(-97deg);
}
#poda:hover > .border::before {
  transform: translate(-50%, -50%) rotate(-110deg);
}

#poda:focus-within > .darkBorderBg::before {
  transform: translate(-50%, -50%) rotate(442deg);
  transition: all 4s;
}
#poda:focus-within > .glow::before {
  transform: translate(-50%, -50%) rotate(420deg);
  transition: all 4s;
}
#poda:focus-within > .white::before {
  transform: translate(-50%, -50%) rotate(443deg);
  transition: all 4s;
}
#poda:focus-within > .border::before {
  transform: translate(-50%, -50%) rotate(430deg);
  transition: all 4s;
}

.glow {
  overflow: hidden;
  filter: blur(30px);
  opacity: 0.4;
  max-height: 130px;
  max-width: 354px;
}
.glow:before {
  content: "";
  z-index: -2;
  text-align: center;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) rotate(60deg);
  position: absolute;
  width: 999px;
  height: 999px;
  background-repeat: no-repeat;
  background-position: 0 0;
  /*border color, change middle color*/
  background-image: conic-gradient(
      #000,
      #402fb5 5%,
      #000 38%,
      #000 50%,
      #cf30aa 60%,
      #000 87%
  );
  /* change speed here */
  //animation: rotate 4s 0.3s linear infinite;
  transition: all 2s;
}

@keyframes rotate {
  100% {
    transform: translate(-50%, -50%) rotate(450deg);
  }
}
@keyframes leftright {
  0% {
    transform: translate(0px, 0px);
    opacity: 1;
  }

  49% {
    transform: translate(250px, 0px);
    opacity: 0;
  }
  80% {
    transform: translate(-40px, 0px);
    opacity: 0;
  }

  100% {
    transform: translate(0px, 0px);
    opacity: 1;
  }
}

#filter-icon {
  position: absolute;
  top: 8px;
  right: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 2;
  max-height: 40px;
  max-width: 38px;
  height: 100%;
  width: 100%;

  isolation: isolate;
  overflow: hidden;
  /* Border Radius */
  border-radius: 10px;
  background: linear-gradient(180deg, #161329, black, #1d1b4b);
  border: 1px solid transparent;
}
.filterBorder {
  height: 42px;
  width: 40px;
  position: absolute;
  overflow: hidden;
  top: 7px;
  right: 7px;
  border-radius: 10px;
}

.filterBorder::before {
  content: "";

  text-align: center;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) rotate(90deg);
  position: absolute;
  width: 600px;
  height: 600px;
  background-repeat: no-repeat;
  background-position: 0 0;
  filter: brightness(1.35);
  background-image: conic-gradient(
      rgba(0, 0, 0, 0),
      #3d3a4f,
      rgba(0, 0, 0, 0) 50%,
      rgba(0, 0, 0, 0) 50%,
      #3d3a4f,
      rgba(0, 0, 0, 0) 100%
  );
  animation: rotate 4s linear infinite;
}
#main {
  position: relative;
}
#search-icon {
  position: absolute;
  left: 20px;
  top: 15px;
}

.search-box {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 2rem auto;
  width: 47%; /* 搜索框宽度为屏幕的一半 */
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
  //background-color: transparent; /* 确保背景透明，显示容器的背景图片 */
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

