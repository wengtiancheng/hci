<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getAllMemories } from "../../api/Memory.ts";
import router from '../../router';

interface Memory {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  type: 'DDR3' | 'DDR4' | 'DDR5';
  brand: 'KINGBANK' | 'KINGSTON' | 'PREDATOR' | 'Colorful';
}

const memoryList = ref<Memory[]>([]);

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
    'KINGBANK': '金百达',
    'KINGSTON': '金士顿',
    'PREDATOR': '宏基',
    'Colorful': '七彩虹'
  };
  return brandMap[brand] || brand;
};

const fetchMemories = async () => {
  const list = await getAllMemories();
  
  // 应用筛选条件
  let filteredList = list.filter(memory => {
    if (filters.value.minPrice && memory.price < filters.value.minPrice) return false;
    if (filters.value.maxPrice && memory.price > filters.value.maxPrice) return false;
    if (filters.value.type && memory.type !== filters.value.type) return false;
    if (filters.value.brand && memory.brand !== filters.value.brand) return false;
    return true;
  });

  // 排序
  filteredList.sort((a, b) => {
    const factor = filters.value.sortOrder === 'asc' ? 1 : -1;
    return (a.price - b.price) * factor;
  });

  memoryList.value = filteredList;
}

const selectMemory = (memory: Memory) => {
  sessionStorage.setItem('memory', String(memory.id));
  sessionStorage.setItem('showSuccessMessage', JSON.stringify({
    type: '内存',
    name: memory.name,
    action: 'select'
  }));
  router.push('/custom-build');
}

onMounted(() => {
  fetchMemories();
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
            @input="fetchMemories"
          />
          <span>-</span>
          <input 
            type="number" 
            v-model="filters.maxPrice" 
            placeholder="最高价" 
            @input="fetchMemories"
          />
        </div>
      </div>

      <!-- 内存类型筛选 -->
      <div class="filter-item">
        <label>内存类型</label>
        <select v-model="filters.type" @change="fetchMemories">
          <option value="">全部</option>
          <option value="DDR3">DDR3</option>
          <option value="DDR4">DDR4</option>
          <option value="DDR5">DDR5</option>
        </select>
      </div>

      <!-- 品牌筛选 -->
      <div class="filter-item">
        <label>品牌</label>
        <select v-model="filters.brand" @change="fetchMemories">
          <option value="">全部</option>
          <option value="KINGBANK">金百达</option>
          <option value="KINGSTON">金士顿</option>
          <option value="PREDATOR">宏基</option>
          <option value="Colorful">七彩虹</option>
        </select>
      </div>

      <!-- 排序方式 -->
      <div class="filter-item">
        <label>排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchMemories">
          <option value="asc">价格从低到高</option>
          <option value="desc">价格从高到低</option>
        </select>
      </div>
    </div>

    <div class="memory-list">
      <div v-for="memory in memoryList" 
           :key="memory.id" 
           class="memory-item">
        <img :src="memory.imageUrl" alt="内存图片" class="memory-image" />
        <div class="memory-name">{{ memory.name }}</div>
        <div class="memory-info">
          <span>{{ getBrandLabel(memory.brand) }}</span>
          <span>{{ memory.type }}</span>
        </div>
        <div class="memory-price">￥{{ memory.price }}</div>
        <button @click="selectMemory(memory)" class="select-button">选择</button>
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

.memory-list {
  margin-left: 250px;
  padding: 20px;
  width: calc(100% - 250px);
  height: calc(100vh - 60px);
  overflow-y: auto;
  box-sizing: border-box;
}

.memory-item {
  display: flex;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid #eaeaea;
  gap: 20px;
}

.memory-image {
  width: 80px;
  height: 80px;
  object-fit: cover;
  flex-shrink: 0;
}

.memory-name {
  width: 200px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  flex-shrink: 0;
  padding-left: 20px;
}

.memory-info {
  width: 160px;
  display: flex;
  justify-content: space-between;
  flex-shrink: 0;
}

.memory-info span {
  padding: 0 10px;
}

.memory-price {
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