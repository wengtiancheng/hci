<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import {getSolutionImages, getUserSolutions} from '../api/User';
import {CPUVO, newCPUVO} from "../api/CPU.ts";
import {DisplayVO, newDisplayVO} from "../api/Display.ts";
import {SolutionVO} from "../api/Solution.ts";
import {deleteSolution} from "../api/Solution.ts";


const solutions = ref([]);
const isLoggedIn = ref(false);
const router = useRouter();
let cpu = ref<CPUVO>(newCPUVO());
let display = ref<DisplayVO>(newDisplayVO());

const isDeleteDialogVisible = ref(false);
const solutionIdToDelete = ref<number | null>(null);

const showDeleteDialog = (id: number) => {
  solutionIdToDelete.value = id;
  isDeleteDialogVisible.value = true;
};

const confirmDelete = async () => {
  if (solutionIdToDelete.value !== null) {
    await deleteSolution(solutionIdToDelete.value);
    isDeleteDialogVisible.value = false;
    window.location.reload();
  }
};

const fetchSolutions = async () => {
  try {
    const response = await getUserSolutions();
    console.log('Fetched solutions:', response.data.result);
    solutions.value = response.data.result;
    for (let i = 0; i < solutions.value.length; i++) {
      const response = await getSolutionImages(solutions.value[i].id);
      console.log('Fetched images:', response);
      solutions.value[i].items = response;
    }
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

const goToSolutionDetail = (solution: SolutionVO) => {
  router.push({ path: `/custom-build`, query: { solution: JSON.stringify(solution) }});
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
      <h1 class="title">我的装机方案</h1>
      <div class="solutions-list">
        <div
            v-for="solution in solutions"
            :key="solution.id"
            class="solution-card"
        >
          <div class="solution-header">
            <h2 class="solution-name">{{ solution.name }}</h2>
            <img
                src="../assets/images/delete.png"
                alt="Delete"
                class="delete-icon"
                @click="showDeleteDialog(solution.id)"
            />
          </div>
          <div class="solution-items">
            <div v-for="item in solution.items" :key="item.id" class="solution-item">
              <img
                  :src="item"
                  :alt="item.name"
                  class="solution-item-image"
              />
              <p class="solution-item-name">名字</p>
            </div>
          </div>
          <div class="solution-footer">
            <span class="solution-price">总价: ￥{{ solution.totalPrice }}</span>
            <button class="detail-button" @click="goToSolutionDetail(solution)">详情</button>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <button class="login-button" @click="redirectToLogin">请先登录</button>
    </div>

    <!-- Confirmation Modal -->
    <div v-if="isDeleteDialogVisible" class="modal-overlay">
      <div class="modal">
        <h3>Warning</h3>
        <p>您确定要删除此方案吗？</p>
        <div class="modal-actions">
          <button @click="isDeleteDialogVisible = false" class="cancel-button">取消</button>
          <button @click="confirmDelete" class="confirm-button">确认</button>
        </div>
      </div>
    </div>

  </div>
</template>

<style scoped>
.my-solutions {
  padding: 20px;
  background-color: rgb(243, 245, 248);
  height: 100vh; /* Fill the entire viewport height */
  width: 100%; /* Fill the entire width */
  box-sizing: border-box;
}

.title {
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
  font-weight: bold;
}

.solutions-list {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

.solution-card {
  width: 75%; /* 占屏幕宽度的3/4 */
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
  transition: transform 0.3s, box-shadow 0.3s;
}

.solution-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.solution-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
}

.solution-name {
  font-size: 18px;
  font-weight: bold;
}

.delete-icon {
  width: 20px;
  height: 20px;
  cursor: pointer;
}

.solution-items {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 15px;
  margin-bottom: 15px;
}

.solution-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.solution-item-name {
  margin-top: 5px;
  font-size: 14px;
  color: #333;
}

.solution-item-image {
  width: 90px;
  height: 90px;
  object-fit: cover;
  border-radius: 4px;
}

.solution-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.solution-price {
  font-size: 16px;
  font-weight: bold;
  color: #333;
}

.detail-button {
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 8px 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.detail-button:hover {
  background-color: #0056b3;
}

.login-button {
  display: block;
  margin: 0 auto;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.login-button:hover {
  background-color: #0056b3;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.6); /* Darker background */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000; /* Ensure it is above other elements */
}

.modal {
  background: white;
  padding: 10px;
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2); /* Softer shadow */
  text-align: center;
  max-width: 400px;
  width: 100%;
  animation: fadeIn 0.3s ease; /* Add fade-in animation */
}

.modal h3 {
  text-align: left;
  margin-left: 10px;
  font-size: 1.5em;
  color: #333;
  margin-top: 10px;
  margin-bottom: 5px; /* Reduce the bottom margin */
}

.modal p {
  font-size: 1em;
  color: #666;
  text-align: left;
  margin-bottom: 20px;
  margin-left: 10px;
  margin-top: 5px; /* Reduce the top margin */
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.confirm-button, .cancel-button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1em;
  transition: background-color 0.3s;
}

.confirm-button {
  background-color: #ff4d4f;
  width: 100px;
  color: white;
}

.confirm-button:hover {
  background-color: #e04344;
}

.cancel-button {
  background-color: #d3d3d3;
  width: 100px;
  color: black;
}

.cancel-button:hover {
  background-color: #bcbcbc;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>