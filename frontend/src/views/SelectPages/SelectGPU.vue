<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getAllGPU } from "../../api/GPU.ts";
import router from '../../router';

interface GPU {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  type: 'INTEL' | 'AMD';
  brand: 'ASUS' | 'MSI' | 'GIGABYTE' | 'COLORFUL';
}

const gpuList = ref<GPU[]>([]);

const filters = ref({
  minPrice: null as number | null,
  maxPrice: null as number | null,
  type: '',
  brand: '',
  sortOrder: 'asc'
});

// 品牌名称转换
const getBrandLabel = (brand: string) => {
  const brandMap = {
    'ASUS': '华硕',
    'MSI': '微星',
    'GIGABYTE': '技嘉',
    'COLORFUL': '七彩虹'
  };
  return brandMap[brand] || brand;
};

const fetchGPUs = async () => {
  const list = await getAllGPU();
  
  // 应用筛选条件
  let filteredList = list.filter(gpu => {
    if (filters.value.minPrice && gpu.price < filters.value.minPrice) return false;
    if (filters.value.maxPrice && gpu.price > filters.value.maxPrice) return false;
    if (filters.value.type && gpu.type !== filters.value.type) return false;
    if (filters.value.brand && gpu.brand !== filters.value.brand) return false;
    return true;
  });

  // 排序
  filteredList.sort((a, b) => {
    const factor = filters.value.sortOrder === 'asc' ? 1 : -1;
    return (a.price - b.price) * factor;
  });

  gpuList.value = filteredList;
}

const selectGPU = (gpu: GPU) => {
  sessionStorage.setItem('gpu', String(gpu.id));
  sessionStorage.setItem('showSuccessMessage', JSON.stringify({
    type: '显卡',
    name: gpu.name,
    action: 'select'
  }));
  router.push('/custom-build');
}

onMounted(() => {
  fetchGPUs();
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
            @input="fetchGPUs"
          />
          <span>-</span>
          <input 
            type="number" 
            v-model="filters.maxPrice" 
            placeholder="最高价" 
            @input="fetchGPUs"
          />
        </div>
      </div>

      <!-- GPU类型筛选 -->
      <div class="filter-item">
        <label>显卡类型</label>
        <select v-model="filters.type" @change="fetchGPUs">
          <option value="">全部</option>
          <option value="INTEL">Intel</option>
          <option value="AMD">AMD</option>
        </select>
      </div>

      <!-- 品牌筛选 -->
      <div class="filter-item">
        <label>品牌</label>
        <select v-model="filters.brand" @change="fetchGPUs">
          <option value="">全部</option>
          <option value="ASUS">华硕</option>
          <option value="MSI">微星</option>
          <option value="GIGABYTE">技嘉</option>
          <option value="COLORFUL">七彩虹</option>
        </select>
      </div>

      <!-- 排序方式 -->
      <div class="filter-item">
        <label>排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchGPUs">
          <option value="asc">价格从低到高</option>
          <option value="desc">价格从高到低</option>
        </select>
      </div>
    </div>

    <div class="gpu-list">
      <div v-for="gpu in gpuList" 
           :key="gpu.id" 
           class="gpu-item">
        <img :src="gpu.imageUrl" alt="显卡图片" class="gpu-image" />
        <div class="gpu-name">{{ gpu.name }}</div>
        <div class="gpu-info">
          <span>{{ getBrandLabel(gpu.brand) }}</span>
          <span>{{ gpu.type }}</span>
        </div>
        <div class="gpu-price">￥{{ gpu.price }}</div>
        <button @click="selectGPU(gpu)" class="select-button">选择</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 基础样式保持不变 */
.container {
  display: flex;
  margin-top: 60px;
  height: calc(100vh - 60px);
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

/* ... 其他基础样式保持不变 ... */

.gpu-list {
  margin-left: 250px;
  padding: 20px;
  width: calc(100% - 250px);
  height: calc(100vh - 60px);
  overflow-y: auto;
  box-sizing: border-box;
}

.gpu-item {
  display: flex;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid #eaeaea;
  gap: 20px;
}

.gpu-image {
  width: 80px;
  height: 80px;
  object-fit: cover;
  flex-shrink: 0;
}

.gpu-name {
  width: 200px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  flex-shrink: 0;
  padding-left: 20px;
}

.gpu-info {
  width: 160px;
  display: flex;
  justify-content: space-between;
  flex-shrink: 0;
}

.gpu-info span {
  padding: 0 10px;
}

.gpu-price {
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