<template>
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
import { getAllSolution, getAllSolutionNofilter, SolutionVO } from '../api/Solution';

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
  // solutions.value = await getAllSolution(filters.value);
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
/* 页面布局样式 */
.all-solutions {
  display: flex;
}

/* 左侧过滤器样式 */
.filters {
  width: 20%;
  padding: 15px;
  background-color: #f4f4f4;
  border-right: 1px solid #ddd;
}

.filter-item {
  margin-bottom: 20px;
}

.filter-item input {
  width: 45%;
  margin-right: 5%;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.filter-item select {
  width: 100%;
  padding: 5px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

/* 右侧装机方案卡片样式 */
.solutions {
  width: 80%;
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  padding: 15px;
}

.solution-card {
  width: calc(33.33% - 20px);
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
}

.solution-image {
  width: 100%;
  height: 150px;
  object-fit: cover;
  border-bottom: 1px solid #ddd;
  margin-bottom: 10px;
}

.solution-info {
  margin-top: 10px;
}

.solution-info h4 {
  margin: 0 0 10px;
  font-size: 16px;
  color: #333;
}

.solution-info p {
  margin: 5px 0;
  font-size: 14px;
  color: #666;
}
</style>