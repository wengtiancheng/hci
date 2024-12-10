<template>
  <div class="container">
    
    <div class="filters">
      <h2 class="page-title">选择显卡</h2>
      <div class="search-container">
        <SearchBox v-model="searchQuery" />
      </div>
      
      <div class="filter-item">
        <label>价格范围</label>
        <vue-slider v-model="sliderValue":min="0" :max="99999"
                    :tooltip="'active'" :tooltip-placement="['bottom', 'bottom']"
                    @change="sliderChange" ></vue-slider>
      </div>

      <div class="filter-item">
        <label>显卡类型</label>
        <select v-model="filters.type" @change="fetchGPUs">
          <option value="">全部</option>
          <option value="NVIDIA">NVIDIA</option>
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
      <div class="list-header">
        <div class="header-image">图片</div>
        <div class="header-name">名称</div>
        <div class="header-info">
          <span>显卡类型</span>
          <span>品牌</span>
        </div>
        <div class="header-price">价格</div>
        <div class="header-action">操作</div>
      </div>

      <div v-if="filteredGPUs.length === 0" class="empty-result">
        未找到匹配的配件
      </div>
      
      <div v-else v-for="gpu in currentPageData" 
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

      <div class="pagination">
        <button 
          :disabled="currentPage === 1"
          @click="handlePageChange(currentPage - 1)"
          class="page-button"
        >
          上一页
        </button>
        
        <span class="page-info">
          {{ currentPage }} / {{ totalPages }}
        </span>
        
        <button 
          :disabled="currentPage === totalPages"
          @click="handlePageChange(currentPage + 1)"
          class="page-button"
        >
          下一页
        </button>
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
  type: 'NVIDIA' | 'AMD';
  brand: 'ASUS' | 'MSI' | 'GIGABYTE' | 'COLORFUL';
}

const gpuList = ref<GPU[]>([]);

const sliderValue = ref([0, 99999]);

const filters = ref({
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

const sliderChange = () => {
  fetchGPUs();
}

const fetchGPUs = async () => {
  const list = await getAllGPU();
  
  let filteredList = list.filter(gpu => {
    if (gpu.price < sliderValue.value[0]) return false;
    if (gpu.price > sliderValue.value[1]) return false;
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

// 添加分页相关的状态
const currentPage = ref(1);
const pageSize = ref(13);  // 每页显示10条

// 计算总页数
const totalPages = computed(() => {
  return Math.ceil(filteredGPUs.value.length / pageSize.value);
});

// 计算当前页的数据
const currentPageData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return filteredGPUs.value.slice(start, end);
});

// 页码改变的处理函数
const handlePageChange = (page: number) => {
  currentPage.value = page;
};

onMounted(() => {
  fetchGPUs();
})
</script>

<style lang="scss" scoped>
@use './select-page.scss';
</style>