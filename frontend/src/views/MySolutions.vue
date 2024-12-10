<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getUserSolutions } from '../api/User';

const solutions = ref([]);
const isLoggedIn = ref(false);
const router = useRouter();

const fetchSolutions = async () => {
  try {
    const response = await getUserSolutions();
    console.log('Fetched solutions:', response.data.result);
    solutions.value = response.data.result;
  } catch (error) {
    console.error('Error fetching solutions:', error);
  }
};

const checkLoginStatus = () => {
  const user = sessionStorage.getItem("token");
  if (user) {
    isLoggedIn.value = true;
    fetchSolutions();
  } else {
    isLoggedIn.value = false;
  }
};

const goToSolutionDetail = (solutionId: number) => {
  router.push({ path: `/solution/${solutionId}` });
};

const redirectToLogin = () => {
  router.push({ path: '/login' });
};

onMounted(() => {
  checkLoginStatus();
});
</script>

<template>
  <div class="my-solutions">
    <div v-if="isLoggedIn">
      <h1>我的装机</h1>
      <div class="solutions-list">
        <div
            v-for="solution in solutions"
            :key="solution.id"
            class="solution-item"
            @click="goToSolutionDetail(solution.id)"
        >
          <h2>{{ solution.name }}</h2>
          <p>{{ solution.description }}</p>
        </div>
      </div>
    </div>
    <div v-else>
      <button class="login-button" @click="redirectToLogin">请先登录</button>
    </div>
  </div>
</template>

<style scoped>
.my-solutions {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
}

.solutions-list {
  width: 100%;
  text-align: center;
}

.solution-item {
  border: 1px solid #ccc;
  padding: 20px;
  margin: 10px 0;
  border-radius: 4px;
  cursor: pointer; /* Add cursor pointer to indicate clickable items */
}

.login-button {
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  background: linear-gradient(135deg, #6e8efb, #a777e3);
  color: white;
  border: none;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.login-button:hover {
  background-color: #0056b3;
}
</style>