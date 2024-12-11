<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getUserSolutions } from '../api/User';
import {CPUVO, newCPUVO} from "../api/CPU.ts";
import {GPUVO, newGPUVO} from "../api/GPU.ts";
import {MemoryVO, newMemoryVO} from "../api/Memory.ts";
import {MotherboardVO, newMotherboardVO} from "../api/Motherboard.ts";
import {HardDiskVO, newHardDiskVO} from "../api/Harddisk.ts";
import {newPowersupplyVO, PowersupplyVO} from "../api/Powersupply.ts";
import {CoolingVO, newCoolingVO} from "../api/Cooling.ts";
import {ChassisVO, newChassisVO} from "../api/Chassis.ts";
import {DisplayVO, newDisplayVO} from "../api/Display.ts";
import { getCPUById } from '../api/CPU';
import { getGPUById } from '../api/GPU';
import { getMemoryById } from '../api/Memory';
import { getMotherboardById } from '../api/Motherboard';
import { getHarddiskById } from '../api/Harddisk';
import { getPowersupplyById } from '../api/Powersupply';
import { getCoolingById } from '../api/Cooling';
import { getChassisById } from '../api/Chassis';
import { getDisplayById } from '../api/Display';


const solutions = ref([]);
const isLoggedIn = ref(false);
const router = useRouter();
let cpu = ref<CPUVO>(newCPUVO());
let gpu = ref<GPUVO>(newGPUVO());
let memory = ref<MemoryVO>(newMemoryVO());
let motherboard = ref<MotherboardVO>(newMotherboardVO());
let harddisk = ref<HardDiskVO>(newHardDiskVO());
let powersupply = ref<PowersupplyVO>(newPowersupplyVO());
let cooling = ref<CoolingVO>(newCoolingVO());
let chassis = ref<ChassisVO>(newChassisVO());
let display = ref<DisplayVO>(newDisplayVO());

const fetchSolutions = async () => {
  try {
    const response = await getUserSolutions();
    console.log('Fetched solutions:', response.data.result);
    solutions.value = response.data.result;
    for (let i = 0; i < solutions.value.length; i++) {
      cpu = await getCPUById(solutions.value[i].cpuId);
      gpu = await getGPUById(solutions.value[i].gpuId);
      memory = await getMemoryById(solutions.value[i].memoryId);
      motherboard = await getMotherboardById(solutions.value[i].motherboardId);
      harddisk = await getHarddiskById(solutions.value[i].harddiskId);
      powersupply = await getPowersupplyById(solutions.value[i].powersupplyId);
      cooling = await getCoolingById(solutions.value[i].coolingId);
      chassis = await getChassisById(solutions.value[i].chassisId);
      display = await getDisplayById(solutions.value[i].displayId);
      solutions.value[i].items = [cpu.imageUrl, gpu.imageUrl, memory.imageUrl, motherboard.imageUrl, harddisk.imageUrl, powersupply.imageUrl, cooling.imageUrl, chassis.imageUrl, display.imageUrl];
      console.log(solutions.value[i].items);
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
      <h1 class="title">我的装机方案</h1>
      <div class="solutions-list">
        <div
            v-for="solution in solutions"
            :key="solution.id"
            class="solution-card"
        >
          <div class="solution-header">
            <h2 class="solution-name">{{ solution.name }}</h2>
            <p class="solution-date">{{ solution.createTime }}</p>
          </div>
          <div class="solution-items">
            <img
                v-for="item in solution.items"
                :key="item.id"
                :src="item"
                :alt="item.name"
                class="solution-item-image"
            />
          </div>
          <div class="solution-footer">
            <span class="solution-price">总价: ￥{{ solution.totalPrice }}</span>
            <button class="detail-button" @click="goToSolutionDetail(solution.id)">详情</button>
          </div>
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
  padding: 20px;
  background-color: #f5f5f5;
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

.solution-date {
  font-size: 14px;
  color: #888;
}

.solution-items {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 15px;
  margin-bottom: 15px;
}

.solution-item-image {
  width: 80px;
  height: 80px;
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
</style>
