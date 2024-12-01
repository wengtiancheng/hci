<template>

  <div class="all-solutions">
    <!-- 左侧过滤器 -->
    <div class="filters">
      <h3>过滤器</h3>

      <!-- 总价区间筛选 -->
      <div class="filter-item">
        <label>价格范围</label>
        <div>
          <input type="number" v-model="filters.lowPrice" placeholder="最低价" @input="fetchSolutions" />
          <input type="number" v-model="filters.highPrice" placeholder="最高价" @input="fetchSolutions" />
        </div>
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
        <label for="cpu">CPU 类型</label>
        <select id="cpu" v-model="filters.cpuName" @change="fetchSolutions">
          <option value="">所有</option>
          <option value="AMD 锐龙 5 7600X">AMD 锐龙 5 7600X</option>
          <option value="Intel Core i7-12700KF">Intel Core i7-12700KF</option>
          <!-- 更多选项 -->
        </select>
      </div>

      <!-- GPU类型筛选 -->
      <div class="filter-item">
        <label for="gpu">GPU 类型</label>
        <select id="gpu" v-model="filters.gpuName" @change="fetchSolutions">
          <option value="">所有</option>
          <option value="RTX 3080">RTX 3080</option>
          <option value="RX 6750">RX 6750</option>
          <!-- 更多选项 -->
        </select>
      </div>

      <!-- 主板类型筛选 -->
      <div class="filter-item">
        <label for="motherboard">主板类型</label>
        <select id="motherboard" v-model="filters.motherboardName" @change="fetchSolutions">
          <option value="">所有</option>
          <option value="B550">B550</option>
          <option value="B560">B560</option>
          <!-- 更多选项 -->
        </select>
      </div>

      <!-- 内存类型筛选 -->
      <div class="filter-item">
        <label for="memory">内存类型</label>
        <select id="memory" v-model="filters.memoryName" @change="fetchSolutions">
          <option value="">所有</option>
          <option value="DDR4">DDR4</option>
          <option value="DDR5">DDR5</option>
          <!-- 更多选项 -->
        </select>
      </div>

    </div>

    <!-- 右侧装机方案卡片 -->
    <div class="solutions">
      <router-link
        v-for="solution in solutions"
        :key="solution.id"
        :to="{ path: `/solution/${solution.id}` }"
        class="solution-card"
      >
        <img :src="solution.imageUrl" alt="方案图片" class="solution-image" style="object-fit: cover;" />
        <div class="solution-info">
          <h4>{{ solution.name }}</h4>
          <p>{{ solution.description }}</p>
          <p>价格：￥{{ solution.totalPrice }}</p>
          <p>保存次数：{{ solution.saveNum }}</p>
          <p>创建时间：{{ new Date(solution.createTime).toLocaleDateString() }}</p>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getAllSolution, SolutionVO, SortType, Filters, initFilters } from '../api/Solution';
import { useRoute } from 'vue-router';

// 定义过滤器的状态
const filters = ref<Filters>(initFilters);

// 装机方案列表
const solutions = ref<SolutionVO[]>([]);

// 获取路由参数
const route = useRoute();
if (route.query.filters) {
  filters.value = JSON.parse(route.query.filters as string); // 解析 filters
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
.all-solutions {
  display: flex;
  width: 100%; /* Set width to 100% */
  margin: 0 auto; /* Center align */
  position: absolute;
  top: 80px;
  left: 0;
  right: 0;
  bottom: 0; /* Ensure it takes the full height */
}

.filters {
  flex: 0 0 20%; /* Fixed width to 30% of the page width */
  padding: 20px;
  padding-right: 40px; /* Add padding to the right */
  border-right: 1px solid #ddd;
  box-sizing: border-box; /* Include padding and border in the element's total width and height */
  text-align: center; /* Center the content */
  display: flex;
  flex-direction: column;
  align-items: center; /* Center align items horizontally */
  overflow-y: auto; /* Enable vertical scrolling */
  height: 100vh; /* Full viewport height */
  background-color: #f9f9f9; /* Light background color */
  border-radius: 8px; /* Rounded corners */
}

.solutions {
  flex: 1; /* Take the remaining width */
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  padding: 20px;
  overflow-y: auto; /* Enable vertical scrolling */
  height: 100vh; /* Full viewport height */
}

.filters h3 {
  font-size: 1.5em;
  margin-bottom: 20px;
  color: #333; /* Darker text color */
}

.filter-item {
  margin-bottom: 20px;
  width: 100%; /* Full width */
  text-align: left; /* Align text to the left */
}

.filter-item label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold; /* Bold labels */
  color: #555; /* Slightly darker text color */
}

.filter-item input,
.filter-item select {
  width: 100%;
  padding: 10px; /* Increased padding */
  font-size: 1em;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1); /* Inner shadow for depth */
  transition: border-color 0.3s ease; /* Smooth transition for border color */
}

.filter-item input:focus,
.filter-item select:focus {
  border-color: #007bff; /* Blue border on focus */
  outline: none; /* Remove default outline */
}

.filter-item input::placeholder {
  color: #aaa; /* Lighter placeholder color */
}

.filter-item select {
  appearance: none; /* Remove default arrow */
  background: url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIxMCIgaGVpZ2h0PSI2Ij4KICA8cGF0aCBkPSJNIDAgMCAwIDIgNSA2IDAgMTAgMCA4IDQgNiAwIDIgMCAwIFoiIGZpbGw9IiM3Nzc3NzciLz4KPC9zdmc+Cg==') no-repeat right 10px center; /* Custom arrow */
  background-size: 10px 6px; /* Size of the custom arrow */
}

.filter-item select::-ms-expand {
  display: none; /* Remove default arrow in IE */
}

.filter-item div {
  display: flex;
  gap: 10px; /* Add spacing between the input fields */
}

.solution-card {
  background: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  height: 450px;
  width: calc(20% - 20px); /* Adjust width to 25% for 4 cards per row */
  box-sizing: border-box;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  color: #333; /* 默认字体颜色 */
  text-decoration: none; /* 去掉链接下划线 */
}

.solution-card:hover {
  color: blue; /* 鼠标悬停时字体颜色变为蓝色 */
}

.solution-card img {
  width: 100%;
  height: 200px; /* 设置固定高度 */
  border-radius: 8px;
  object-fit: cover; /* 添加 object-fit 属性 */
}

.solution-info {
  margin-top: 10px;
}

.solution-info h4 {
  font-size: 1.2em;
  margin-bottom: 10px;
}

.solution-info p {
  margin: 5px 0;
}

/* 响应式设计 */
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