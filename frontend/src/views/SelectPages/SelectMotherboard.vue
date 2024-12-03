<template>
  <div class="container">
    
    
    <div class="filters">
      
      
      <div class="filter-item">
        <label>价格范围</label>
        <vue-slider v-model="sliderValue" :min="0" :max="99999"
                    :tooltip="'active'" :tooltip-placement="['bottom', 'bottom']"
                    @change="sliderChange" ></vue-slider>
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
      <h2 class="page-title">选择主板</h2>
      <div class="search-container"> 
        <SearchBox v-model="searchQuery" />
      </div>
      <div class="list-header">
        <div class="header-image">图片</div>
        <div class="header-name">名称</div>
        <div class="header-info">
          <span>品牌</span>
          <span>类型</span>
          <span>内存接口</span>
        </div>
        <div class="header-price">价格</div>
        <div class="header-action">操作</div>
      </div>
      
      <div v-if="filteredMotherboards.length === 0" class="empty-result">
        未找到匹配的配件
      </div>
      
      <div v-else v-for="motherboard in currentPageData" 
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
        <div v-if="motherboard.type !== cpuType && cpuType !== ''" class="warning">
          <img src="../../assets/icons/warning.svg" alt="警告" class="warning-icon" />
          警告：主板类型与CPU类型不匹配
        </div>
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
    <ConfirmDialog ref="confirmDialog" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { getAllMotherboard } from "../../api/Motherboard.ts";
import { getCPUById } from '../../api/CPU';
import router from '../../router';
import SearchBox from '../../components/SearchBox.vue';
import ConfirmDialog from '../../components/ConfirmDialog.vue';

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
const sliderValue = ref([0, 99999]);

const sliderChange = () => {
  fetchMotherboards();
}

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
    if (motherboard.price < sliderValue.value[0]) return false;
    if (motherboard.price > sliderValue.value[1]) return false;
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

const confirmDialog = ref();

const selectMotherboard = async (motherboard: Motherboard) => {
  if (motherboard.type !== cpuType.value && cpuType.value !== '') {
    const confirmed = await confirmDialog.value.show(
      '类型不匹配提醒',
      `当前选择的主板类型(${motherboard.type})与CPU类型(${cpuType.value})不匹配，是否确认选择？`
    );
    
    if (!confirmed) {
      return;
    }
  }
  
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

// 添加分页相关的状态
const currentPage = ref(1);
const pageSize = ref(10);  // 每页显示10条

// 计算总页数
const totalPages = computed(() => {
  return Math.ceil(filteredMotherboards.value.length / pageSize.value);
});

// 计算当前页的数据
const currentPageData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return filteredMotherboards.value.slice(start, end);
});

// 页码改变的处理函数
const handlePageChange = (page: number) => {
  currentPage.value = page;
};
</script>

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