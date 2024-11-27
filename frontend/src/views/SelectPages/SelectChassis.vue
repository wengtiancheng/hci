<script setup lang="ts">
import {ref, onMounted} from 'vue';
import {getAllChassis} from "../../api/Chassis.ts";
import router from '../../router'



interface Chassis {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  linkUrl: string;
  brand: string;
  type: string;
}

const chassisList = ref<Chassis[]>([]);

const filters = ref({
  minPrice: null as number | null,
  maxPrice: null as number | null,
  brand: '',
  type: '',
  sortBy: 'price', // 排序字段：price或name
  sortOrder: 'asc' // 排序方式：asc或desc
});

const toastRef = ref();

const fetchChassis = async () => {
  const list = await getAllChassis();
  
  // 价格筛选
  let filteredList = list.filter(chassis => {
    if (filters.value.minPrice && chassis.price < filters.value.minPrice) return false;
    if (filters.value.maxPrice && chassis.price > filters.value.maxPrice) return false;
    if (filters.value.brand && chassis.brand !== filters.value.brand) return false;
    if (filters.value.type && chassis.type !== filters.value.type) return false;
    return true;
  });

  // 排序
  filteredList.sort((a, b) => {
    const factor = filters.value.sortOrder === 'asc' ? 1 : -1;
    if (filters.value.sortBy === 'price') {
      return (a.price - b.price) * factor;
    }
    return a.name.localeCompare(b.name) * factor;
  });

  chassisList.value = filteredList;
}

const selectChassis = (chassis : Chassis) => {
  sessionStorage.setItem('chassis', String(chassis.id));
  // 保存选择的配件信息用于显示提示
  sessionStorage.setItem('showSuccessMessage', JSON.stringify({
    type: '机箱',  // 配件类型
    name: chassis.name,  // 配件名称
    action: 'select'  // 动作类型：select-新选择，change-更换
  }));
  router.push('/custom-build')
  
}

onMounted(() => {
  fetchChassis();
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
            @input="fetchChassis"
          />
          <span>-</span>
          <input 
            type="number" 
            v-model="filters.maxPrice" 
            placeholder="最高价" 
            @input="fetchChassis"
          />
        </div>
      </div>

      <!-- 品牌筛选 -->
      <div class="filter-item">
        <label>品牌</label>
        <select v-model="filters.brand" @change="fetchChassis">
          <option value="">全部</option>
          <option value="ASUS">华硕</option>
          <option value="AIGO">爱国者</option>
        </select>
      </div>

      <!-- 类型筛选 -->
      <div class="filter-item">
        <label>类型</label>
        <select v-model="filters.type" @change="fetchChassis">
          <option value="">全部</option>
          <option value="ATX">ATX</option>
          <option value="M-ATX">M-ATX</option>
          <option value="E-ATX">E-ATX</option>
          <option value="MINI-ITX">MINI-ITX</option>
        </select>
      </div>

      <!-- 排序方式 -->
      <div class="filter-item">
        <label>排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchChassis">
          <option value="asc">价格从低到高</option>
          <option value="desc">价格从高到低</option>
        </select>
      </div>
    </div>

    <div class="component-list">
      <div v-for="chassis in chassisList" 
           :key="chassis.id" 
           class="component-item">
        <img :src="chassis.imageUrl" alt="机箱图片" class="component-image" />
        <div class="component-name">{{ chassis.name }}</div>
        <div class="component-brand">{{ chassis.brand }}</div>
        <div class="component-type">{{ chassis.type }}</div>
        <div class="component-price">￥{{ chassis.price }}</div>
        <button @click="selectChassis(chassis)" class="select-button">选择</button>
      </div>
    </div>
    <Toast ref="toastRef" />
  </div>
</template>

<style lang="scss" scoped>
@use './select-page.scss';
</style>