<template>
  <div class="container">
    <div class="filters">
      <h3>筛选条件</h3>
      
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

      <div class="filter-item">
        <label>硬盘类型</label>
        <select v-model="filters.type" @change="fetchHarddisks">
          <option value="">全部</option>
          <option value="SSD">固态硬盘</option>
          <option value="HDD">机械硬盘</option>
        </select>
      </div>

      <div class="filter-item">
        <label>品牌</label>
        <select v-model="filters.brand" @change="fetchHarddisks">
          <option value="">全部</option>
          <option value="SAMSUNG">三星</option>
          <option value="KINGSTON">金士顿</option>
          <option value="WD">WD</option>
        </select>
      </div>

      <div class="filter-item">
        <label>排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchHarddisks">
          <option value="asc">价格从低到高</option>
          <option value="desc">价格从高到低</option>
        </select>
      </div>
    </div>

    <div class="component-list">
      <div class="search-container">
        <SearchBox v-model="searchQuery" />
      </div>
      <div v-if="filteredHarddisks.length === 0" class="empty-result">
        未找到匹配的配件
      </div>
      <div v-else v-for="harddisk in filteredHarddisks" 
           :key="harddisk.id" 
           class="component-item">
        <img :src="harddisk.imageUrl" alt="硬盘图片" class="component-image" />
        <div class="component-name">{{ harddisk.name }}</div>
        <div class="component-info">
          <span>{{ getBrandLabel(harddisk.brand) }}</span>
          <span>{{ getTypeLabel(harddisk.type) }}</span>
        </div>
        <div class="component-price">￥{{ harddisk.price }}</div>
        <button @click="selectHarddisk(harddisk)" class="select-button">选择</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { getAllHarddisk } from "../../api/Harddisk.ts";
import SearchBox from '../../components/SearchBox.vue';
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
const searchQuery = ref('');

const filteredHarddisks = computed(() => {
  if(!searchQuery.value) return harddiskList.value;
  
  const query = searchQuery.value.toLowerCase();
  return harddiskList.value.filter(harddisk => harddisk.name.toLowerCase().includes(query));
});

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

<style lang="scss" scoped>
@use './select-page.scss';
</style>