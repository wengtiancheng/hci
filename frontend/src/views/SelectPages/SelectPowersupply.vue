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
            @input="fetchPowersupplies"
          />
          <span>-</span>
          <input 
            type="number" 
            v-model="filters.maxPrice" 
            placeholder="最高价" 
            @input="fetchPowersupplies"
          />
        </div>
      </div>

      <div class="filter-item">
        <label>品牌</label>
        <select v-model="filters.brand" @change="fetchPowersupplies">
          <option value="">全部</option>
          <option value="Huntkey">航嘉</option>
          <option value="ASUS">华硕</option>
        </select>
      </div>

      <div class="filter-item">
        <label>排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchPowersupplies">
          <option value="asc">价格从低到高</option>
          <option value="desc">价格从高到低</option>
        </select>
      </div>
    </div>

    <div class="component-list">
      <div v-for="powersupply in powersupplyList" 
           :key="powersupply.id" 
           class="component-item">
        <img :src="powersupply.imageUrl" alt="电源图片" class="component-image" />
        <div class="component-name">{{ powersupply.name }}</div>
        <div class="component-info">
          <span>{{ getBrandLabel(powersupply.brand) }}</span>
          <span>{{ powersupply.power }}W</span>
        </div>
        <div class="component-price">￥{{ powersupply.price }}</div>
        <button @click="selectPowersupply(powersupply)" class="select-button">选择</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getAllPowersupply } from "../../api/Powersupply.ts";
import router from '../../router';

interface Powersupply {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  brand: 'Huntkey' | 'ASUS';
  power: number; // 额定功率
}

const powersupplyList = ref<Powersupply[]>([]);

const filters = ref({
  minPrice: null as number | null,
  maxPrice: null as number | null,
  brand: '',
  sortOrder: 'asc'
});

// 品牌名称转换
const getBrandLabel = (brand: string) => {
  const brandMap = {
    'Huntkey': '航嘉',
    'ASUS': '华硕'
  };
  return brandMap[brand] || brand;
};

const fetchPowersupplies = async () => {
  const list = await getAllPowersupply();
  
  // 应用筛选条件
  let filteredList = list.filter(powersupply => {
    if (filters.value.minPrice && powersupply.price < filters.value.minPrice) return false;
    if (filters.value.maxPrice && powersupply.price > filters.value.maxPrice) return false;
    if (filters.value.brand && powersupply.brand !== filters.value.brand) return false;
    return true;
  });

  // 排序
  filteredList.sort((a, b) => {
    const factor = filters.value.sortOrder === 'asc' ? 1 : -1;
    return (a.price - b.price) * factor;
  });

  powersupplyList.value = filteredList;
}

const selectPowersupply = (powersupply: Powersupply) => {
  sessionStorage.setItem('powersupply', String(powersupply.id));
  sessionStorage.setItem('showSuccessMessage', JSON.stringify({
    type: '电源',
    name: powersupply.name,
    action: 'select'
  }));
  router.push('/custom-build');
}

onMounted(() => {
  fetchPowersupplies();
})
</script>

<style lang="scss" scoped>
@use './select-page.scss';

// 调整电源信息显示的样式
.component-info {
  span:last-child {
    color: #666;
    font-size: 0.9em;
  }
}
</style>