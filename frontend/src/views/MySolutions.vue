<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getUserSolutions } from '../api/User';
import { SolutionVO } from '../api/Solution'; // 确保导入 SolutionVO 接口

// 定义用户方案的状态
const userSolutions = ref<SolutionVO[]>([]);

// 获取用户的装机方案
const fetchUserSolutions = async () => {
    try {
        const response = await getUserSolutions();
        userSolutions.value = response.data; // 假设返回的数据在 response.data 中
    } catch (error) {
        console.error('获取用户方案失败:', error);
    }
};

// 组件挂载时获取用户方案
onMounted(() => {
    fetchUserSolutions();
});
</script>

<template>
  <div class="my-solutions">
    <h1>我的装机方案</h1>
    <div v-if="userSolutions.length === 0">没有找到任何装机方案。</div>
    <div v-else>
      <div v-for="solution in userSolutions" :key="solution.id" class="solution-card">
        <img :src="solution.imageUrl" alt="方案图片" />
        <h2>{{ solution.name }}</h2>
        <p>{{ solution.description }}</p>
        <p>总价：￥{{ solution.totalPrice }}</p>
        <p>创建时间：{{ new Date(solution.createTime).toLocaleDateString() }}</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.my-solutions {
  padding: 20px;
}

.solution-card {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  margin-bottom: 20px;
  text-align: center;
}

.solution-card img {
  width: 100%;
  height: auto;
  border-radius: 8px;
}
</style>
