<script setup>
import {onMounted, ref} from 'vue';
import {getCPUById} from "../api/CPU.ts";
import {getMotherboardById} from "../api/Motherboard.ts";
import {getMemoryById} from "../api/Memory.ts";
import {getHarddiskById} from "../api/HardDisk.ts";
import {getGPUById} from "../api/GPU.ts";
import {getPowersupplyById} from "../api/PowerSupply.ts";
import {getChassisById} from "../api/Chassis.ts";
import {getDisplayById} from "../api/Display.ts";
import {getCoolingById} from "../api/Cooling.ts";
import router from "../router/index.ts";
import Toast from "../components/Toast.vue";
import ConfirmDialog from '../components/ConfirmDialog.vue';


const totalPrice = ref(0);
const toastRef = ref(null);
const confirmDialogRef = ref(null);


  // 定义硬件数据结构
const hardwareConfig = ref([
  { key: 'cpu', name: 'CPU', defaultIcon:"/src/assets/icons/cpu.svg", details: null},
  { key: 'motherboard', name: '主板', defaultIcon:'/src/assets/icons/motherboard.svg', details: null},
  { key: 'gpu', name: '显卡', defaultIcon:'src/assets/icons/gpu.svg', details: null},
  { key: 'memory', name: '内存', defaultIcon:'src/assets/icons/memory.svg', details: null},
  { key: 'harddisk', name: '硬盘', defaultIcon:'src/assets/icons/harddisk.svg', details: null},
  { key: 'powersupply', name: '电源', defaultIcon:'src/assets/icons/power.svg', details: null},
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
    case 'harddisk': return await getHarddiskById(id);
    case 'gpu': return await getGPUById(id);
    case 'powersupply': return await getPowersupplyById(id);
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

const resetConfig = () => {
  hardwareConfig.value.forEach(item => {
    sessionStorage.removeItem(item.key);
    item.details = null;
  });
  calculateTotalPrice();
  toastRef.value.show('已重置所有配置');
};

const confirmReset = async () => {
  const confirmed = await confirmDialogRef.value.show(
    '确认重置',
    '确定要重置所有配置吗？这将清空所有已选择的配件。'
  );
  if (confirmed) {
    resetConfig();
  }
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
  <div class="self-service">
    <!-- 左侧配置列表 -->
    <div class="left-panel">
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
      <div class="button-container">
        <div class="button-group" :class="{ 'has-clear': item.details }">
          <button 
            v-if="item.details"
            class="clear-button" 
            @click="clearSelection(item)"
          >
            清空
          </button>
          <button 
            class="action-button" 
            @click="gotoSelectPage(item.key)"
          >
            {{ item.details ? '更换' : '选择' }}
          </button>
        </div>
      </div>
    </div>
  </div>

    </div>
    <!-- 右侧配置详情 -->
    <div class="right-panel">
      <div class="compatibility-panel">
        <h3>硬件兼容性检查</h3>
        <div class="compatibility-content">
          <!-- 这里后续添加兼容性检查的具体内容 -->
          <p>暂无兼容性问题</p>
        </div>
      </div>
      <div class="summary-panel">
        <div class="total-price-container">
          <div class="total-price">
            <span class="total-label">总价</span>
            <span class="price-value">￥{{ totalPrice }}</span>
          </div>
        </div>
        <div class="action-buttons">
          <button class="reset-btn" @click="confirmReset">
            <span class="reset-text">重置配置</span>
          </button>
          <button class="save-btn">保存配置</button>
        </div>
      </div>
    </div>
    <ConfirmDialog ref="confirmDialogRef" />
  </div>
</template>

<style scoped>
.self-service {
  position: fixed;
  top: 60px; /* 导航栏高度 */
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  
}

.left-panel {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  overflow-x: hidden;
  margin-top: 30px
}

.right-panel {
  width: 300px;
  background-color: white;
  border-left: 1px solid #e0e0e0;
  display: flex;
  flex-direction: column;
  padding: 20px;
  margin-top: 30px;

}
:host {
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
}

.hardware-list {
 
  display: flex;
  flex-direction: column;
  gap: 20px;
  min-width: 800px;
  align-items: center;
  
  
  
  
}
.hardware-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 700px;
  position: relative;
  
  
  
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
.button-container {
  width: 180px;
  display: flex;
  justify-content: flex-end;
}
.button-group {
  display: flex;
  gap: 10px;
  align-items: center;
  width: 80px;
}
.button-group.has-clear {
  width: 170px;
}
.clear-button {
  background-color: #d3d3d3;
  color: #000;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  min-width: 80px;
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

.compatibility, .total-price {
  margin-bottom: 20px;
}

.compatibility-panel {
  height: 300px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 20px;
  background-color: #f8f8f8;
}

.compatibility-panel h3 {
  margin: 0 0 15px 0;
  color: #333;
}

.compatibility-content {
  height: calc(100% - 40px);
  overflow-y: auto;
}

.bottom-panel {
  margin-top: auto;
}

.total-price {
  background-color: #f8f8f8;
  padding: 15px;
  border-radius: 8px;
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.price-value {
  color: #ff4d4f;
  font-size: 20px;
  font-weight: bold;
}

.action-buttons {
  display: flex;
  gap: 10px;
}

.reset-btn, .save-btn {
  flex: 1;
  padding: 12px;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: bold;
  transition: all 0.3s ease;
}

.reset-btn {
  background-color: #f5f5f5;
  color: #333;
}

.save-btn {
  background-color: #007bff;
  color: white;
}

.reset-btn:hover {
  background-color: #e0e0e0;
}

.save-btn:hover {
  background-color: #0056b3;
}

.summary-panel {
  margin-top: 80px;
  padding: 0;
}

.total-price-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.total-price {
  background-color: #f8f8f8;
  padding: 15px 30px;
  border-radius: 8px;
  width: fit-content;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.total-label {
  font-size: 16px;
  font-weight: bold;
  color: #333;
}

.price-value {
  color: #ff4d4f;
  font-size: 22px;
  font-weight: bold;
  margin-left: 20px;
}

.action-buttons {
  display: flex;
  gap: 15px;
  justify-content: center;
  margin-top: 15px;
}

.reset-btn, .save-btn {
  padding: 12px 35px; 
  border-radius: 6px;
  cursor: pointer;
  font-size: 16px; /* 增加字体大小 */
  min-width: 120px; /* 增加最小宽度 */
  font-weight: bold; /* 加粗字体 */
  transition: all 0.3s ease;
}
</style>
