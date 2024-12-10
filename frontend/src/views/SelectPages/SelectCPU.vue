<script setup lang="ts">
import { ref, onMounted, computed } from 'vue';
import { getAllCPU } from "../../api/CPU.ts";
import SearchBox from '../../components/SearchBox.vue';
import router from '../../router';
import { getMotherboardById } from '../../api/Motherboard';
import ConfirmDialog from '../../components/ConfirmDialog.vue';

interface CPU {
  id: number;
  name: string;
  price: number;
  imageUrl: string;
  type: 'INTEL' | 'AMD';
}

const cpuList = ref<CPU[]>([]);
const searchQuery = ref('');
const motherboardType = ref('');
const sliderValue = ref([0, 99999]);
const confirmDialog = ref();

const filters = ref({
  type: '',
  sortOrder: 'asc'
});

const sliderChange = () => {
  fetchCPUs();
}

const fetchCPUs = async () => {
  const list = await getAllCPU();
  
  let filteredList = list.filter(cpu => {
    if (cpu.price < sliderValue.value[0]) return false;
    if (cpu.price > sliderValue.value[1]) return false;
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



// 获取已选择的主板信息
const getSelectedMotherboard = () => {
  const motherboardId = sessionStorage.getItem('motherboard');
  if (motherboardId) {
    getMotherboardById(Number(motherboardId)).then(motherboard => {
      motherboardType.value = motherboard.type;
    });
  }
}

// 修改 selectCPU 函数
const selectCPU = async (cpu: CPU) => {
  if (cpu.type !== motherboardType.value && motherboardType.value !== '') {
    const confirmed = await confirmDialog.value.show(
      '类型不匹配提醒',
      `当前选择的CPU类型(${cpu.type})与主板类型(${motherboardType.value})不匹配，是否确认选择？`
    );
    
    if (!confirmed) {
      return;
    }
  }
  
  sessionStorage.setItem('cpu', String(cpu.id));
  sessionStorage.setItem('showSuccessMessage', JSON.stringify({
    type: 'CPU',
    name: cpu.name,
    action: 'select'
  }));
  router.push('/custom-build');
}

const filteredCPUs = computed(() => {
  if(!searchQuery.value) return cpuList.value;

  const query = searchQuery.value.toLowerCase();
  return cpuList.value.filter(cpu => cpu.name.toLowerCase().includes(query));
});

onMounted(() => {
  getSelectedMotherboard();
  fetchCPUs();
})

// 添加分页相关的状态
const currentPage = ref(1);
const pageSize = ref(13);  // 每页显示10条

// 计算总页数
const totalPages = computed(() => {
  return Math.ceil(filteredCPUs.value.length / pageSize.value);
});

// 计算当前页的数据
const currentPageData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value;
  const end = start + pageSize.value;
  return filteredCPUs.value.slice(start, end);
});

// 页码改变的处理函数
const handlePageChange = (page: number) => {
  currentPage.value = page;
};
</script>

<template>
  <div class="container">
    
    <div class="filters">

      <h2 class="page-title">选择CPU</h2>

      <div class="search-container">
        <SearchBox v-model="searchQuery" />
      </div>

      <div class="filter-item">
        <label style="margin-top: 30px">价格范围</label>
        <vue-slider v-model="sliderValue" :min="0" :max="99999"
                    :tooltip="'active'" :tooltip-placement="['bottom', 'bottom']"
                    @change="sliderChange" ></vue-slider>
      </div>
      
      <div class="filter-item">
        <label style="margin-top: 30px">处理器类型</label>
        <select v-model="filters.type" @change="fetchCPUs">
          <option value="">全部</option>
          <option value="INTEL">Intel</option>
          <option value="AMD">AMD</option>
        </select>
      </div>

      <div class="filter-item">
        <label style="margin-top: 30px">排序方式</label>
        <select v-model="filters.sortOrder" @change="fetchCPUs">
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
          <span>类型</span>
        </div>
        <div class="header-price">价格</div>
        <div class="header-action">操作</div>
      </div>
      
      <div v-if="filteredCPUs.length === 0" class="empty-result">
        未找到匹配的配件
      </div>
      
      <div v-else v-for="cpu in currentPageData" 
           :key="cpu.id" 
           class="component-item">
        <div class="main-info-row">
          <img :src="cpu.imageUrl" alt="CPU图片" class="component-image" />
          <div class="component-name">{{ cpu.name }}</div>
          <div class="component-info">
            <span>{{ cpu.type }}</span>
          </div>
          <div class="component-price">￥{{ cpu.price }}</div>
          <button @click="selectCPU(cpu)" class="select-button">选择</button>
        </div>
        
        <div v-if="cpu.type !== motherboardType && motherboardType !== ''" 
             class="warning-row">
          <div class="warning">
            <img src="../../assets/icons/warning.svg" alt="警告" class="warning-icon" />
            警告：CPU类型与主板类型不匹配
          </div>
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

<style lang="scss" scoped>
@use './select-page.scss';

.component-item {
  flex-direction: column;
  padding: 0;
}

.main-info-row {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 0.5em 0.75em;
  gap: 1em;
  justify-content: space-between;
}

.warning-row {
  width: 100%;
  padding: 0 0.75em;
  margin-top: -2em;  // 减少间距
  display: flex;
  justify-content: center;
  padding-bottom: 1em;
}

.warning {
  color: red;
  display: flex;
  align-items: flex-start;
  gap: 4px;
  
  .warning-icon {
    width: 20px;
    height: 20px;
    margin-top: 3px;
  }
}
</style>