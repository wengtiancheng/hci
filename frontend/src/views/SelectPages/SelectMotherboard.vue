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
            @input="fetchMotherboards"
          />
          <span>-</span>
          <input 
            type="number" 
            v-model="filters.maxPrice" 
            placeholder="最高价" 
            @input="fetchMotherboards"
          />
        </div>
      </div>

      <div class="filter-item">
        <label>主板类型</label>
        <select v-model="filters.type" @change="fetchMotherboards">
          <option value="">全部</option>
          <option value="INTEL">Intel平台</option>
          <option value="AMD">AMD平台</option>
        </select>
      </div>

      <div class="filter-item">
        <label>品牌</label>
        <select v-model="filters.brand" @change="fetchMotherboards">
          <option value="">全部</option>
          <option value="ASUS">华硕</option>
          <option value="MSI">微星</option>
          <option value="GIGABYTE">技嘉</option>
          <option value="COLORFUL">七彩虹</option>
        </select>
      </div>

      <div class="filter-item">
        <label>内存接口</label>
        <select v-model="filters.memoryType" @change="fetchMotherboards">
          <option value="">全部</option>
          <option value="DDR3">DDR3</option>
          <option value="DDR4">DDR4</option>
          <option value="DDR5">DDR5</option>
        </select>
      </div>

      <div class="filter-item">
        <label>排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchMotherboards">
          <option value="asc">价格从低到高</option>
          <option value="desc">价格从高到低</option>
        </select>
      </div>
    </div>

    <div class="component-list">
      <div class="search-container"> 
        <SearchBox v-model="searchQuery" />
      </div>
      <div v-if="filteredMotherboards.length === 0" class="empty-result">
        未找到匹配的配件
      </div>
      <div v-else v-for="motherboard in filteredMotherboards" 
           :key="motherboard.id" 
           class="component-item">
        <img :src="motherboard.imageUrl" alt="主板图片" class="component-image" />
        <div class="component-name">{{ motherboard.name }}</div>
        <div class="component-info">
          <span>{{ getBrandLabel(motherboard.brand) }}</span>
          <span>{{ motherboard.type }}</span>
          <span>{{ motherboard.memoryType }}</span>
        </div>
        <div class="component-price">￥{{ motherboard.price }}</div>
        <button @click="selectMotherboard(motherboard)" class="select-button">选择</button>
        <div v-if="motherboard.type !== cpuType" class="warning">
          警告：主板类型与CPU类型不匹配
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { getAllMotherboard } from "../../api/Motherboard.ts";
import { getCPUById } from '../../api/CPU';
import router from '../../router';
import SearchBox from '../../components/SearchBox.vue';
interface Motherboard {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  type: 'INTEL' | 'AMD';
  brand: 'ASUS' | 'MSI' | 'GIGABYTE' | 'COLORFUL';
  memoryType: 'DDR3' | 'DDR4' | 'DDR5';
}

const motherboardList = ref<Motherboard[]>([]);
const cpuType = ref('');
const searchQuery = ref('');

const filteredMotherboards = computed(() => {
  if(!searchQuery.value) return motherboardList.value;
  
  const query = searchQuery.value.toLowerCase();
  return motherboardList.value.filter(motherboard => motherboard.name.toLowerCase().includes(query));
});

const getSelectedCPU = () => {
  const cpuId = sessionStorage.getItem('cpu');
  console.log(cpuId)
  if (cpuId) {
      getCPUById(Number(cpuId)).then(cpu => {
      cpuType.value = cpu.type;
      console.log(cpuType.value)
      
    });
  }

}

const filters = ref({
  minPrice: null as number | null,
  maxPrice: null as number | null,
  type: '',
  brand: '',
  memoryType: '',
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

const fetchMotherboards = async () => {
  const list = await getAllMotherboard();
  
  // 应用筛选条件
  let filteredList = list.filter(motherboard => {
    if (filters.value.minPrice && motherboard.price < filters.value.minPrice) return false;
    if (filters.value.maxPrice && motherboard.price > filters.value.maxPrice) return false;
    if (filters.value.type && motherboard.type !== filters.value.type) return false;
    if (filters.value.brand && motherboard.brand !== filters.value.brand) return false;
    if (filters.value.memoryType && motherboard.memoryType !== filters.value.memoryType) return false;
    return true;
  });

  // 排序
  filteredList.sort((a, b) => {
    const factor = filters.value.sortOrder === 'asc' ? 1 : -1;
    return (a.price - b.price) * factor;
  });

  motherboardList.value = filteredList;
}

const selectMotherboard = (motherboard: Motherboard) => {
  sessionStorage.setItem('motherboard', String(motherboard.id));
  sessionStorage.setItem('showSuccessMessage', JSON.stringify({
    type: '主板',
    name: motherboard.name,
    action: 'select'
  }));
  router.push('/custom-build');
}

onMounted(() => {
  getSelectedCPU();
  fetchMotherboards();
})
</script>

<style lang="scss" scoped>
@use './select-page.scss';

// 由于主板信息较多，可能需要调整info的宽度

</style>