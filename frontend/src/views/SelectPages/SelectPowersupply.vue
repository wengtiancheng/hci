<template>
  <div class="container">

    <div class="filters">
      <h2 class="page-title">选择电源</h2>
      <div class="search-container">
        <SearchBox v-model="searchQuery" />
      </div>

      <div class="filter-item">
        <label style="padding-top: 30px">价格范围</label>
        <vue-slider v-model="sliderValue" :min="0" :max="5000"
                    :tooltip="'active'" :tooltip-placement="['bottom', 'bottom']"
                    @change="sliderChange" ></vue-slider>
      </div>

      <div class="filter-item">
        <label style="padding-top: 30px">品牌</label>
        <select v-model="filters.brand" @change="fetchPowersupplies">
          <option value="">全部</option>
          <option value="Huntkey">航嘉</option>
          <option value="ASUS">华硕</option>
        </select>
      </div>

      <div class="filter-item">
        <label style="padding-top: 30px">排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchPowersupplies">
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
      <div v-if="filteredPowersupplies.length === 0" class="empty-result">
        未找到匹配的配件
      </div>
      <div v-else v-for="powersupply in currentPageData"
           :key="powersupply.id"
           class="component-item">
        <img :src="powersupply.imageUrl" alt="电源图片" class="component-image" />
        <div class="component-name">{{ powersupply.name }}</div>
        <div class="component-info">
          <span>{{ getBrandLabel(powersupply.brand) }}</span>

        </div>
        <div class="component-price">￥{{ powersupply.price }}</div>
        <button @click="selectPowersupply(powersupply)" class="select-button">选择</button>
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
import { getAllPowersupply } from "../../api/Powersupply.ts";
import router from '../../router';
import SearchBox from '../../components/SearchBox.vue';
const currentPage = ref(1);
const pageSize = ref(13);

const totalPages = computed(() => {
  return Math.ceil(filteredPowersupplies.value.length / pageSize.value);
});

const currentPageData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return filteredPowersupplies.value.slice(start, end);
});

const handlePageChange = (page: number) => {
  currentPage.value = page;
};

interface Powersupply {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  brand: 'Huntkey' | 'ASUS';
  power: number; // 额定功率
}

const powersupplyList = ref<Powersupply[]>([]);

const sliderValue = ref([0, 5000]);

const filters = ref({
  brand: '',
  sortOrder: 'asc'
});

const searchQuery = ref('');

const filteredPowersupplies = computed(() => {
  if(!searchQuery.value) return powersupplyList.value;

  const query = searchQuery.value.toLowerCase();
  return powersupplyList.value.filter(powersupply => powersupply.name.toLowerCase().includes(query));
});

// 品牌名称转换
const getBrandLabel = (brand: string) => {
  const brandMap = {
    'Huntkey': '航嘉',
    'ASUS': '华硕'
  };
  return brandMap[brand] || brand;
};

const sliderChange = () => {
  fetchPowersupplies();
}

const fetchPowersupplies = async () => {
  const list = await getAllPowersupply();

  let filteredList = list.filter(powersupply => {
    if (powersupply.price < sliderValue.value[0]) return false;
    if (powersupply.price > sliderValue.value[1]) return false;
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