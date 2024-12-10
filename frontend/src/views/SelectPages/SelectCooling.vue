<template>
  <div class="container">
    
    <div class="filters">
      <h2 class="page-title">选择散热器</h2>
      <div class="search-container">
        <SearchBox v-model="searchQuery" />
      </div>
      
      <div class="filter-item">
        <label style="padding-top: 30px">价格范围</label>
        <vue-slider v-model="sliderValue" :min="0" :max="99999"
                    :tooltip="'active'" :tooltip-placement="['bottom', 'bottom']"
                    @change="sliderChange" ></vue-slider>
      </div>

      <div class="filter-item">
        <label style="padding-top: 30px">品牌</label>
        <select v-model="filters.brand" @change="fetchCooling">
          <option value="">全部</option>
          <option value="VALKYRIE">瓦尔基里</option>
          <option value="Thermalright">利民</option>
        </select>
      </div>

      <div class="filter-item">
        <label style="padding-top: 30px">排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchCooling">
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

      <div v-if="filteredCoolings.length === 0" class="empty-result">
        未找到匹配的配件
      </div>
      
      <div v-else v-for="cooling in currentPageData" 
           :key="cooling.id" 
           class="component-item">
        <img :src="cooling.imageUrl" alt="水冷图片" class="component-image" />
        <div class="component-name">{{ cooling.name }}</div>
        <div class="component-info">
          <span>{{ getBrandLabel(cooling.brand) }}</span>
        </div>
        <div class="component-price">￥{{ cooling.price }}</div>
        <button @click="selectCooling(cooling)" class="select-button">选择</button>
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
import { getAllCooling } from "../../api/Cooling.ts";
import SearchBox from '../../components/SearchBox.vue';
import router from '../../router';

interface Cooling {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  brand: 'VALKYRIE' | 'Thermalright';
}

const coolingList = ref<Cooling[]>([]);
const sliderValue = ref([0, 99999]);
const filters = ref({
  minPrice: null as number | null,
  maxPrice: null as number | null,
  brand: '',
  sortOrder: 'asc'
});

const sliderChange = () => {
  fetchCooling();
}

const searchQuery = ref('');

const filteredCoolings = computed(() => {
  if(!searchQuery.value) return coolingList.value;
  
  const query = searchQuery.value.toLowerCase();
  return coolingList.value.filter(cooling => cooling.name.toLowerCase().includes(query));
});

// 品牌名称转换
const getBrandLabel = (brand: string) => {
  const brandMap = {
    'VALKYRIE': '瓦尔基里',
    'Thermalright': '利民'
  };
  return brandMap[brand] || brand;
};

const fetchCooling = async () => {
  const list = await getAllCooling();
  
  let filteredList = list.filter(cooling => {
    if (cooling.price < sliderValue.value[0]) return false;
    if (cooling.price > sliderValue.value[1]) return false;
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

// 添加分页相关的状态
const currentPage = ref(1);
const pageSize = ref(13);  // 每页显示10条

// 计算总页数
const totalPages = computed(() => {
  return Math.ceil(filteredCoolings.value.length / pageSize.value);
});

// 计算当前页的数据
const currentPageData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return filteredCoolings.value.slice(start, end);
});

// 页码改变的处理函数
const handlePageChange = (page: number) => {
  currentPage.value = page;
};

onMounted(() => {
  fetchCooling();
})
</script>

<style lang="scss" scoped>
@use './select-page.scss';
</style>