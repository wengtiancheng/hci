<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { getAllMemory } from "../../api/Memory.ts";
import ConfirmDialog from '../../components/ConfirmDialog.vue';
import { getMotherboardById } from '../../api/Motherboard.ts';
import SearchBox from '../../components/SearchBox.vue';
import router from '../../router';

interface Memory {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  type: 'DDR3' | 'DDR4' | 'DDR5';
  brand: 'KINGBANK' | 'KINGSTON' | 'PREDATOR' | 'Colorful';
}

const memoryList = ref<Memory[]>([]);
const searchQuery = ref('');
const motherboardMemoryType = ref('');
const confirmDialog = ref();
const sliderValue = ref([0, 99999]);

// 添加分页相关的状态
const currentPage = ref(1);
const pageSize = ref(10);  // 每页显示10条

// 计算总页数
const totalPages = computed(() => {
  return Math.ceil(filteredMemories.value.length / pageSize.value);
});

// 计算当前页的数据
const currentPageData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return filteredMemories.value.slice(start, end);
});

// 页码改变的处理函数
const handlePageChange = (page: number) => {
  currentPage.value = page;
};

const sliderChange = () => {
  fetchMemories();
}

const filteredMemories = computed(() => {
  if(!searchQuery.value) return memoryList.value;
  
  const query = searchQuery.value.toLowerCase();
  return memoryList.value.filter(memory => memory.name.toLowerCase().includes(query));
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
    'KINGBANK': '金百达',
    'KINGSTON': '金士顿',
    'PREDATOR': '宏基',
    'Colorful': '七彩虹'
  };
  return brandMap[brand] || brand;
};

const fetchMemories = async () => {
  const list = await getAllMemory();
  
  // 应用筛选条件
  let filteredList = list.filter(memory => {
    if (memory.price < sliderValue.value[0]) return false;
    if (memory.price > sliderValue.value[1]) return false;
    if (filters.value.type && memory.type !== filters.value.type) return false;
    if (filters.value.brand && memory.brand !== filters.value.brand) return false;
    return true;
  });

  // 排序
  filteredList.sort((a, b) => {
    const factor = filters.value.sortOrder === 'asc' ? 1 : -1;
    return (a.price - b.price) * factor;
  });

  memoryList.value = filteredList;
}

const getSelectedMotherboard = () => {
  const motherboardId = sessionStorage.getItem('motherboard');
  if (motherboardId) {
    getMotherboardById(Number(motherboardId)).then(motherboard => {
      motherboardMemoryType.value = motherboard.memoryType;
    });
  }
}

const selectMemory = async (memory: Memory) => {
  if (motherboardMemoryType.value && memory.type !== motherboardMemoryType.value) {
    const confirmed = await confirmDialog.value.show(
      '内存类型不兼容提醒',
      `当前选择的内存类型(${memory.type})与主板支持的内存类型(${motherboardMemoryType.value})不兼容，是否确认选择？`
    );
    
    if (!confirmed) {
      return;
    }
  }

  sessionStorage.setItem('memory', String(memory.id));
  sessionStorage.setItem('showSuccessMessage', JSON.stringify({
    type: '内存',
    name: memory.name,
    action: 'select'
  }));
  router.push('/custom-build');
}

onMounted(() => {
  getSelectedMotherboard();
  fetchMemories();
})
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

      <!-- 内存类型筛选 -->
      <div class="filter-item">
        <label>内存类型</label>
        <select v-model="filters.type" @change="fetchMemories">
          <option value="">全部</option>
          <option value="DDR3">DDR3</option>
          <option value="DDR4">DDR4</option>
          <option value="DDR5">DDR5</option>
        </select>
      </div>

      <!-- 品牌筛选 -->
      <div class="filter-item">
        <label>品牌</label>
        <select v-model="filters.brand" @change="fetchMemories">
          <option value="">全部</option>
          <option value="KINGBANK">金百达</option>
          <option value="KINGSTON">金士顿</option>
          <option value="PREDATOR">宏基</option>
          <option value="Colorful">七彩虹</option>
        </select>
      </div>

      <!-- 排序方式 -->
      <div class="filter-item">
        <label>排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchMemories">
          <option value="asc">价格从低到高</option>
          <option value="desc">价格从高到低</option>
        </select>
      </div>
    </div>

    <div class="component-list">
      <h2 class="page-title">选择内存</h2>
      <div class="search-container"> 
        <SearchBox v-model="searchQuery" />
      </div>
      
      
      <!-- 添加表头 -->
      <div class="list-header">
        <div class="header-image">图片</div>
        <div class="header-name">名称</div>
        <div class="header-info">
          <span>品牌</span>
          <span>类型</span>
        </div>
        <div class="header-price">价格</div>
        <div class="header-action">操作</div>
      </div>

      <div v-if="filteredMemories.length === 0" class="empty-result">
        未找到匹配的配件
      </div>
      
      <div v-else v-for="memory in currentPageData" 
           :key="memory.id" 
           class="component-item">
        <div class="main-info-row">
          <img :src="memory.imageUrl" alt="内存图片" class="component-image" />
          <div class="component-name">{{ memory.name }}</div>
          <div class="component-info">
            <span>{{ getBrandLabel(memory.brand) }}</span>
            <span>{{ memory.type }}</span>
          </div>
          <div class="component-price">￥{{ memory.price }}</div>
          <button @click="selectMemory(memory)" class="select-button">选择</button>
        </div>
        
        <div v-if="memory.type !== motherboardMemoryType && motherboardMemoryType !== ''" 
             class="warning-row">
          <div class="warning"> 
            <img src="../../assets/icons/warning.svg" alt="警告" class="warning-icon" />
            警告：内存类型和主板不兼容
          </div>
        </div>
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
    <ConfirmDialog ref="confirmDialog" />
  </div>
</template>

<style lang="scss" scoped>
@use './select-page.scss';

.component-item {
  flex-direction: column;
  padding: 0;
}

.main-info-row {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 0.5em 0.75em;
  gap: 1em;
  justify-content: space-between;
}

.warning-row {
  width: 100%;
  padding: 0 0.75em;
  margin-top: -2em;  // 减少间距
  display: flex;
  justify-content: center;
  padding-bottom: 1em;
}

.warning {
  color: red;
  display: flex;
  align-items: flex-start;
  gap: 4px;
  
  .warning-icon {
    width: 20px;
    height: 20px;
    margin-top: 3px;
  }
}
</style>