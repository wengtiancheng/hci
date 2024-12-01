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

const filters = ref({
  minPrice: null as number | null,
  maxPrice: null as number | null,
  sortOrder: 'asc'
});

const fetchChassis = async () => {
  const list = await getAllChassis();
  
  let filteredList = list.filter(chassis => {
    if (filters.value.minPrice && chassis.price < filters.value.minPrice) return false;
    if (filters.value.maxPrice && chassis.price > filters.value.maxPrice) return false;
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

onMounted(() => {
  fetchChassis();
})
</script>

<template>
  <div class="container">
    <div class="filters">
      <h3>筛选条件</h3>
      
      <div class="filter-item">
        <label>价格区间</label>
        <div class="price-range">
          <input type="number" v-model="filters.minPrice" placeholder="最低价" @input="fetchChassis"/>
          <span>-</span>
          <input type="number" v-model="filters.maxPrice" placeholder="最高价" @input="fetchChassis"/>
        </div>
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
      <div class="search-container">
        <SearchBox v-model="searchQuery" />
      </div>

      <div v-if="filteredChassis.length === 0" class="empty-result">
        未找到匹配的配件
      </div>

      <div v-else v-for="chassis in filteredChassis" 
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
    </div>
  </div>
</template>

<style lang="scss" scoped>
@use './select-page.scss';
</style>