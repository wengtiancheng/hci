<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getUserSolutions } from '../api/User';

const solutions = ref([]);
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

const goToSolutionDetail = (solutionId: number) => {
  router.push({ path: `/solution/${solutionId}` });
};

onMounted(() => {
  fetchSolutions();
});
</script>

<template>
  <div class="my-solutions">
    <h1>所有装机方案</h1>
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
</style>