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
            @input="fetchCooling"
          />
          <span>-</span>
          <input 
            type="number" 
            v-model="filters.maxPrice" 
            placeholder="最高价" 
            @input="fetchCooling"
          />
        </div>
      </div>

      <div class="filter-item">
        <label>品牌</label>
        <select v-model="filters.brand" @change="fetchCooling">
          <option value="">全部</option>
          <option value="VALKYRIE">瓦尔基里</option>
          <option value="Thermalright">利民</option>
        </select>
      </div>

      <div class="filter-item">
        <label>排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchCooling">
          <option value="asc">价格从低到高</option>
          <option value="desc">价格从高到低</option>
        </select>
      </div>
    </div>

    <div class="component-list">
      <div v-for="cooling in coolingList" 
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
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getAllCooling } from "../../api/Cooling.ts";
import router from '../../router';

interface Cooling {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  brand: 'VALKYRIE' | 'Thermalright';
}

const coolingList = ref<Cooling[]>([]);

const filters = ref({
  minPrice: null as number | null,
  maxPrice: null as number | null,
  brand: '',
  sortOrder: 'asc'
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
  
  // 应用筛选条件
  let filteredList = list.filter(cooling => {
    if (filters.value.minPrice && cooling.price < filters.value.minPrice) return false;
    if (filters.value.maxPrice && cooling.price > filters.value.maxPrice) return false;
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

onMounted(() => {
  fetchCooling();
})
</script>

<style lang="scss" scoped>
@use './select-page.scss';
</style>