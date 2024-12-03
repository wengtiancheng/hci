<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { getAllChassis } from "../../api/Chassis.ts";
import SearchBox from '../../components/SearchBox.vue';
import router from '../../router';

interface Chassis {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  type: string;
}

const chassisList = ref<Chassis[]>([]);
const searchQuery = ref('');

const sliderValue = ref([0, 99999])


const filters = ref({
  sortOrder: 'asc'
});

const sliderChange = () => {
  fetchChassis();
}

const fetchChassis = async () => {
  const list = await getAllChassis();
  
  let filteredList = list.filter(chassis => {
    if (chassis.price < sliderValue.value[0]) return false;
    if (chassis.price > sliderValue.value[1]) return false;
    return true;
  });

  filteredList.sort((a, b) => {
    const factor = filters.value.sortOrder === 'asc' ? 1 : -1;
    return (a.price - b.price) * factor;
  });

  chassisList.value = filteredList;
}

const selectChassis = (chassis: Chassis) => {
  sessionStorage.setItem('chassis', String(chassis.id));
  sessionStorage.setItem('showSuccessMessage', JSON.stringify({
    type: '机箱',
    name: chassis.name,
    action: 'select'
  }));
  router.push('/custom-build');
}

const filteredChassis = computed(() => {
  if(!searchQuery.value) return chassisList.value;
  
  const query = searchQuery.value.toLowerCase();
  return chassisList.value.filter(chassis => 
    chassis.name.toLowerCase().includes(query)
  );
});

// 添加分页相关的状态
const currentPage = ref(1);
const pageSize = ref(10);  // 每页显示10条

// 计算总页数
const totalPages = computed(() => {
  return Math.ceil(filteredChassis.value.length / pageSize.value);
});

// 计算当前页的数据
const currentPageData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return filteredChassis.value.slice(start, end);
});

// 页码改变的处理函数
const handlePageChange = (page: number) => {
  currentPage.value = page;
};

onMounted(() => {
  fetchChassis();
})
</script>

<template>
  <div class="container">
    
    <div class="filters">
      
      
      <!-- 总价区间筛选 -->
      <div class="filter-item">
        <label>价格范围</label>
        <vue-slider v-model="sliderValue" :min="0" :max="99999"
                    :tooltip="'active'" :tooltip-placement="['bottom', 'bottom']"
                    @change="sliderChange" ></vue-slider>
      </div>

      <div class="filter-item">
        <label>排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchChassis">
          <option value="asc">价格从低到高</option>
          <option value="desc">价格从高到低</option>
        </select>
      </div>
    </div>

    <div class="component-list">
      <h2 class="page-title">选择机箱</h2>
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

      <div v-if="filteredChassis.length === 0" class="empty-result">
        未找到匹配的配件
      </div>

      <div v-else v-for="chassis in currentPageData" 
           :key="chassis.id" 
           class="component-item">
        <img :src="chassis.imageUrl" alt="机箱图片" class="component-image" />
        <div class="component-name">{{ chassis.name }}</div>
        <div class="component-info">
          <span>{{ chassis.type }}</span>
        </div>
        <div class="component-price">￥{{ chassis.price }}</div>
        <button @click="selectChassis(chassis)" class="select-button">选择</button>
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