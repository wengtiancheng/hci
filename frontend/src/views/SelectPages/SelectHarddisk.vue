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
            @input="fetchHarddisks"
          />
          <span>-</span>
          <input 
            type="number" 
            v-model="filters.maxPrice" 
            placeholder="最高价" 
            @input="fetchHarddisks"
          />
        </div>
      </div>

      <!-- 硬盘类型筛选 -->
      <div class="filter-item">
        <label>硬盘类型</label>
        <select v-model="filters.type" @change="fetchHarddisks">
          <option value="">全部</option>
          <option value="SSD">固态硬盘</option>
          <option value="HDD">机械硬盘</option>
        </select>
      </div>

      <!-- 品牌筛选 -->
      <div class="filter-item">
        <label>品牌</label>
        <select v-model="filters.brand" @change="fetchHarddisks">
          <option value="">全部</option>
          <option value="SAMSUNG">三星</option>
          <option value="KINGSTON">金士顿</option>
          <option value="WD">WD</option>
        </select>
      </div>

      <!-- 排序方式 -->
      <div class="filter-item">
        <label>排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchHarddisks">
          <option value="asc">价格从低到高</option>
          <option value="desc">价格从高到低</option>
        </select>
      </div>
    </div>

    <div class="harddisk-list">
      <div v-for="harddisk in harddiskList" 
           :key="harddisk.id" 
           class="harddisk-item">
        <img :src="harddisk.imageUrl" alt="硬盘图片" class="harddisk-image" />
        <div class="harddisk-name">{{ harddisk.name }}</div>
        <div class="harddisk-info">
          <span>{{ getBrandLabel(harddisk.brand) }}</span>
          <span>{{ getTypeLabel(harddisk.type) }}</span>
        </div>
        <div class="harddisk-price">￥{{ harddisk.price }}</div>
        <button @click="selectHarddisk(harddisk)" class="select-button">选择</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getAllHarddisk } from "../../api/Harddisk.ts";
import router from '../../router';

interface Harddisk {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  type: 'SSD' | 'HDD';
  brand: 'SAMSUNG' | 'KINGSTON' | 'WD';
}

const harddiskList = ref<Harddisk[]>([]);

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
    'SAMSUNG': '三星',
    'KINGSTON': '金士顿',
    'WD': 'WD'
  };
  return brandMap[brand] || brand;
};

// 类型名称转换
const getTypeLabel = (type: string) => {
  const typeMap = {
    'SSD': '固态硬盘',
    'HDD': '机械硬盘'
  };
  return typeMap[type] || type;
};

const fetchHarddisks = async () => {
  const list = await getAllHarddisk();
  
  // 应用筛选条件
  let filteredList = list.filter(harddisk => {
    if (filters.value.minPrice && harddisk.price < filters.value.minPrice) return false;
    if (filters.value.maxPrice && harddisk.price > filters.value.maxPrice) return false;
    if (filters.value.type && harddisk.type !== filters.value.type) return false;
    if (filters.value.brand && harddisk.brand !== filters.value.brand) return false;
    return true;
  });

  // 排序
  filteredList.sort((a, b) => {
    const factor = filters.value.sortOrder === 'asc' ? 1 : -1;
    return (a.price - b.price) * factor;
  });

  harddiskList.value = filteredList;
}

const selectHarddisk = (harddisk: Harddisk) => {
  sessionStorage.setItem('harddisk', String(harddisk.id));
  sessionStorage.setItem('showSuccessMessage', JSON.stringify({
    type: '硬盘',
    name: harddisk.name,
    action: 'select'
  }));
  router.push('/custom-build');
}

onMounted(() => {
  fetchHarddisks();
})
</script>

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

.harddisk-list {
  margin-left: 250px;
  padding: 20px;
  width: calc(100% - 250px);
  height: calc(100vh - 60px);
  overflow-y: auto;
  box-sizing: border-box;
}

.harddisk-item {
  display: flex;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid #eaeaea;
  gap: 20px;
}

.harddisk-image {
  width: 80px;
  height: 80px;
  object-fit: cover;
  flex-shrink: 0;
}

.harddisk-name {
  width: 200px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  flex-shrink: 0;
  padding-left: 20px;
}

.harddisk-info {
  width: 160px;
  display: flex;
  justify-content: space-between;
  flex-shrink: 0;
}

.harddisk-info span {
  padding: 0 10px;
}

.harddisk-price {
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