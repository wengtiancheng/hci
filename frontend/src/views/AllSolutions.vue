<template>
  <div class="all-solutions">
    <!-- 左侧过滤器 -->
    <div class="filters">
      <h3>过滤器</h3>

      <!-- 总价区间筛选 -->
      <div class="filter-item">
        <label>价格范围</label>
        <vue-slider v-model="sliderValue" :min="0" :max="99999"
                    :tooltip="'active'" :tooltip-placement="['bottom', 'bottom']"
                    @change="sliderChange" ></vue-slider>
      </div>

      <!-- 排序方式 -->
      <div class="filter-item">
        <label for="sort-order">排序方式</label>
        <select id="sort-order" v-model="filters.sortBy" @change="fetchSolutions">
          <option :value="SortType.NONE">默认排序</option>
          <option :value="SortType.PRICE_DESC">按价格降序</option>
          <option :value="SortType.SAVE_DESC">按收藏数降序</option>
          <option :value="SortType.CREATE_TIME_DESC">按创建时间降序</option>
        </select>
      </div>

      <!-- CPU类型筛选 -->
      <div class="filter-item">
        <label for="cpu" style="font-size: 20px; font-family: 'Microsoft YaHei UI',serif">CPU 类型</label>
        <el-checkbox-group v-model="filters.cpuName" @change="fetchSolutions">
          <el-checkbox label="AMD Ryzen7 7800X3D">AMD Ryzen7 7800X3D</el-checkbox>
          <el-checkbox label="i5 12600KF">i5 12600KF</el-checkbox>
          <el-checkbox label="i7 13700K">i7 13700K</el-checkbox>
          <!-- 更多选项 -->
        </el-checkbox-group>
      </div>

      <!-- GPU类型筛选 -->
      <div class="filter-item">
        <label for="gpu" style="font-size: 20px; font-family: 'Microsoft YaHei UI',serif">GPU 类型</label>
        <el-checkbox-group v-model="filters.gpuName" @change="fetchSolutions">
          <el-checkbox label="RTX3080">RTX3080</el-checkbox>
          <el-checkbox label="RX6750">RX6750</el-checkbox>
          <el-checkbox label="RTX4060Ti">RTX4060Ti</el-checkbox>
          <el-checkbox label="RX7800XT">RX7800XT</el-checkbox>
        </el-checkbox-group>
      </div>

      <!-- 主板类型筛选 -->
      <div class="filter-item">
        <label for="motherboard" style="font-size: 20px; font-family: 'Microsoft YaHei UI',serif">主板类型</label>
        <el-checkbox-group v-model="filters.motherboardName" @change="fetchSolutions">
          <el-checkbox label="B550">B550</el-checkbox>
          <el-checkbox label="B560">B560</el-checkbox>
        </el-checkbox-group>
      </div>

      <!-- 内存类型筛选 -->
      <div class="filter-item">
        <label for="memory" style="font-size: 20px; font-family: 'Microsoft YaHei UI',serif">内存类型</label>
        <el-checkbox-group v-model="filters.memoryName" @change="fetchSolutions">
          <el-checkbox label="DDR4">DDR4</el-checkbox>
          <el-checkbox label="DDR5">DDR5</el-checkbox>
        </el-checkbox-group>
      </div>

    </div>

    <!-- 右侧装机方案卡片 -->
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
          <p class="secondary-text">收藏次数：{{ solution.saveNum }}</p>
          <p class="secondary-text">创建时间：{{ new Date(solution.createTime).toLocaleDateString() }}</p>
          <p class="price">价格：￥{{ solution.totalPrice }}</p>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import {getAllSolution, SolutionVO, SortType, Filters, initFilters, getSolution} from '../api/Solution';
import {useRoute, useRouter} from 'vue-router';

const router = useRouter();
const editSolution = (solutionId: number) => {
  router.push({ path: '/custom-build', query: { solution: JSON.stringify(getSolution(solutionId)) } });
};

// 定义过滤器的状态
const filters = ref<Filters>(initFilters);

const sliderValue = ref([0, 99999])

// 装机方案列表
const solutions = ref<SolutionVO[]>([]);

// 获取路由参数
const route = useRoute();
if (route.query.filters) {
  filters.value = JSON.parse(route.query.filters as string); // 解析 filters
}

// 价格区间滑动条的回调函数
const sliderChange = async (value: number[]) => {
  filters.value.lowPrice = value[0];
  filters.value.highPrice = value[1];
  console.log('Change slider:', filters.value);
  solutions.value = await getAllSolution(filters.value);
}

// 获取所有装机方案的方法
const fetchSolutions = async () => {
  console.log('Fetching solutions with filters:', filters.value);
  solutions.value = await getAllSolution(filters.value);
};

// 当组件挂载时获取装机方案
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
  top: 78px;
  left: 0;
  right: 0;
  bottom: 0;
  font-family: 'PingFang SC', sans-serif;
  background: url('../assets/images/allSolutionsbg.png') no-repeat center center fixed;
  background-size: cover;
  z-index: -1;
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
  font-size: 1.4em;
  font-weight: bold;
  color: #006dba; /* A red color to highlight the price */
  padding-top: 15px;
}

.secondary-text {
  font-size: 0.8em;
  color: #888; /* A lighter color to make the text less prominent */
  margin-bottom: 10px; /* Add margin to the bottom of secondary text */
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