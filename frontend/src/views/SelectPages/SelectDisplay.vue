<template>
  <div class="container">
    
    <div class="filters">
      <h2 class="page-title">选择显示器</h2>
      <div class="search-container">
        <SearchBox v-model="searchQuery" />
      </div>

      <div class="filter-item">
        <label style="padding-top: 30px">价格范围</label>
        <vue-slider v-model="sliderValue":min="0" :max="99999"
                    :tooltip="'active'" :tooltip-placement="['bottom', 'bottom']"
                    @change="sliderChange" ></vue-slider>
      </div>

      <div class="filter-item">
        <label style="padding-top: 30px">品牌</label>
        <select v-model="filters.brand" @change="fetchDisplays">
          <option value="">全部</option>
          <option value="HKC">HKC</option>
          <option value="ASUS">华硕</option>
          <option value="AOC">AOC</option>
        </select>
      </div>

      <div class="filter-item">
        <label style="padding-top: 30px">排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchDisplays">
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
          <span>品牌</span>
        </div>
        <div class="header-price">价格</div>
        <div class="header-action">操作</div>
      </div>
      
      <div v-if="filteredDisplays.length === 0" class="empty-result">
        未找到匹配的配件
      </div>
      
      <div v-else v-for="display in currentPageData" 
           :key="display.id" 
           class="component-item">
        <img :src="display.imageUrl" alt="显示器图片" class="component-image" />
        <div class="component-name">{{ display.name }}</div>
        <div class="component-info">
          <span>{{ display.brand }}</span>
        </div>
        <div class="component-price">￥{{ display.price }}</div>
        <button @click="selectDisplay(display)" class="select-button">选择</button>
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
import { getAllDisplay } from "../../api/Display.ts";
import SearchBox from '../../components/SearchBox.vue';
import router from '../../router';

interface Display {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  brand: 'HKC' | 'ASUS' | 'AOC';
}

const displayList = ref<Display[]>([]);
const searchQuery = ref('');

const filteredDisplays = computed(() => {
  if(!searchQuery.value) return displayList.value;
  
  const query = searchQuery.value.toLowerCase();
  return displayList.value.filter(display => display.name.toLowerCase().includes(query));
});

const sliderValue = ref([0, 99999]);

const filters = ref({
  brand: '',
  sortOrder: 'asc'
});

const sliderChange = () => {
  fetchDisplays();
}

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
  
  let filteredList = list.filter(display => {
    if (display.price < sliderValue.value[0]) return false;
    if (display.price > sliderValue.value[1]) return false;
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

// 添加分页相关的状态
const currentPage = ref(1);
const pageSize = ref(13);  // 每页显示10条

// 计算总页数
const totalPages = computed(() => {
  return Math.ceil(filteredDisplays.value.length / pageSize.value);
});

// 计算当前页的数据
const currentPageData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return filteredDisplays.value.slice(start, end);
});

// 页码改变的处理函数
const handlePageChange = (page: number) => {
  currentPage.value = page;
};

onMounted(() => {
  fetchDisplays();
})
</script>

<style lang="scss" scoped>
@use './select-page.scss';
</style>