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
            @input="fetchDisplays"
          />
          <span>-</span>
          <input 
            type="number" 
            v-model="filters.maxPrice" 
            placeholder="最高价" 
            @input="fetchDisplays"
          />
        </div>
      </div>

      <!-- 品牌筛选 -->
      <div class="filter-item">
        <label>品牌</label>
        <select v-model="filters.brand" @change="fetchDisplays">
          <option value="">全部</option>
          <option value="HKC">HKC</option>
          <option value="ASUS">华硕</option>
          <option value="AOC">AOC</option>
        </select>
      </div>

      <!-- 排序方式 -->
      <div class="filter-item">
        <label>排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchDisplays">
          <option value="asc">价格从低到高</option>
          <option value="desc">价格从高到低</option>
        </select>
      </div>
    </div>

    <div class="display-list">
      <div v-for="display in displayList" 
           :key="display.id" 
           class="display-item">
        <img :src="display.imageUrl" alt="显示器图片" class="display-image" />
        <div class="display-name">{{ display.name }}</div>
        <div class="display-brand">{{ getBrandLabel(display.brand) }}</div>
        <div class="display-price">￥{{ display.price }}</div>
        <button @click="selectDisplay(display)" class="select-button">选择</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getAllDisplay } from "../../api/Display.ts";
import router from '../../router';

interface Display {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  brand: 'HKC' | 'ASUS' | 'AOC';
}

const displayList = ref<Display[]>([]);

const filters = ref({
  minPrice: null as number | null,
  maxPrice: null as number | null,
  brand: '',
  sortOrder: 'asc'
});

// 品牌名称转换
const getBrandLabel = (brand: string) => {
  const brandMap = {
    'HKC': 'HKC',
    'ASUS': '华硕',
    'AOC': 'AOC'
  };
  return brandMap[brand] || brand;
};

const fetchDisplays = async () => {
  const list = await getAllDisplay();
  
  // 应用筛选条件
  let filteredList = list.filter(display => {
    if (filters.value.minPrice && display.price < filters.value.minPrice) return false;
    if (filters.value.maxPrice && display.price > filters.value.maxPrice) return false;
    if (filters.value.brand && display.brand !== filters.value.brand) return false;
    return true;
  });

  // 排序
  filteredList.sort((a, b) => {
    const factor = filters.value.sortOrder === 'asc' ? 1 : -1;
    return (a.price - b.price) * factor;
  });

  displayList.value = filteredList;
}

const selectDisplay = (display: Display) => {
  sessionStorage.setItem('display', String(display.id));
  sessionStorage.setItem('showSuccessMessage', JSON.stringify({
    type: '显示器',
    name: display.name,
    action: 'select'
  }));
  router.push('/custom-build');
}

onMounted(() => {
  fetchDisplays();
})
</script>

<style scoped>
/* 复用基础样式 */
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

.display-list {
  margin-left: 250px;
  padding: 20px;
  width: calc(100% - 250px);
  height: calc(100vh - 60px);
  overflow-y: auto;
  box-sizing: border-box;
}

.display-item {
  display: flex;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid #eaeaea;
  gap: 20px;
}

.display-image {
  width: 80px;
  height: 80px;
  object-fit: cover;
  flex-shrink: 0;
}

.display-name {
  width: 200px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  flex-shrink: 0;
  padding-left: 20px;
}

.display-brand {
  width: 80px;
  text-align: center;
  flex-shrink: 0;
}

.display-price {
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