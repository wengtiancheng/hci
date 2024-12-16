<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getUserSolutions } from '../api/User';
import { SolutionVO } from "../api/Solution.ts";
import { deleteSolution } from "../api/Solution.ts";

const solutions = ref<SolutionVO[]>([]);
const isLoggedIn = ref(false);
const router = useRouter();

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
    solutions.value = response.data.result.map(solution => {
      return {
        ...solution,
        items: solution.images.map((image, index) => ({
          image,
          name: solution.componentNames[index]
        }))
      };
    });
    console.log('Solutions:', solutions.value);
  } catch (error) {
    console.error('Error fetching solutions:', error);
  } finally {
    isLoading.value = false;
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

const goToCustomBuild = () => {
  router.push({ path: '/custom-build' });
};

const isLoading = ref(true);

onMounted(() => {
  checkLoginStatus();
});
</script>

<template>
  <div class="my-solutions">
    <div v-if="isLoggedIn">
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
        <p class="spinner-text">正在加载...</p>
      </div>
      <div v-else>
        <div v-if="solutions.length === 0" class="no-solutions">
          <img src="../assets/images/no_solution.svg" alt="No Solutions" class="no-solutions-icon" />
          暂时还没有方案哦
          <button class="start-journey-button" @click="goToCustomBuild">创建我的方案</button>
        </div>
        <div v-else class="solutions-list">
          <div
              v-for="solution in solutions"
              :key="solution.id"
              class="solution-card"
          >
            <div class="solution-header">
              <h2 class="solution-name">{{ solution.name }}</h2>
              <img
                  src="../assets/images/delete.svg"
                  alt="Delete"
                  class="delete-icon"
                  @click="showDeleteDialog(solution.id)"
              />
            </div>

            <div class="solution-items">
              <div v-for="item in solution.items" :key="item.image" class="solution-item">
                <img
                    :src="item.image"
                    :alt="item.name"
                    class="solution-item-image"
                />
                <span class="solution-item-name">{{ item.name }}</span>
              </div>
            </div>

            <div class="solution-footer">
              <span class="solution-price">总价: ￥{{ solution.totalPrice }}</span>
              <button class="detail-button" @click="goToSolutionDetail(solution)">详情</button>
            </div>
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
  margin-top: 100px;  /* 控制文字与旋转器之间的距离 */
  font-size: 16px;
  color: #333;
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

.no-solutions {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 93.5vh;
  font-size: 1.5em;
  color: #666;
}

.no-solutions-icon {
  width: 100px;
  height: 100px;
  margin-bottom: 20px;
}

.start-journey-button {
  margin-top: 20px;
  background: linear-gradient(135deg, #878787, #000000);
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.start-journey-button:hover {
  background-color: #0056b3;
}

.my-solutions {
  position: relative;
  height: 100%; /* Fill the entire viewport height */
  min-height: 93vh;
  width: 100%; /* Fill the entire width */
  box-sizing: border-box;
  background: rgb(247, 248, 249);
}

.solutions-list {
  background: rgba(255, 255, 255, 0.0);
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
  margin-top: 20px;
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
  width: 25px;
  height: 25px;
  cursor: pointer;
  margin-right: 10px;
}

.solution-items {
  display: flex;
  flex-wrap: wrap; /* 允许换行 */
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
  display: -webkit-box; /* Use a flexbox-based layout */
  -webkit-box-orient: vertical; /* Set the box orientation to vertical */
  -webkit-line-clamp: 2; /* Limit the text to 2 lines */
  overflow: hidden; /* Hide overflow text */
  text-overflow: ellipsis; /* Add ellipsis for overflow text */
  max-width: 100px; /* Set a maximum width for the text */
  text-align: center; /* Center align the text */
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
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: linear-gradient(135deg, #6e8efb, #a777e3);
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