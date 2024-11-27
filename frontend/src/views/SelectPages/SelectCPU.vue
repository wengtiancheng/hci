<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getAllCPU } from "../../api/CPU.ts";
import router from '../../router';

interface CPU {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  type: 'INTEL' | 'AMD';
}

const cpuList = ref<CPU[]>([]);

const filters = ref({
  minPrice: null as number | null,
  maxPrice: null as number | null,
  type: '',
  sortOrder: 'asc'
});

const fetchCPUs = async () => {
  const list = await getAllCPU();
  
  // 应用筛选条件
  let filteredList = list.filter(cpu => {
    if (filters.value.minPrice && cpu.price < filters.value.minPrice) return false;
    if (filters.value.maxPrice && cpu.price > filters.value.maxPrice) return false;
    if (filters.value.type && cpu.type !== filters.value.type) return false;
    return true;
  });

  // 排序
  filteredList.sort((a, b) => {
    const factor = filters.value.sortOrder === 'asc' ? 1 : -1;
    return (a.price - b.price) * factor;
  });

  cpuList.value = filteredList;
}

const selectCPU = (cpu: CPU) => {
  sessionStorage.setItem('cpu', String(cpu.id));
  sessionStorage.setItem('showSuccessMessage', JSON.stringify({
    type: 'CPU',
    name: cpu.name,
    action: 'select'
  }));
  router.push('/custom-build');
}

onMounted(() => {
  fetchCPUs();
})
</script>

<template>
  <div class="container">
    <div class="filters">
      <h3>筛选条件</h3>
      
      <div class="filter-item">
        <label>价格区间</label>
        <div class="price-range">
          <input type="number" v-model="filters.minPrice" placeholder="最低价" @input="fetchCPUs"/>
          <span>-</span>
          <input type="number" v-model="filters.maxPrice" placeholder="最高价" @input="fetchCPUs"/>
        </div>
      </div>

      <div class="filter-item">
        <label>处理器类型</label>
        <select v-model="filters.type" @change="fetchCPUs">
          <option value="">全部</option>
          <option value="INTEL">Intel</option>
          <option value="AMD">AMD</option>
        </select>
      </div>

      <div class="filter-item">
        <label>排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchCPUs">
          <option value="asc">价格从低到高</option>
          <option value="desc">价格从高到低</option>
        </select>
      </div>
    </div>

    <div class="component-list">
      <div v-for="cpu in cpuList" 
           :key="cpu.id" 
           class="component-item">
        <img :src="cpu.imageUrl" alt="CPU图片" class="component-image" />
        <div class="component-name">{{ cpu.name }}</div>
        <div class="component-info">
          <span>{{ cpu.type }}</span>
        </div>
        <div class="component-price">￥{{ cpu.price }}</div>
        <button @click="selectCPU(cpu)" class="select-button">选择</button>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@use './select-page.scss';
</style>