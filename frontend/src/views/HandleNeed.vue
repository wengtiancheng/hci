<script setup lang="ts">

import { ref } from 'vue';
import { parseUserNeed, } from '../api/Solution';
import { useRouter } from 'vue-router';
import Header from '../components/Header.vue';

// 定义用户输入的响应式变量
const userInput = ref("");
const router = useRouter(); // 获取路由实例

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
  <div class="container">
    <!-- 输入框 -->
    <input v-model="userInput" type="text" placeholder="请输入文本" class="input-box" />
    <!-- 按钮 -->
    <button @click="handleClick" class="submit-btn">获取文本</button>
  </div>
</template>

<style scoped>
/* 使用 Flexbox 将元素居中 */
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  flex-direction: column;
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
</style>