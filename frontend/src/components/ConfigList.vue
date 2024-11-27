<script setup lang="js">
import {onMounted, ref} from 'vue';
import {getCPUById} from "../api/CPU.ts";
import {getMotherboardById} from "../api/Motherboard.ts";
import {getMemoryById} from "../api/Memory.ts";
import {getHarddiskById} from "../api/Harddisk.ts";
import {getGPUById} from "../api/GPU.ts";
import {getPowersupplyById} from "../api/Powersupply.ts";
import {getChassisById} from "../api/Chassis.ts";
import {getDisplayById} from "../api/Display.ts";
import {getCoolingById} from "../api/Cooling.ts";
import router from "../router/index.ts";
import Toast from "../components/Toast.vue";


const totalPrice = ref(0);
const toastRef = ref(null);


  // 定义硬件数据结构
const hardwareConfig = ref([
  { key: 'cpu', name: 'CPU', defaultIcon:"/src/assets/icons/cpu.svg", details: null},
  { key: 'motherboard', name: '主板', defaultIcon:'/src/assets/icons/cpu.svg', details: null},
  { key: 'gpu', name: '显卡', defaultIcon:'src/assets/icons/gpu.svg', details: null},
  { key: 'memory', name: '内存', defaultIcon:'src/assets/icons/memory.svg', details: null},
  { key: 'hardDisk', name: '硬盘', defaultIcon:'src/assets/icons/harddisk.svg', details: null},
  { key: 'powerSupply', name: '电源', defaultIcon:'src/assets/icons/power.svg', details: null},
  { key: 'chassis', name: '机箱', defaultIcon:'src/assets/icons/chassis.svg', details: null},
  { key: 'display', name: '显示器', defaultIcon:'src/assets/icons/display.svg', details: null},
  { key: 'cooling', name: '散热器', defaultIcon:'src/assets/icons/cooling.svg', details: null}
  ]);

  // 计算总价的方法
const calculateTotalPrice = () => {
  totalPrice.value = hardwareConfig.value.reduce((sum, item) => {
    return sum + (item.details?.price || 0);
  }, 0);
};

  // 获取硬件详情的方法
const fetchHardwareDetails = async () => {
  for (const item of hardwareConfig.value) {
    const id = sessionStorage.getItem(item.key);
    if (id) {
      item.details = await getHardwareDetailsById(item.key, id);
      console.log(item.details);
    }
  }
  calculateTotalPrice(); // 获取完数据后计算总价
};

  // 根据硬件类型和 ID 获取详情
const getHardwareDetailsById = async (type, id) => {
  switch (type) {
    case 'cpu': return await getCPUById(id);
    case 'motherboard': return await getMotherboardById(id);
    case 'memory': return await getMemoryById(id);
    case 'hardDisk': return await getHarddiskById(id);
    case 'gpu': return await getGPUById(id);
    case 'powerSupply': return await getPowersupplyById(id);
    case 'chassis': return await getChassisById(id);
    case 'display': return await getDisplayById(id);
    case 'cooling': return await getCoolingById(id);
    default: return null;
  }
};

  // 跳转到选择页面
const gotoSelectPage = (key) => {
  router.push({path: `/select/${key}`});
};

// 添加清空选择的方法
const clearSelection = (item) => {
  sessionStorage.removeItem(item.key);
  item.details = null;
  calculateTotalPrice();
  toastRef.value.show(`已清空${item.name}`);
};

onMounted(() => {
  fetchHardwareDetails();
   // 检查是否需要显示提示
   const messageInfo = sessionStorage.getItem('showSuccessMessage');
  if (messageInfo) {
    const { type, name, action } = JSON.parse(messageInfo);
    const message = action === 'select' 
      ? `已选择${type}：${name}`
      : `已更换${type}：${name}`;
    toastRef.value.show(message);
    // 显示后清除标记
    sessionStorage.removeItem('showSuccessMessage');
  }
});
</script>

<template>
  <div class="hardware-list">
    <Toast ref="toastRef" />
    <div v-for="item in hardwareConfig" :key="item.key" class="hardware-item">
      <div class="hardware-info">
        <h3 class="hardware-title">{{ item.name }}</h3>
        <img :src="item.details?.imageUrl || item.defaultIcon" alt="Hardware Image" class="hardware-image" />
        <div class="details-container">
          <div class="product-info">
            <span class="product-name">{{ item.details ? item.details.name : '未选择' }}</span>
            <span v-if="item.details" class="price">￥{{ item.details.price }}</span>
          </div>
        </div>
      </div>
      <div class="button-group">
        <button 
          class="action-button" 
          @click="gotoSelectPage(item.key)"
        >
          {{ item.details ? '更换' : '选择' }}
        </button>
        <button 
          v-if="item.details"
          class="clear-button" 
          @click="clearSelection(item)"
        >
          清空
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
:host {
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
}

.hardware-list {
  margin-left: 0;
  display: flex;
  flex-direction: column;
  gap: 20px;
  min-width: 800px;
  justify-content: flex-start;
  align-items: flex-start;
}
.hardware-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  min-width: 600px;
}
.hardware-info {
  display: flex;
  gap: 30px;
  align-items: center;
}
.hardware-image {
  width: 80px;
  height: 80px;
  object-fit: contain;
  border-radius: 5px;
  background-color: #f8f8f8;
}
.button-group {
  display: flex;
  gap: 10px;
  align-items: center;
}
.action-button {
  background-color: #007bff;
  color: #fff;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  min-width: 80px;
}
.action-button:hover {
  background-color: #0056b3;
}
.clear-button {
  background-color: #6c757d;
  color: #fff;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  min-width: 80px;
}
.clear-button:hover {
  background-color: #5a6268;
}
.hardware-title {
  min-width: 80px;
  text-align: left;
  margin: 0;
}

.details-container {
  min-width: 300px;
}

.product-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
}

.product-name {
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 200px;
}

.price {
  margin: 0;
  color: #ff4d4f;
  font-weight: bold;
  white-space: nowrap;
  margin-right: 30px;
}

.total-price {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 20px;
  padding: 15px;
  background-color: #f8f8f8;
  border-radius: 8px;
  width: 100%;
}
</style>
