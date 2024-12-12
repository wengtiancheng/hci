<template>
  <div class="all-solutions">
    <!-- Left filters -->
    <div class="filters">
      <h3>过滤器</h3>

      <!-- Price range filter -->
      <div class="filter-item">
        <label>价格范围</label>
        <vue-slider v-model="sliderValue" :min=minPrice :max=maxPrice
                    :tooltip="'active'" :tooltip-placement="['bottom', 'bottom']"
                    @change="sliderChange"></vue-slider>
      </div>

      <!-- Sort order filter -->
      <div class="filter-item">
        <label for="sort-order">排序方式</label>
        <select id="sort-order" v-model="filters.sortBy" @change="fetchSolutions">
          <option :value="SortType.NONE">默认排序</option>
          <option :value="SortType.PRICE_DESC">按价格降序</option>
          <option :value="SortType.SAVE_DESC">按收藏数降序</option>
          <option :value="SortType.CREATE_TIME_DESC">按创建时间降序</option>
        </select>
      </div>

      <!-- CPU type filter -->
      <div class="filter-item">
        <label for="cpu" style="font-size: 20px; font-family: 'Microsoft YaHei UI',serif">CPU 类型</label>
        <el-checkbox v-model="selectAllCPUs" @change="toggleSelectAllCPUs">全选</el-checkbox>
        <el-checkbox-group v-model="filters.cpuName" @change="handleCPUChange">
          <el-checkbox label="i9 14900K">i9 14900K</el-checkbox>
          <el-checkbox label="i7 14700KF">i7 14700KF</el-checkbox>
          <el-checkbox label="i7 14700K">i7 14700K</el-checkbox>
          <el-checkbox label="i5 14600KF">i5 14600KF</el-checkbox>
          <el-checkbox label="i9 13900K">i9 13900K</el-checkbox>
          <el-checkbox label="i5 13600KF">i5 13600KF</el-checkbox>
          <el-checkbox label="i5 13600K">i5 13600K</el-checkbox>
          <el-checkbox label="i5 13490F">i5 13490F</el-checkbox>
          <el-checkbox label="i5 13400F">i5 13400F</el-checkbox>
          <el-checkbox label="i5 13400">i5 13400</el-checkbox>
          <el-checkbox label="i5 12600KF">i5 12600KF</el-checkbox>
          <el-checkbox label="i5 12400F">i5 12400F</el-checkbox>
          <el-checkbox label="AMD Ryzen5 9600X">AMD Ryzen5 9600X</el-checkbox>
          <el-checkbox label="AMD Ryzen7 7800X3D">AMD Ryzen7 7800X3D</el-checkbox>
          <el-checkbox label="AMD Ryzen5 7500F">AMD Ryzen5 7500F</el-checkbox>
          <el-checkbox label="AMD Ryzen7 5700X3D">AMD Ryzen7 5700X3D</el-checkbox>
          <el-checkbox label="AMD Ryzen7 5700X">AMD Ryzen7 5700X</el-checkbox>
          <el-checkbox label="AMD Ryzen5 5600G">AMD Ryzen5 5600G</el-checkbox>
          <el-checkbox label="AMD Ryzen5 5600">AMD Ryzen5 5600</el-checkbox>
          <!-- More options -->
        </el-checkbox-group>
      </div>

      <!-- GPU type filter -->
      <div class="filter-item">
        <label for="gpu" style="font-size: 20px; font-family: 'Microsoft YaHei UI',serif">GPU 类型</label>
        <el-checkbox v-model="selectAllGPUs" @change="toggleSelectAllGPUs">全选</el-checkbox>
        <el-checkbox-group v-model="filters.gpuName" @change="handleGPUChange">
          <el-checkbox label="RTX4090">RTX4090</el-checkbox>
          <el-checkbox label="RTX4070SUPER">RTX4070SUPER</el-checkbox>
          <el-checkbox label="RTX4070">RTX4070</el-checkbox>
          <el-checkbox label="RTX4060Ti">RTX4060Ti</el-checkbox>
          <el-checkbox label="RTX4060">RTX4060</el-checkbox>
          <el-checkbox label="RTX3060Ti">RTX3060Ti</el-checkbox>
          <el-checkbox label="RTX3060">RTX3060</el-checkbox>
          <el-checkbox label="RX7800XT">RX7800XT</el-checkbox>
          <el-checkbox label="RX6750GRE">RX6750GRE</el-checkbox>
          <el-checkbox label="RX6750">RX6750</el-checkbox>
          <el-checkbox label="RX6500XT">RX6500XT</el-checkbox>
        </el-checkbox-group>
      </div>

      <!-- Motherboard type filter -->
      <div class="filter-item">
        <label for="motherboard" style="font-size: 20px; font-family: 'Microsoft YaHei UI',serif">主板类型</label>
        <el-checkbox v-model="selectAllMotherboards" @change="toggleSelectAllMotherboards">全选</el-checkbox>
        <el-checkbox-group v-model="filters.motherboardName" @change="handleMotherboardChange">
          <el-checkbox label="B760M-PLUS">B760M-PLUS</el-checkbox>
          <el-checkbox label="B760M">B760M</el-checkbox>
          <el-checkbox label="B660M">B660M</el-checkbox>
          <el-checkbox label="B650M-B">B650M-B</el-checkbox>
          <el-checkbox label="B650M">B650M</el-checkbox>
          <el-checkbox label="B550M-P">B550M-P</el-checkbox>
          <el-checkbox label="H610M">H610M</el-checkbox>
          <el-checkbox label="H510M">H510M</el-checkbox>
          <el-checkbox label="A520M-A">A520M-A</el-checkbox>
          <el-checkbox label="Z790">Z790</el-checkbox>
        </el-checkbox-group>
      </div>

      <!-- Memory type filter -->
      <div class="filter-item">
        <label for="memory" style="font-size: 20px; font-family: 'Microsoft YaHei UI',serif">内存类型</label>
        <el-checkbox v-model="selectAllMemory" @change="toggleSelectAllMemory">全选</el-checkbox>
        <el-checkbox-group v-model="filters.memoryName" @change="handleMemoryChange">
          <el-checkbox label="DDR4">DDR4</el-checkbox>
          <el-checkbox label="DDR5">DDR5</el-checkbox>
        </el-checkbox-group>
      </div>
    </div>

    <!-- Right solution cards -->
    <div class="solutions">
      <router-link
          v-for="solution in solutions"
          :key="solution.id"
          :to="{ path: '/custom-build', query: { solution: JSON.stringify(solution) } }"
          class="solution-card"
      >
        <img :src="solution.imageUrl" alt="方案图片" class="solution-image" />
        <div class="solution-info">
          <h4>{{ solution.name }}</h4>
          <p class="secondary-text">CPU：{{ solution.cpuName }}</p>
          <p class="secondary-text">GPU：{{ solution.gpuName }}</p>
          <p class="price">价格：￥{{ solution.totalPrice }}</p>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getAllSolution, SolutionVO, SortType, Filters, initFilters } from '../api/Solution';
import { getCPUById } from '../api/CPU';
import { getGPUById } from '../api/GPU';
import { useRoute } from 'vue-router';

// Define filter state
const filters = ref<Filters>(initFilters);

const maxPrice = 40423;
const minPrice = 3730;
const sliderValue = ref([minPrice , maxPrice]);

// List of solutions
const solutions = ref<SolutionVO[]>([]);

// CPU options
const cpuOptions = [
  'i9 14900K',
  'i7 14700KF',
  'i7 14700K',
  'i5 14600K',
  'i9 13900K',
  'i5 13600KF',
  'i5 13600K',
  'i5 13490F',
  'i5 13400F',
  'i5 13400',
  'i5 12600KF',
  'i5 12400F',
  'AMD Ryzen5 9600X',
  'AMD Ryzen7 7800X3D',
  'AMD Ryzen5 7500F',
  'AMD Ryzen7 5700X3D',
  'AMD Ryzen7 5700X',
  'AMD Ryzen5 5600G',
  'AMD Ryzen5 5600',
];

const gpuOptions = [
  'RTX4090',
  'RTX4070SUPER',
  'RTX4070',
  'RTX4060Ti',
  'RTX4060',
  'RTX3060',
  'RX7800XT',
  'RX6750GRE',
  'RX6750',
  'RX6500XT',
];

const motherboardOptions = [
  'B760M-PLUS',
  'B760M',
  'B660M',
  'B650M-B',
  'B650M',
  'B550M-P',
  'H610M',
  'H510M',
  'A520M-A',
  'Z790',
];

const memoryOptions = ['DDR4', 'DDR5'];

// Select all states
const selectAllCPUs = ref(true);
const selectAllGPUs = ref(true);
const selectAllMotherboards = ref(true);
const selectAllMemory = ref(true);

// Fetch CPU and GPU names
const fetchNames = async (solutions: SolutionVO[]) => {
  for (const solution of solutions) {
    solution.cpuName = (await getCPUById(solution.cpuId)).name;
    solution.gpuName = (await getGPUById(solution.gpuId)).name;
  }
};

// Fetch all solutions
const fetchSolutions = async () => {
  console.log('Fetching solutions with filters:', filters.value);
  solutions.value = await getAllSolution(filters.value);
  await fetchNames(solutions.value);
};

// Get route parameters
const route = useRoute();
if (route.query.filters) {
  filters.value = JSON.parse(route.query.filters as string); // Parse filters
}

// Slider change callback
const sliderChange = async (value: number[]) => {
  filters.value.lowPrice = value[0];
  filters.value.highPrice = value[1];
  console.log('Change slider:', filters.value);
  await fetchSolutions();
};

// Toggle select all CPUs
const toggleSelectAllCPUs = () => {
  if (filters.value.cpuName.length === 0) {
    selectAllCPUs.value = true;
    fetchSolutions();
  } else if (filters.value.cpuName.length === cpuOptions.length) {
    selectAllCPUs.value = true;
    filters.value.cpuName = [];
    fetchSolutions();
  } else if (selectAllCPUs.value) {
    filters.value.cpuName = [...cpuOptions];
    fetchSolutions();
  } else {
    filters.value.cpuName = [];
    fetchSolutions();
  }
};

// Handle individual CPU checkbox change
const handleCPUChange = () => {
  if (filters.value.cpuName.length === 0) {
    selectAllCPUs.value = true;
  } else if (filters.value.cpuName.length === cpuOptions.length) {
    selectAllCPUs.value = true;
  } else {
    selectAllCPUs.value = false;
  }
  fetchSolutions();
};

// Toggle select all GPUs
const toggleSelectAllGPUs = () => {
  if (filters.value.gpuName.length === 0) {
    selectAllGPUs.value = true;
    fetchSolutions();
  } else if (filters.value.gpuName.length === gpuOptions.length) {
    selectAllGPUs.value = true;
    filters.value.gpuName = [];
    fetchSolutions();
  }
  else if (selectAllGPUs.value) {
    filters.value.gpuName = [...gpuOptions];
    fetchSolutions();
  } else {
    filters.value.gpuName = [];
    fetchSolutions();
  }
};

// Handle individual GPU checkbox change
const handleGPUChange = () => {
  console.log('GPU change:', filters.value.gpuName.length);
  if (filters.value.gpuName.length === 0) {
    selectAllGPUs.value = true;
  } else if (filters.value.gpuName.length === gpuOptions.length) {
    selectAllGPUs.value = true;
  } else {
    selectAllGPUs.value = false;
  }
  fetchSolutions();
};

// Toggle select all Motherboards
const toggleSelectAllMotherboards = () => {
  if (selectAllMotherboards.value) {
    filters.value.motherboardName = [...motherboardOptions];
    fetchSolutions();
  } else if (filters.value.motherboardName.length === motherboardOptions.length) {
    selectAllMotherboards.value = true;
    filters.value.motherboardName = [];
    fetchSolutions();
  } else {
    filters.value.motherboardName = [];
    fetchSolutions();
  }
};

// Handle individual Motherboard checkbox change
const handleMotherboardChange = () => {
  if (filters.value.motherboardName.length === 0) {
    selectAllMotherboards.value = true;
  } else if (filters.value.motherboardName.length === motherboardOptions.length) {
    selectAllMotherboards.value = true;
  } else {
    selectAllMotherboards.value = false;
  }
  fetchSolutions();
};

// Toggle select all Memory
const toggleSelectAllMemory = () => {
  if (selectAllMemory.value) {
    filters.value.memoryName = [...memoryOptions];
    fetchSolutions();
  } else if (filters.value.memoryName.length === memoryOptions.length) {
    selectAllMemory.value = true;
    filters.value.memoryName = [];
    fetchSolutions();
  } else {
    filters.value.memoryName = [];
    fetchSolutions();
  }
};

// Handle individual Memory checkbox change
const handleMemoryChange = () => {
  if (filters.value.memoryName.length === 0) {
    selectAllMemory.value = true;
  } else if (filters.value.memoryName.length === memoryOptions.length) {
    selectAllMemory.value = true;
  } else {
    selectAllMemory.value = false;
  }
  fetchSolutions();
};

// Fetch solutions when component is mounted
onMounted(() => {
  console.log('AllSolutions mounted.');
  fetchSolutions();
});
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=PingFang+SC:wght@400;500;700&display=swap');

.all-solutions {
  display: flex;
  width: 100%;
  margin: 0 auto;
  position: absolute;
  top: 61px;
  left: 0;
  right: 0;
  bottom: 0;
  font-family: 'PingFang SC', sans-serif;
  background-size: cover;
  z-index: -1;
  background-color: rgb(243, 245, 248); /* Add background color */
}

.filters {
  flex: 0 0 15%; /* Adjusted width to make it narrower */
  padding: 20px;
  padding-right: 20px;
  border-right: 1px solid #ddd;
  box-sizing: border-box;
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow-y: auto;
  background-color: rgba(249, 249, 249, 0.8);
  border-radius: 8px;
  z-index: 1;
  overflow-x: hidden;
}

.price {
  font-size: 1.0em;
  font-weight: bold;
  color: #000000; /* A red color to highlight the price */
  padding-top: 15px;
}

.secondary-text {
  font-size: 0.8em;
  color: #888;
  margin-bottom: 10px;
  white-space: nowrap; /* Prevent text from wrapping */
  overflow: hidden; /* Hide overflow text */
  text-overflow: ellipsis; /* Add ellipsis for overflow text */
}

.styled-checkbox-group {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.styled-checkbox-group .el-checkbox {
  display: flex;
  align-items: center;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: #f9f9f9;
  transition: background-color 0.3s ease, border-color 0.3s ease;
}

.styled-checkbox-group .el-checkbox:hover {
  background-color: #f0f0f0;
  border-color: #ccc;
}

.styled-checkbox-group .el-checkbox__input.is-checked + .el-checkbox__label {
  background-color: #e0f7fa; /* Change the background color when checked */
  border-color: #00acc1;
}

.styled-checkbox-group .el-checkbox__label {
  margin-left: 10px;
  font-size: 1em;
  color: #333;
}

.styled-checkbox-group .el-checkbox__input {
  transform: scale(1.5); /* Increase the size of the checkbox */
}

.el-checkbox-group {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.el-checkbox {
  font-size: 1rem;
}

.solutions {
  flex: 1;
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  padding: 30px;
  overflow-y: auto;
  height: auto;
  z-index: 1;
}

.filters h3 {
  font-size: 1.5em;
  margin-bottom: 20px;
  color: #333;
}

.filter-item {
  margin-bottom: 20px;
  width: 100%;
  text-align: left;
}

.description {
  color: #6c757d;
}

.filter-item label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
  color: #555;
}

.filter-item input,
.filter-item select {
  width: 100%;
  padding: 10px;
  font-size: 1em;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: border-color 0.3s ease;
}

.filter-item input:focus,
.filter-item select:focus {
  border-color: #007bff;
  outline: none;
}

.filter-item input::placeholder {
  color: #aaa;
}

.filter-item select {
  appearance: none;
  background: url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxMCIgaGVpZ2h0PSI2Ij4KICA8cGF0aCBkPSJNIDAgMCAwIDIgNSA2IDAgMTAgMCA4IDQgNiAwIDIgMCAwIFoiIGZpbGw9IiM3Nzc3NzciLz4KPC9zdmc+Cg==') no-repeat right 10px center;
  background-size: 10px 6px;
}

.filter-item select::-ms-expand {
  display: none;
}

.filter-item div {
  display: flex;
  gap: 10px;
}

.solution-card {
  background: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  height: 450px;
  width: calc(20% - 20px);
  box-sizing: border-box;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  margin-bottom: 10px;
  color: #333;
  text-decoration: none;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.solution-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.solution-card img {
  width: 100%;
  height: 55%;
  border-radius: 8px;
  object-fit: cover;
}

.solution-info h4 {
  font-size: 1.2em;
  margin-bottom: 10px;
}

.solution-info p {
  margin: 5px 0;
}

@media (max-width: 768px) {
  .all-solutions {
    flex-direction: column;
  }

  .filters {
    max-width: 100%;
    border-right: none;
    margin-bottom: 20px;
  }

  .solutions {
    flex: 1;
  }

  .solution-card {
    width: calc(50% - 20px);
  }
}

@media (max-width: 480px) {
  .solution-card {
    width: 100%;
  }
}
</style>