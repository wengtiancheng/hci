<script setup>
import { ref } from 'vue';

const totalPrice = ref(0);

const saveConfig = () => {
  // 保存配置逻辑
};
</script>
<template>
  <div class="right-panel">
    <div class="compatibility">
      <h3>硬件兼容性</h3>
      <!-- 兼容性检查逻辑 -->
    </div>
    <div class="total-price">
      <h3>总价: {{ totalPrice }}</h3>
    </div>
    <button @click="saveConfig">保存</button>
  </div>
</template>



<style scoped>
.right-panel {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
}
.compatibility, .total-price {
  margin-bottom: 20px;
}
</style>