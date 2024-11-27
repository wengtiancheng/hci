<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getAllCPU } from "../../api/CPU.ts";
import router from '../../router';

interface CPU {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  type: 'INTEL' | 'AMD';
}

const cpuList = ref<CPU[]>([]);

const filters = ref({
  minPrice: null as number | null,
  maxPrice: null as number | null,
  type: '',
  sortOrder: 'asc'
});

const fetchCPUs = async () => {
  const list = await getAllCPU();
  
  // 应用筛选条件
  let filteredList = list.filter(cpu => {
    if (filters.value.minPrice && cpu.price < filters.value.minPrice) return false;
    if (filters.value.maxPrice && cpu.price > filters.value.maxPrice) return false;
    if (filters.value.type && cpu.type !== filters.value.type) return false;
    return true;
  });

  // 排序
  filteredList.sort((a, b) => {
    const factor = filters.value.sortOrder === 'asc' ? 1 : -1;
    return (a.price - b.price) * factor;
  });

  cpuList.value = filteredList;
}

const selectCPU = (cpu: CPU) => {
  sessionStorage.setItem('cpu', String(cpu.id));
  sessionStorage.setItem('showSuccessMessage', JSON.stringify({
    type: 'CPU',
    name: cpu.name,
    action: 'select'
  }));
  router.push('/custom-build');
}

onMounted(() => {
  fetchCPUs();
})
</script>

<template>
  <div class="container">
    <div class="filters">
      <h3>筛选条件</h3>

      <!-- 价格区间筛选 -->
      <div class="filter-item">
        <label>价格区间</label>
        <div class="price-range">
          <input 
            type="number" 
            v-model="filters.minPrice" 
            placeholder="最低价" 
            @input="fetchCPUs"
          />
          <span>-</span>
          <input 
            type="number" 
            v-model="filters.maxPrice" 
            placeholder="最高价" 
            @input="fetchCPUs"
          />
        </div>
      </div>

      <!-- CPU类型筛选 -->
      <div class="filter-item">
        <label>处理器类型</label>
        <select v-model="filters.type" @change="fetchCPUs">
          <option value="">全部</option>
          <option value="INTEL">Intel</option>
          <option value="AMD">AMD</option>
        </select>
      </div>

      <!-- 排序方式 -->
      <div class="filter-item">
        <label>排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchCPUs">
          <option value="asc">价格从低到高</option>
          <option value="desc">价格从高到低</option>
        </select>
      </div>
    </div>

    <div class="cpu-list">
      <div v-for="cpu in cpuList" 
           :key="cpu.id" 
           class="cpu-item">
        <img :src="cpu.imageUrl" alt="CPU图片" class="cpu-image" />
        <div class="cpu-name">{{ cpu.name }}</div>
        <div class="cpu-type">{{ cpu.type === 'INTEL' ? 'Intel' : 'AMD' }}</div>
        <div class="cpu-price">￥{{ cpu.price }}</div>
        <button @click="selectCPU(cpu)" class="select-button">选择</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 复用之前的样式，只需修改类名 */
.container {
  display: flex;
  margin-top: 60px;
  height: calc(100vh - 140px);
  overflow: hidden;
}

.filters {
  width: 250px;
  padding: 20px;
  background-color: #f8f9fa;
  border-right: 1px solid #dee2e6;
  position: fixed;
  left: 0;
  top: 60px;
  height: calc(100vh - 60px);
  overflow-y: auto;
}

.filter-item {
  margin-bottom: 20px;
}

.filter-item label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
  color: #495057;
}

.filter-item select,
.filter-item input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ced4da;
  border-radius: 4px;
}

.price-range {
  display: flex;
  align-items: center;
  gap: 8px;
}

.price-range input {
  width: calc(50% - 10px);
}

.cpu-list {
  margin-left: 250px;
  padding: 20px;
  width: calc(100% - 250px);
  height: calc(100vh - 60px);
  overflow-y: auto;
  box-sizing: border-box;
}

.cpu-item {
  display: flex;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid #eaeaea;
  gap: 20px;
}

.cpu-image {
  width: 80px;
  height: 80px;
  object-fit: cover;
  flex-shrink: 0;
}

.cpu-name {
  width: 200px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  flex-shrink: 0;
  padding-left: 20px;
}

.cpu-type {
  width: 80px;
  text-align: center;
  flex-shrink: 0;
}

.cpu-price {
  width: 80px;
  color: #ff4d4f;
  font-weight: bold;
  text-align: center;
  flex-shrink: 0;
}

.select-button {
  width: 60px;
  padding: 6px 12px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
  flex-shrink: 0;
}

.select-button:hover {
  background-color: #0056b3;
}
</style>