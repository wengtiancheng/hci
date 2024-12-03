<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { getAllCPU } from "../../api/CPU.ts";
import SearchBox from '../../components/SearchBox.vue';
import router from '../../router';

interface CPU {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  type: 'INTEL' | 'AMD';
}

const cpuList = ref<CPU[]>([]);
const searchQuery = ref('');

const sliderValue = ref([0, 99999]);

const filters = ref({
  type: '',
  sortOrder: 'asc'
});

const sliderChange = () => {
  fetchCPUs();
}

const fetchCPUs = async () => {
  const list = await getAllCPU();
  
  let filteredList = list.filter(cpu => {
    if (cpu.price < sliderValue.value[0]) return false;
    if (cpu.price > sliderValue.value[1]) return false;
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

const filteredCPUs = computed(() => {
  if(!searchQuery.value) return cpuList.value;

  const query = searchQuery.value.toLowerCase();
  return cpuList.value.filter(cpu => cpu.name.toLowerCase().includes(query));
});

onMounted(() => {
  fetchCPUs();
})

// 添加分页相关的状态
const currentPage = ref(1);
const pageSize = ref(10);  // 每页显示10条

// 计算总页数
const totalPages = computed(() => {
  return Math.ceil(filteredCPUs.value.length / pageSize.value);
});

// 计算当前页的数据
const currentPageData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return filteredCPUs.value.slice(start, end);
});

// 页码改变的处理函数
const handlePageChange = (page: number) => {
  currentPage.value = page;
};
</script>

<template>
  <div class="container">
    
    <div class="filters">
      
      <div class="filter-item">
        <label>价格范围</label>
        <vue-slider v-model="sliderValue" :min="0" :max="99999"
                    :tooltip="'active'" :tooltip-placement="['bottom', 'bottom']"
                    @change="sliderChange" ></vue-slider>
      </div>
      
      <div class="filter-item">
        <label>处理器类型</label>
        <select v-model="filters.type" @change="fetchCPUs">
          <option value="">全部</option>
          <option value="INTEL">Intel</option>
          <option value="AMD">AMD</option>
        </select>
      </div>

      <div class="filter-item">
        <label>排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchCPUs">
          <option value="asc">价格从低到高</option>
          <option value="desc">价格从高到低</option>
        </select>
      </div>
    </div>

    <div class="component-list">
      <h2 class="page-title">选择CPU</h2>
      <div class="search-container">
        <SearchBox v-model="searchQuery" />
      </div>
      
      <div class="list-header">
        <div class="header-image">图片</div>
        <div class="header-name">名称</div>
        <div class="header-info">
          <span>类型</span>
        </div>
        <div class="header-price">价格</div>
        <div class="header-action">操作</div>
      </div>
      
      <div v-if="filteredCPUs.length === 0" class="empty-result">
        未找到匹配的配件
      </div>
      
      <div v-else v-for="cpu in currentPageData" 
           :key="cpu.id" 
           class="component-item">
        <img :src="cpu.imageUrl" alt="CPU图片" class="component-image" />
        <div class="component-name">{{ cpu.name }}</div>
        <div class="component-info">
          <span>{{ cpu.type }}</span>
        </div>
        <div class="component-price">￥{{ cpu.price }}</div>
        <button @click="selectCPU(cpu)" class="select-button">选择</button>
      </div>

      <!-- 添加分页控件 -->
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

<style lang="scss" scoped>
@use './select-page.scss';

  

  
</style>