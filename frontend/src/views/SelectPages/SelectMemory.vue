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
    if (filters.value.minPrice && memory.price < filters.value.minPrice) return false;
    if (filters.value.maxPrice && memory.price > filters.value.maxPrice) return false;
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
      <h3>筛选条件</h3>

      <!-- 价格区间筛选 -->
      <div class="filter-item">
        <label>价格区间</label>
        <div class="price-range">
          <input 
            type="number" 
            v-model="filters.minPrice" 
            placeholder="最低价" 
            @input="fetchMemories"
          />
          <span>-</span>
          <input 
            type="number" 
            v-model="filters.maxPrice" 
            placeholder="最高价" 
            @input="fetchMemories"
          />
        </div>
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
      <div class="search-container"> 
        <SearchBox v-model="searchQuery" />
      </div>
      <div v-if="filteredMemories.length === 0" class="empty-result">
        未找到匹配的配件
      </div>
      <div v-else v-for="memory in filteredMemories" 
           :key="memory.id" 
           class="component-item">
        <img :src="memory.imageUrl" alt="内存图片" class="component-image" />
        <div class="component-name">{{ memory.name }}</div>
        <div class="component-info">
          <span>{{ getBrandLabel(memory.brand) }}</span>
          <span>{{ memory.type }}</span>
        </div>
        <div class="component-price">￥{{ memory.price }}</div>
        <button @click="selectMemory(memory)" class="select-button">选择</button>
        <div v-if="memory.type !== motherboardMemoryType && motherboardMemoryType !== ''" class="warning"> 
          <img src="../../assets/icons/warning.svg" alt="警告" class="warning-icon" />
          警告：内存类型和主板不兼容
        </div>
      </div>
    </div>
    <ConfirmDialog ref="confirmDialog" />
  </div>
</template>

<style lang="scss" scoped>
@use './select-page.scss';

.warning {
  color: red;
  display: flex;
  align-items: center;
  gap: 4px;
  
  .warning-icon {
    width: 20px;
    height: 20px;
  }
}
</style>