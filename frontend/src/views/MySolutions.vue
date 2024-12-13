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
.text-solution-name {
  margin-top: 5px;
  font-size: 14px;
  color: #333;
  white-space: nowrap; /* Prevent text from wrapping */
  overflow: hidden; /* Hide overflow text */
  text-overflow: ellipsis; /* Add ellipsis for overflow text */
  max-width: 90px; /* Set a maximum width for the text */
  text-align: center; /* Center align the text */
}

.my-solutions {
  position: relative;
  height: 100%; /* Fill the entire viewport height */
  width: 100%; /* Fill the entire width */
  box-sizing: border-box;
  z-index: -1; /* Send the background image behind other content */
}

.my-solutions::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: url('/src/assets/images/bg_light.png') no-repeat center center;
  background-size: cover; /* Ensure the background image covers the entire container */
  background-attachment: fixed; /* Fix the background image */
  opacity: 0.5; /* Set the opacity to 50% */
  z-index: -1; /* Ensure the pseudo-element is behind the content */
}

.title {
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
  font-weight: bold;
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
  width: 20px;
  height: 20px;
  cursor: pointer;
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
  display: block;
  margin-top: 100px;
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