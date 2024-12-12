<script setup>
import {onMounted, ref, onBeforeUnmount, watch} from 'vue';
import {getCPUById} from "../api/CPU.ts";
import {getMotherboardById} from "../api/Motherboard.ts";
import {getMemoryById} from "../api/Memory.ts";
import {getHarddiskById} from "../api/Harddisk.ts";
import {getGPUById} from "../api/GPU.ts";
import {getPowersupplyById} from "../api/Powersupply.ts";
import {getChassisById} from "../api/Chassis.ts";
import {getDisplayById} from "../api/Display.ts";
import {getCoolingById} from "../api/Cooling.ts";
import {uploadSolution} from "../api/Solution.ts";
import router from "../router/index.ts";
import Toast from "../components/Toast.vue";
import ConfirmDialog from '../components/ConfirmDialog.vue';
import Header from '../components/Header.vue';


const totalPrice = ref(0);
const toastRef = ref(null);
const confirmDialogRef = ref(null);
const compatibilityIssues = ref([]);
const token = sessionStorage.getItem('token');
const isLoading = ref(true);

// 添加滚动位置相关的变量和方法
const leftPanelRef = ref(null);

// 保存滚动位置
const saveScrollPosition = () => {
  if (leftPanelRef.value) {
    const scrollTop = leftPanelRef.value.scrollTop;
    sessionStorage.setItem('selfServiceScrollPosition', scrollTop.toString());
  }
};

// 恢复滚动位置
const restoreScrollPosition = () => {
  const savedPosition = sessionStorage.getItem('selfServiceScrollPosition');
  if (savedPosition && leftPanelRef.value) {
    leftPanelRef.value.scrollTop = parseInt(savedPosition);
  }
};

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

// 计算总价的
const calculateTotalPrice = () => {
  totalPrice.value = hardwareConfig.value.reduce((sum, item) => {
    return sum + (item.details?.price || 0);
  }, 0);
};

// 获取硬件详情的方法
const fetchHardwareDetails = async () => {
  try {
    for (const item of hardwareConfig.value) {
      const id = sessionStorage.getItem(item.key);
      if (id) {
        item.details = await getHardwareDetailsById(item.key, id);
      }
    }
    solutionName.value = sessionStorage.getItem('solutionName') || '';
    solutionDescription.value = sessionStorage.getItem('solutionDescription') || '';
    calculateTotalPrice();
    checkCompatibility();
  } finally {
    isLoading.value = false;
  }
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
  saveScrollPosition();
  router.push({path: `/select/${key}`});
};

// 添加清空选择的方法
const clearSelection = (item) => {
  sessionStorage.removeItem(item.key);
  item.details = null;
  calculateTotalPrice();
  checkCompatibility();
  toastRef.value.show(`已清空${item.name}`);
};

const resetConfig = () => {
  hardwareConfig.value.forEach(item => {
    sessionStorage.removeItem(item.key);
    sessionStorage.removeItem('solutionName');
    sessionStorage.removeItem('solutionDescription');
    solutionDescription.value = '';
    solutionName.value = '';
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



const checkCompatibility = () => {
  compatibilityIssues.value = [];

  // 检查 CPU 和主板的类型
  const cpu = hardwareConfig.value.find(item => item.key === 'cpu').details;
  const motherboard = hardwareConfig.value.find(item => item.key === 'motherboard').details;


  if (cpu && motherboard) {
    if (cpu.type !== motherboard.type) {
      compatibilityIssues.value.push({ cpuName: 'CPU', cpuKey: 'cpu', motherboardName: '主板', motherboardKey: 'motherboard' });
    }
  }

  // 检查主板和内存条的类型
  const memory = hardwareConfig.value.find(item => item.key === 'memory').details;

  if (motherboard && memory) {

    if (motherboard.memoryType != memory.type) {
      compatibilityIssues.value.push({ cpuName: '主板', cpuKey: 'motherboard', motherboardName: '内存', motherboardKey: 'memory' });
    }
  }
};

// 保存配置
const saveSolution = async () => {
  const solution = {
    id: parseInt(sessionStorage.getItem('id') || '0'),
    name: solutionName.value,
    imageUrl: '',
    totalPrice: totalPrice.value,
    description: solutionDescription.value,
    saveNum: -1,
    createTime: new Date(),
    cpuId: parseInt(sessionStorage.getItem('cpu') || '0'),
    motherboardId: parseInt(sessionStorage.getItem('motherboard') || '0'),
    gpuId: parseInt(sessionStorage.getItem('gpu') || '0'),
    memoryId: parseInt(sessionStorage.getItem('memory') || '0'),
    harddiskId: parseInt(sessionStorage.getItem('harddisk') || '0'),
    powersupplyId: parseInt(sessionStorage.getItem('powersupply') || '0'),
    coolingId: parseInt(sessionStorage.getItem('cooling') || '0'),
    chassisId: parseInt(sessionStorage.getItem('chassis') || '0'),
    displayId: parseInt(sessionStorage.getItem('display') || '0')
  };


  const result = await uploadSolution(solution);
  console.log(result);
  if (result.data.code === '000') {
    toastRef.value.show('保存成功');
    //等待1秒后跳转
    await new Promise(resolve => setTimeout(resolve, 1000));
    // 跳转到 MySolutions 页面
    router.push('/mysolutions');
  } else {
    toastRef.value.show('保存失败!' + result.data.msg, 'error');
  }

};

const solutionName = ref(sessionStorage.getItem('solutionName') || '');
const solutionDescription = ref(sessionStorage.getItem('solutionDescription') || '');

// 监听 solutionName 和 solutionDescription 的变化
watch(solutionName, (newValue) => {
  sessionStorage.setItem('solutionName', newValue);
});

watch(solutionDescription, (newValue) => {
  sessionStorage.setItem('solutionDescription', newValue);

});




onMounted(async () => {
  // 处理来自 SolutionDetail 的参数
  const query = router.currentRoute.value.query;
  if (query.solution) {
    const solution = JSON.parse(query.solution);
    console.log("Get Solution:" , solution);
    // 将解决方案的配件ID存储到 sessionStorage
    sessionStorage.setItem('id', solution.id);
    sessionStorage.setItem('cpu', solution.cpuId);
    sessionStorage.setItem('motherboard', solution.motherboardId);
    sessionStorage.setItem('gpu', solution.gpuId);
    sessionStorage.setItem('memory', solution.memoryId);
    sessionStorage.setItem('harddisk', solution.harddiskId);
    sessionStorage.setItem('powersupply', solution.powersupplyId);
    sessionStorage.setItem('cooling', solution.coolingId);
    sessionStorage.setItem('chassis', solution.chassisId);
    sessionStorage.setItem('display', solution.displayId);
    sessionStorage.setItem('solutionName', solution.name);
    sessionStorage.setItem('solutionDescription', solution.description);

    // solutionName.value = sessionStorage.getItem('solutionName') || '';
    // solutionDescription.value = sessionStorage.getItem('solutionDescription') || '';
    // solutionName.value = solution.name;
    // solutionDescription.value = solution.description;
  }

  // 先获取配件详情
  await fetchHardwareDetails();


  // 检查是否需要显示提示
  const messageInfo = sessionStorage.getItem('showSuccessMessage');
  if (messageInfo && toastRef.value) {  // 添加 toastRef.value 检查
    const {type, name, action} = JSON.parse(messageInfo);
    const message = action === 'select'
        ? `已选择${type}:${name}`
        : `已更换${type}:${name}`;
    toastRef.value.show(message);
    sessionStorage.removeItem('showSuccessMessage');
  }

  // 恢复滚动位置
  restoreScrollPosition();
});

// 在组件销毁前保存滚动位置
onBeforeUnmount(() => {
  saveScrollPosition();
});
</script>


<template>
  <!-- 加载动画 -->
  <div v-if="isLoading" class="loading-overlay">
    <div class="spinner">
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
    </div>

  </div>
  <div v-else class="self-service">


    <!-- 左侧配置列表 -->

    <div class="left-panel" ref="leftPanelRef">
      <div class="hardware-list">
        <Toast ref="toastRef"/>
        <div v-for="item in hardwareConfig" :key="item.key" class="hardware-item">
          <div class="hardware-info">
            <h3 class="hardware-title">
              {{ item.name }}
            </h3>
            <a v-if="item.details" :href="item.details.linkUrl" target="_blank" class="image-link">
              <img
                  :src="item.details?.imageUrl || item.defaultIcon"
                  alt="Hardware Image"
                  class="hardware-image"
              />
            </a>
            <div class="details-container">
              <div class="product-info">
                <span class="product-name">
                  <a v-if="item.details" :href="item.details.linkUrl" target="_blank"
                     class="purchase-link">{{ item.details ? item.details.name : '未选择' }}</a>
                </span>
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
      <!-- 当有现成方案时显示信息，否则显示输入框 -->
<!--      <div class="solution-info-panel">-->
<!--        <template v-if="hasSavedSolution">-->
<!--          <h3 class="solution-name">{{ solutionName }}</h3>-->
<!--          <p class="solution-description">{{ solutionDescription }}</p>-->
<!--        </template>-->
<!--        <template v-else>-->
<!--          <div class="solution-input">-->
<!--            <input-->
<!--                v-model="solutionName"-->
<!--                placeholder="请输入配置方案名称"-->
<!--                class="solution-name-input"-->
<!--            />-->
<!--            <textarea-->
<!--                v-model="solutionDescription"-->
<!--                placeholder="请输入配置方案描述"-->
<!--                class="solution-description-input"-->
<!--            ></textarea>-->
<!--          </div>-->
<!--        </template>-->
<!--      </div>-->
      <div class="solution-info-panel">
        <div class="solution-input">
          <textarea
              v-model="solutionName"
              placeholder="请输入装机方案名字"
              class="solution-name-input"
              @change="checkCompatibility"
          ></textarea>
          <textarea
              v-model="solutionDescription"
              placeholder="请输入装机方案描述"
              class="solution-description-input"
          ></textarea>
        </div>
      </div>
      <div class="compatibility-panel">
        <h3>硬件兼容性检查</h3>
        <div class="compatibility-content">
          <p v-if="compatibilityIssues.length === 0">暂无兼容性问题</p>
          <div v-else>
            <p v-for="(issue, index) in compatibilityIssues" :key="index" class="compatibility-issue">
              <img src="../assets/icons/warning.svg" alt="警告" class="warning-icon"/>
              <span @click="gotoSelectPage(issue.cpuKey)" class="clickable">{{ issue.cpuName }}</span> 和
              <span @click="gotoSelectPage(issue.motherboardKey)" class="clickable">{{ issue.motherboardName }}</span>
              不匹配
            </p>
          </div>
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
            <span class="reset-text">重置</span>
          </button>
          <button class="save-btn" @click="saveSolution">保存</button>
        </div>
      </div>
    </div>
    <ConfirmDialog ref="confirmDialogRef"/>
  </div>
</template>

<style scoped>
.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}

.spinner {
  position: absolute;
  width: 9px;
  height: 9px;
}

.spinner div {
  position: absolute;
  width: 50%;
  height: 150%;
  background: #001657;
  transform: rotate(calc(var(--rotation) * 1deg)) translate(0, calc(var(--translation) * 1%));
  animation: spinner-fzua35 1s calc(var(--delay) * 1s) infinite ease;
}

.spinner div:nth-child(1) {
  --delay: 0.1;
  --rotation: 36;
  --translation: 150;
}

.spinner div:nth-child(2) {
  --delay: 0.2;
  --rotation: 72;
  --translation: 150;
}

.spinner div:nth-child(3) {
  --delay: 0.3;
  --rotation: 108;
  --translation: 150;
}

.spinner div:nth-child(4) {
  --delay: 0.4;
  --rotation: 144;
  --translation: 150;
}

.spinner div:nth-child(5) {
  --delay: 0.5;
  --rotation: 180;
  --translation: 150;
}

.spinner div:nth-child(6) {
  --delay: 0.6;
  --rotation: 216;
  --translation: 150;
}

.spinner div:nth-child(7) {
  --delay: 0.7;
  --rotation: 252;
  --translation: 150;
}

.spinner div:nth-child(8) {
  --delay: 0.8;
  --rotation: 288;
  --translation: 150;
}

.spinner div:nth-child(9) {
  --delay: 0.9;
  --rotation: 324;
  --translation: 150;
}

.spinner div:nth-child(10) {
  --delay: 1;
  --rotation: 360;
  --translation: 150;
}

@keyframes spinner-fzua35 {
  0%, 10%, 20%, 30%, 50%, 60%, 70%, 80%, 90%, 100% {
    transform: rotate(calc(var(--rotation) * 1deg)) translate(0, calc(var(--translation) * 1%));
  }

  50% {
    transform: rotate(calc(var(--rotation) * 1deg)) translate(0, calc(var(--translation) * 1.5%));
  }
}

.self-service {
  position: fixed;
  top: 50px;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  margin: 0 auto; /* 居中对齐 */
  font-size: 0.875em;
  justify-content: space-between; /* 左右面板之间平均分配空间 */
  background-color: rgb(243, 245, 248);
}

.left-panel {
  flex: 1;
  overflow-y: scroll;
  overflow-x: hidden;
  margin-top: 2%;
  max-width: 65%;
  min-width: 800px;
  background-color: rgb(243, 245, 248);
  margin-left: 200px;
  padding-right: 10px;
}

.right-panel {
  width: 15%;
  min-width: 250px;
  max-width: 270px;
  background-color: rgb(243, 245, 248);
  display: flex;
  flex-direction: column;
  margin-right: 200px;
  margin-left: 50px;
  max-width: 20%;
  margin-top: 2%;
  height: 100%;
  padding-right: 75px; /* 左侧间距 */
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
  width: 90%;
  margin: 0 auto;
}

.hardware-item {
  width: 98%;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 15px 15px 30px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  position: relative;
  background-color: #fff;
  transition: background-color 0.3s ease, box-shadow 0.3s ease; /* Add transition for smooth effect */
}

.hardware-item:hover {
  background-color: #f0f0f0; /* Change background color on hover */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Add a larger shadow on hover */
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
  font-size: 1.25em;
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
  max-width: 300px;
  font-size: 1.125em;
}

.price {
  margin: 0;
  /*需要减去button-container的宽度*/
  right: 180px;
  position: absolute;
  color: #ff4d4f;
  font-weight: bold;
  white-space: nowrap;
  margin-right: 30px;
  font-size: 1.125em;
}

.total-price {

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
  height: 150px;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 10px;
  background-color: #fff;
  margin-bottom: 50px
}

.compatibility-panel h3 {
  margin: 0 0 15px 0;
  color: #333;
  font-size: 1.375em;
  text-align: center;
}

.compatibility-content {
  height: calc(100% - 40px);
  overflow-y: auto;
  font-size: 1em;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.bottom-panel {
  margin-top: auto;
}

.total-price {
  border: 1px solid #e0e0e0;
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
  font-size: 1.625em;
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
  font-size: 0.875em;
}

.reset-btn {
  background-color: #d3d3d3;
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
  margin-top: 0;
  padding: 0 0 15px 0;
  margin-bottom: 50px
}

.total-price-container {
  display: flex;
  justify-content: center;
  margin-bottom: 30px;
}

.total-price {
  background-color: #fff;
  padding: 20px 30px;
  border-radius: 8px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.total-label {
  font-size: 1.25em;
  font-weight: bold;
  color: #333;
}

.price-value {
  color: #ff4d4f;
  font-size: 1.625em;
  font-weight: bold;
  margin-left: 20px;
}

.action-buttons {
  display: flex;
  gap: 20px;
  justify-content: center;
  margin-top: 20px;
}

.reset-btn, .save-btn {
  padding: 15px 35px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1em;
  min-width: 120px;
  font-weight: bold;
  transition: all 0.3s ease;
}

.clickable {
  text-decoration: underline;
  cursor: pointer;
  display: inline-block;
  vertical-align: middle;
}

.compatibility-issue {
  color: red;
  font-size: 1.3em;
  display: inline-flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 10px;
  justify-content: center;
}

.warning-icon {
  width: 20px;
  height: 20px;
  flex-shrink: 0;
}

.purchase-link {
  color: black;
  text-decoration: none;
}

.purchase-link:hover {
  color: gray;
}

.solution-info-panel {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 15px;
  background-color: #fff;
  margin-bottom: 50px
}

.solution-name {
  margin: 0 0 10px 0;
  color: #333;
  font-size: 1.2em;
  font-weight: bold;
}

.solution-description {
  margin: 0;
  color: #666;
  font-size: 0.9em;
  line-height: 1.4;
}

.image-link {
  display: inline-block;
}

.hardware-image {
  cursor: pointer;
}

.solution-input {
  display: flex;
  flex-direction: column;
  gap: 15px;

}

.solution-name-input {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 1em;
  width: calc(100% - 20px);
  margin: 0 auto;
}

.solution-description-input {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 1em;
  width: calc(100% - 20px);
  margin: 0 auto;
  min-height: 100px;
}

.solution-name-input:focus,
.solution-description-input:focus {
  outline: none;
  border-color: #007bff;
}

</style>