<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { getAllSolution, getAllSolutionNofilter, SolutionVO } from '../api/Solution.ts';

// 定义过滤器状态
const filters = ref({
  minPrice: null, // 最低价格
  maxPrice: null, // 最高价格
  cpuId: null,    // CPU 类型
  sortBy: null,   // 排序字段
  sortOrder: 'asc', // 排序方式，asc 或 desc
});

// 装机方案列表
const solutionList = ref<SolutionVO[]>([]);

// 获取所有装机方案
const fetchSolutions = async () => {
  solutionList.value = await getAllSolutionNofilter();
};

// 当组件加载时，获取方案数据
onMounted(() => {
  fetchSolutions();
});
</script>

<template>
  <div class="all-solutions">
    <div class="filters">
      <h3>过滤器</h3>
      <!-- CPU过滤器 -->
      <div class="filter-item">
        <label for="cpu">CPU</label>
        <select id="cpu" v-model="filters.cpuId" @change="fetchSolutions">
          <option value="">所有</option>
          <!-- 填充 CPU 选项 -->
          <option value="1">Intel i7</option>
          <option value="2">AMD Ryzen 7</option>
        </select>
      </div>

<!--      &lt;!&ndash; GPU过滤器 &ndash;&gt;-->
<!--      <div class="filter-item">-->
<!--        <label for="gpu">GPU</label>-->
<!--        <select id="gpu" v-model="filters.gpuId" @change="fetchSolutions">-->
<!--          <option value="">所有</option>-->
<!--          &lt;!&ndash; 填充 GPU 选项 &ndash;&gt;-->
<!--          <option value="1">NVIDIA RTX 3060</option>-->
<!--          <option value="2">AMD RX 6800</option>-->
<!--        </select>-->
<!--      </div>-->

      <!-- 其他过滤器如内存、硬盘等 -->
    </div>

    <div class="solutions">
      <div v-for="solution in solutionList" :key="solution.id" class="solution-card">
        <img :src="solution.imageUrl" alt="方案图片" class="solution-image" />
        <div class="solution-info">
          <h4>{{ solution.name }}</h4>
          <p>{{ solution.description }}</p>
          <p>价格：￥{{ solution.totalPrice }}</p>
          <p>保存次数：{{ solution.saveNum }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.all-solutions {
  display: flex;
}

.filters {
  width: 20%;
  padding: 10px;
  background-color: #f4f4f4;
}

.filter-item {
  margin-bottom: 15px;
}

.solutions {
  width: 80%;
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.solution-card {
  width: calc(33.33% - 20px);
  background-color: #fff;
  border: 1px solid #ddd;
  padding: 10px;
  box-sizing: border-box;
}

.solution-image {
  width: 100%;
  height: auto;
}

.solution-info {
  margin-top: 10px;
}
</style>