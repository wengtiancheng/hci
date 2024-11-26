<template>
  <!-- 导航栏 -->
  <Header />

  <div class="all-solutions">
    <!-- 左侧过滤器 -->
    <div class="filters">
      <h3>过滤器</h3>

      <!-- 总价区间筛选 -->
      <div class="filter-item">
        <label>价格范围</label>
        <div>
          <input type="number" v-model="filters.minPrice" placeholder="最低价" @input="fetchSolutions" />
          <input type="number" v-model="filters.maxPrice" placeholder="最高价" @input="fetchSolutions" />
        </div>
      </div>

      <!-- CPU类型筛选 -->
      <div class="filter-item">
        <label for="cpu">CPU 类型</label>
        <select id="cpu" v-model="filters.cpuId" @change="fetchSolutions">
          <option value="">所有</option>
          <option value="1">Intel i7</option>
          <option value="2">AMD Ryzen 7</option>
          <!-- 更多选项 -->
        </select>
      </div>

      <!-- 排序字段 -->
      <div class="filter-item">
        <label for="sort-by">排序字段</label>
        <select id="sort-by" v-model="filters.sortBy" @change="fetchSolutions">
          <option value="">默认</option>
          <option value="createTime">创建时间</option>
          <option value="saveNum">收藏数</option>
        </select>
      </div>

      <!-- 排序方式 -->
      <div class="filter-item">
        <label for="sort-order">排序方式</label>
        <select id="sort-order" v-model="filters.sortOrder" @change="fetchSolutions">
          <option value="asc">升序</option>
          <option value="desc">降序</option>
        </select>
      </div>
    </div>

    <!-- 右侧装机方案卡片 -->
    <div class="solutions">
      <div v-for="solution in solutions" :key="solution.id" class="solution-card">
        <img :src="solution.imageUrl" alt="方案图片" class="solution-image" />
        <div class="solution-info">
          <h4>{{ solution.name }}</h4>
          <p>{{ solution.description }}</p>
          <p>价格：￥{{ solution.totalPrice }}</p>
          <p>保存次数：{{ solution.saveNum }}</p>
          <p>创建时间：{{ new Date(solution.createTime).toLocaleDateString() }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getAllSolutionNofilter, SolutionVO } from '../api/Solution';
import Header from '../components/Header.vue';

// 定义过滤器的状态
const filters = ref({
  minPrice: null, // 最低价格
  maxPrice: null, // 最高价格
  cpuId: null,    // CPU 类型
  sortBy: null,   // 排序字段
  sortOrder: 'asc', // 排序方式（asc 或 desc）
});

// 装机方案列表
const solutions = ref<SolutionVO[]>([]);

// 获取所有装机方案的方法
const fetchSolutions = async () => {
  solutions.value = await getAllSolutionNofilter();
  console.log(solutions);
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
  width: calc(20% - 20px); /* Adjust width to 25% for 4 cards per row */
  box-sizing: border-box;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.solution-card img {
  width: 100%;
  height: auto;
  border-radius: 8px;
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