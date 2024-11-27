<template>
  <div class="container">
    <div class="filters">
      <h3>筛选条件</h3>
      
      <div class="filter-item">
        <label>价格区间</label>
        <div class="price-range">
          <input type="number" v-model="filters.minPrice" placeholder="最低价" @input="fetchDisplays"/>
          <span>-</span>
          <input type="number" v-model="filters.maxPrice" placeholder="最高价" @input="fetchDisplays"/>
        </div>
      </div>

      <div class="filter-item">
        <label>品牌</label>
        <select v-model="filters.brand" @change="fetchDisplays">
          <option value="">全部</option>
          <option value="HKC">HKC</option>
          <option value="ASUS">华硕</option>
          <option value="AOC">AOC</option>
        </select>
      </div>

      <div class="filter-item">
        <label>排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchDisplays">
          <option value="asc">价格从低到高</option>
          <option value="desc">价格从高到低</option>
        </select>
      </div>
    </div>

    <div class="component-list">
      <div v-for="display in displayList" 
           :key="display.id" 
           class="component-item">
        <img :src="display.imageUrl" alt="显示器图片" class="component-image" />
        <div class="component-name">{{ display.name }}</div>
        <div class="component-info">
          <span>{{ getBrandLabel(display.brand) }}</span>
        </div>
        <div class="component-price">￥{{ display.price }}</div>
        <button @click="selectDisplay(display)" class="select-button">选择</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getAllDisplay } from "../../api/Display.ts";
import router from '../../router';

interface Display {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  brand: 'HKC' | 'ASUS' | 'AOC';
}

const displayList = ref<Display[]>([]);

const filters = ref({
  minPrice: null as number | null,
  maxPrice: null as number | null,
  brand: '',
  sortOrder: 'asc'
});

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
  
  // 应用筛选条件
  let filteredList = list.filter(display => {
    if (filters.value.minPrice && display.price < filters.value.minPrice) return false;
    if (filters.value.maxPrice && display.price > filters.value.maxPrice) return false;
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

onMounted(() => {
  fetchDisplays();
})
</script>

<style lang="scss" scoped>
@use './select-page.scss';
</style>