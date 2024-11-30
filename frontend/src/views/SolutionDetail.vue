<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getSolution, starSolution, SolutionVO , newSolutionVO} from '../api/Solution';
import Header from '../components/Header.vue';

import { getGPUById, newGPUVO, GPUVO} from '../api/GPU';
import {getCPUById, newCPUVO, CPUVO} from '../api/CPU';
import { getMotherboardById, newMotherboardVO, MotherboardVO} from '../api/Motherboard';
import { getMemoryById, newMemoryVO, MemoryVO} from '../api/Memory';
import { getHarddiskById, newHardDiskVO, HardDiskVO } from '../api/Harddisk';
import { getPowersupplyById, newPowersupplyVO, PowersupplyVO } from '../api/Powersupply';
import { getCoolingById, newCoolingVO, CoolingVO } from '../api/Cooling';
import { getChassisById, newChassisVO, ChassisVO } from '../api/Chassis';
import { getDisplayById, newDisplayVO, DisplayVO } from '../api/Display';


// 获取路由参数
const route = useRoute();
const solutionId = route.params.id as string;

// 方案详情
const solution = ref<SolutionVO>(newSolutionVO());

// 配件信息
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

// 获取装机方案和配件信息
const fetchSolutionDetails = async () => {
  // 获取装机方案
  solution.value = await getSolution(parseInt(solutionId));

  // 获取各个配件信息
  cpu.value = await getCPUById(solution.value.cpuId);
  gpu.value = await getGPUById(solution.value.gpuId);
  memory.value = await getMemoryById(solution.value.memoryId);
  motherboard.value = await getMotherboardById(solution.value.motherboardId);
  harddisk.value = await getHarddiskById(solution.value.harddiskId); // 假设有此接口
  powersupply.value = await getPowersupplyById(solution.value.powersupplyId); // 假设有此接口
  cooling.value = await getCoolingById(solution.value.coolingId); // 假设有此接口
  chassis.value = await getChassisById(solution.value.chassisId); // 假设有此接口
  display.value = await getDisplayById(solution.value.displayId); // 假设有此接口
};

// 收藏方案
const handleStarSolution = async () => {
  const result = await starSolution(solution.value.id);
  console.log(result ? '收藏成功' : '收藏失败');
};

const editSolution = () => {
  router.push({ path: '/custom-build', query: { solution: JSON.stringify(solution.value) } });
};

onMounted(() => {
  fetchSolutionDetails();
});
</script>

<template>
  <Header />
  <div class="solution-detail">
    <!-- 左侧方案信息 -->
    <div class="solution-info">
      <img :src="solution.imageUrl" alt="方案图片" class="solution-image" />
      <h2>{{ solution.name }}</h2>
      <p>{{ solution.description }}</p>
      <p>总价：￥{{ solution.totalPrice }}</p>
      <p>创建时间：{{ new Date(solution.createTime).toLocaleDateString() }}</p>
      <p>收藏次数：{{ solution.saveNum }}</p>
      <button @click="handleStarSolution">收藏</button>
      <button @click="editSolution">编辑</button>
    </div>

    <!-- 右侧配件信息 -->
    <div class="parts-info">
      <div class="part-card">
        <h4>CPU</h4>
        <img :src="cpu.imageUrl" alt="CPU" />
        <p>{{ cpu.name }}</p>
        <p>价格：￥{{ cpu.price}}</p>
      </div>
      <div class="part-card">
        <h4>显卡</h4>
        <img :src="gpu.imageUrl" alt="GPU" />
        <p>{{ gpu.name }}</p>
        <p>价格：￥{{ gpu.price }}</p>
      </div>
      <div class="part-card">
        <h4>内存</h4>
        <img :src="memory.imageUrl" alt="Memory" />
        <p>{{ memory.name }}</p>
        <p>价格：￥{{ memory.price }}</p>
      </div>
      <div class="part-card">
        <h4>主板</h4>
        <img :src="motherboard.imageUrl" alt="Motherboard" />
        <p>{{ motherboard.name }}</p>
        <p>价格：￥{{ motherboard.price }}</p>
      </div>
      <div class="part-card">
        <h4>硬盘</h4>
        <img :src="harddisk.imageUrl" alt="Harddisk" />
        <p>{{ harddisk.name }}</p>
        <p>价格：￥{{ harddisk.price }}</p>
      </div>
      <div class="part-card">
        <h4>电源</h4>
        <img :src="powersupply.imageUrl" alt="Powersupply" />
        <p>{{ powersupply.name }}</p>
        <p>价格：￥{{ powersupply.price }}</p>
      </div>
      <div class="part-card">
        <h4>散热</h4>
        <img :src="cooling.imageUrl" alt="Cooling" />
        <p>{{ cooling.name }}</p>
        <p>价格：￥{{ cooling.price }}</p>
      </div>
      <div class="part-card">
        <h4>机箱</h4>
        <img :src="chassis.imageUrl" alt="Chassis" />
        <p>{{ chassis.name }}</p>
        <p>价格：￥{{ chassis.price }}</p>
      </div>
      <div class="part-card">
        <h4>显示器</h4>
        <img :src="display.imageUrl" alt="Display" />
        <p>{{ display.name }}</p>
        <p>价格：￥{{ display.price }}</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.solution-detail {
  display: flex;
  gap: 30px;
}

.solution-info {
  flex: 1;
  padding: 20px;
  background-color: #f4f4f4;
}

.solution-image {
  width: 100%;
  max-height: 300px;
  object-fit: cover;
}

.parts-info {
  flex: 2;
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.part-card {
  width: 220px;
  background-color: #ffffff;
  padding: 10px;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.part-card img {
  width: 100%;
  height: 150px;
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
