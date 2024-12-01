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

      <div class="filter-item">
        <label>显卡类型</label>
        <select v-model="filters.type" @change="fetchGPUs">
          <option value="">全部</option>
          <option value="INTEL">Intel</option>
          <option value="AMD">AMD</option>
        </select>
      </div>

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

      <div class="filter-item">
        <label>排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchGPUs">
          <option value="asc">价格从低到高</option>
          <option value="desc">价格从高到低</option>
        </select>
      </div>
    </div>

    <div class="component-list">
      <div class="search-container">
        <SearchBox v-model="searchQuery" />
      </div>

      <div v-if="filteredGPUs.length === 0" class="empty-result">
        未找到匹配的配件
      </div>
      
      <div v-else v-for="gpu in filteredGPUs" 
           :key="gpu.id" 
           class="component-item">
        <img :src="gpu.imageUrl" alt="显卡图片" class="component-image" />
        <div class="component-name">{{ gpu.name }}</div>
        <div class="component-info">
          <span>{{ getBrandLabel(gpu.brand) }}</span>
          <span>{{ gpu.type }}</span>
        </div>
        <div class="component-price">￥{{ gpu.price }}</div>
        <button @click="selectGPU(gpu)" class="select-button">选择</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { getAllGPU } from "../../api/GPU.ts";
import router from '../../router';
import SearchBox from '../../components/SearchBox.vue';

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

const searchQuery = ref('');

const filteredGPUs = computed(() => {
  if(!searchQuery.value) return gpuList.value;
  
  const query = searchQuery.value.toLowerCase();
  return gpuList.value.filter(gpu => gpu.name.toLowerCase().includes(query));
});

onMounted(() => {
  fetchGPUs();
})
</script>

<style lang="scss" scoped>
@use './select-page.scss';
</style>