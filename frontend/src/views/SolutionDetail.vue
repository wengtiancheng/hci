<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getSolution, starSolution, SolutionVO , newSolutionVO} from '../api/Solution';

import { getGPUById, newGPUVO, GPUVO} from '../api/GPU';
import {getCPUById, newCPUVO, CPUVO} from '../api/CPU';
import { getMotherboardById, newMotherboardVO, MotherboardVO} from '../api/Motherboard';
import { getMemoryById, newMemoryVO, MemoryVO} from '../api/Memory';
import { getHarddiskById, newHardDiskVO, HardDiskVO } from '../api/Harddisk';
import { getPowersupplyById, newPowersupplyVO, PowersupplyVO} from '../api/Powersupply';
import { getCoolingById, newCoolingVO, CoolingVO} from '../api/Cooling';
import { getChassisById, newChassisVO, ChassisVO} from '../api/Chassis';
import { getDisplayById, newDisplayVO, DisplayVO} from '../api/Display';

import favoriteIcon from '../assets/icons/favorite.svg';
import favoritedIcon from '../assets/icons/favorited.svg';

const route = useRoute();
const solutionId = route.params.id as string;

const solution = ref<SolutionVO>(newSolutionVO());

const cpu = ref<CPUVO>(newCPUVO());
const gpu = ref<GPUVO>(newGPUVO());
const memory = ref<MemoryVO>(newMemoryVO());
const motherboard = ref<MotherboardVO>(newMotherboardVO());
const harddisk = ref<HardDiskVO>(newHardDiskVO());
const powersupply = ref<PowersupplyVO>(newPowersupplyVO());
const cooling = ref<CoolingVO>(newCoolingVO());
const chassis = ref<ChassisVO>(newChassisVO());
const display = ref<DisplayVO>(newDisplayVO());

const router = useRouter();

const isFavorited = ref(false);

const fetchSolutionDetails = async () => {
  solution.value = await getSolution(parseInt(solutionId));

  cpu.value = await getCPUById(solution.value.cpuId);
  gpu.value = await getGPUById(solution.value.gpuId);
  memory.value = await getMemoryById(solution.value.memoryId);
  motherboard.value = await getMotherboardById(solution.value.motherboardId);
  harddisk.value = await getHarddiskById(solution.value.harddiskId);
  powersupply.value = await getPowersupplyById(solution.value.powersupplyId);
  cooling.value = await getCoolingById(solution.value.coolingId);
  chassis.value = await getChassisById(solution.value.chassisId);
  display.value = await getDisplayById(solution.value.displayId);
};

const handleStarSolution = async () => {
  const result = await starSolution(solution.value.id);
  if (result) {
    isFavorited.value = !isFavorited.value;
    console.log('收藏成功');
  } else {
    console.log('收藏失败');
  }
};

const editSolution = () => {
  router.push({ path: '/custom-build', query: { solution: JSON.stringify(solution.value) } });
};

onMounted(() => {
  fetchSolutionDetails();
});
</script>

<template>
  <div class="solution-detail">
    <div class="solution-info">
      <img :src="solution.imageUrl" alt="方案图片" class="solution-image" />
      <h2>{{ solution.name }}</h2>
      <p>{{ solution.description }}</p>
      <p>总价：￥{{ solution.totalPrice }}</p>
      <p>创建时间：{{ new Date(solution.createTime).toLocaleDateString() }}</p>
      <p>收藏次数：{{ solution.saveNum }}</p>
      <img
          :src="isFavorited ? favoritedIcon : favoriteIcon"
          alt="收藏"
          class="favorite-icon"
          @click="handleStarSolution"
      />
      <img
          src="../assets/icons/edit.svg"
          alt="编辑"
          class="edit-icon"
          @click="editSolution"
      />
    </div>

    <div class="parts-info">
      <table>
        <thead>
        <tr>
          <th>配件</th>
          <th>图片</th>
          <th>名称</th>
          <th>价格</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>CPU</td>
          <td><img :src="cpu.imageUrl" alt="CPU" /></td>
          <td>{{ cpu.name }}</td>
          <td>￥{{ cpu.price }}</td>
        </tr>
        <tr>
          <td>显卡</td>
          <td><img :src="gpu.imageUrl" alt="GPU" /></td>
          <td>{{ gpu.name }}</td>
          <td>￥{{ gpu.price }}</td>
        </tr>
        <tr>
          <td>内存</td>
          <td><img :src="memory.imageUrl" alt="Memory" /></td>
          <td>{{ memory.name }}</td>
          <td>￥{{ memory.price }}</td>
        </tr>
        <tr>
          <td>主板</td>
          <td><img :src="motherboard.imageUrl" alt="Motherboard" /></td>
          <td>{{ motherboard.name }}</td>
          <td>￥{{ motherboard.price }}</td>
        </tr>
        <tr>
          <td>硬盘</td>
          <td><img :src="harddisk.imageUrl" alt="Harddisk" /></td>
          <td>{{ harddisk.name }}</td>
          <td>￥{{ harddisk.price }}</td>
        </tr>
        <tr>
          <td>电源</td>
          <td><img :src="powersupply.imageUrl" alt="Powersupply" /></td>
          <td>{{ powersupply.name }}</td>
          <td>￥{{ powersupply.price }}</td>
        </tr>
        <tr>
          <td>散热</td>
          <td><img :src="cooling.imageUrl" alt="Cooling" /></td>
          <td>{{ cooling.name }}</td>
          <td>￥{{ cooling.price }}</td>
        </tr>
        <tr>
          <td>机箱</td>
          <td><img :src="chassis.imageUrl" alt="Chassis" /></td>
          <td>{{ chassis.name }}</td>
          <td>￥{{ chassis.price }}</td>
        </tr>
        <tr>
          <td>显示器</td>
          <td><img :src="display.imageUrl" alt="Display" /></td>
          <td>{{ display.name }}</td>
          <td>￥{{ display.price }}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
/* Ensure the root container takes the full width and height */
:root, body {
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
  overflow-x: hidden; /* Prevent horizontal scrolling */
}

.solution-detail {
  display: flex;
  gap: 30px;
  padding-top: 80px; /* Add padding to the top to avoid being covered by the header */
  width: 90vw; /* Ensure it takes the full viewport width */
  margin: 0; /* Remove any default margin */
  padding-left: 0; /* Ensure no padding on the left */
}

.solution-info {
  flex: 0 0 20%; /* Set a fixed width of 25% for the left container */
  background-color: #f4f4f4;
}

.solution-image {
  object-fit: cover;
}

.favorite-icon, .edit-icon {
  width: 24px;
  height: 24px;
  cursor: pointer;
  margin-top: 10px;
}

.edit-icon {
  margin-left: 30px; /* Add margin to the left of the edit icon */
}

.parts-info {
  flex: 1; /* Take the remaining width */
  padding: 20px;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 10px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f4f4f4;
}

img {
  width: 100px;
  height: 100px;
  object-fit: contain;
}

button {
  padding: 10px 20px;
  background-color: #2c3e50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 10px;
}

button:hover {
  background-color: #34495e;
}
</style>