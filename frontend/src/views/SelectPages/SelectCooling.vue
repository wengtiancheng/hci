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
            @input="fetchCooling"
          />
          <span>-</span>
          <input 
            type="number" 
            v-model="filters.maxPrice" 
            placeholder="最高价" 
            @input="fetchCooling"
          />
        </div>
      </div>

      <!-- 品牌筛选 -->
      <div class="filter-item">
        <label>品牌</label>
        <select v-model="filters.brand" @change="fetchCooling">
          <option value="">全部</option>
          <option value="VALKYRIE">瓦尔基里</option>
          <option value="Thermalright">利民</option>
        </select>
      </div>

      <!-- 排序方式 -->
      <div class="filter-item">
        <label>排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchCooling">
          <option value="asc">价格从低到高</option>
          <option value="desc">价格从高到低</option>
        </select>
      </div>
    </div>

    <div class="cooling-list">
      <div v-for="cooling in coolingList" 
           :key="cooling.id" 
           class="cooling-item">
        <img :src="cooling.imageUrl" alt="水冷图片" class="cooling-image" />
        <div class="cooling-name">{{ cooling.name }}</div>
        <div class="cooling-brand">{{ cooling.brand }}</div>
        <div class="cooling-price">￥{{ cooling.price }}</div>
        <button @click="selectCooling(cooling)" class="select-button">选择</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getAllCooling } from "../../api/Cooling.ts";
import router from '../../router';

interface Cooling {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  brand: string;
}

const coolingList = ref<Cooling[]>([]);

const filters = ref({
  minPrice: null as number | null,
  maxPrice: null as number | null,
  brand: '',
  sortOrder: 'asc'
});

const fetchCooling = async () => {
  const list = await getAllCooling();
  
  // 价格筛选
  let filteredList = list.filter(cooling => {
    if (filters.value.minPrice && cooling.price < filters.value.minPrice) return false;
    if (filters.value.maxPrice && cooling.price > filters.value.maxPrice) return false;
    if (filters.value.brand && cooling.brand !== filters.value.brand) return false;
    return true;
  });

  // 排序
  filteredList.sort((a, b) => {
    const factor = filters.value.sortOrder === 'asc' ? 1 : -1;
    return (a.price - b.price) * factor;
  });

  coolingList.value = filteredList;
}

const selectCooling = (cooling: Cooling) => {
  sessionStorage.setItem('cooling', String(cooling.id));
  sessionStorage.setItem('showSuccessMessage', JSON.stringify({
    type: '水冷',
    name: cooling.name,
    action: 'select'
  }));
  router.push('/custom-build');
}

onMounted(() => {
  fetchCooling();
})
</script>

<style scoped>
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

.cooling-list {
  margin-left: 250px;
  padding: 20px;
  width: calc(100% - 250px);
  height: calc(100vh - 60px);
  overflow-y: auto;
  box-sizing: border-box;
}

.cooling-item {
  display: flex;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid #eaeaea;
  gap: 20px;
}

.cooling-image {
  width: 80px;
  height: 80px;
  object-fit: cover;
  flex-shrink: 0;
}

.cooling-name {
  width: 200px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  flex-shrink: 0;
  padding-left: 20px;
}

.cooling-brand {
  width: 80px;
  text-align: center;
  flex-shrink: 0;
}

.cooling-price {
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