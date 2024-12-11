<template>
    <transition name="fade">
      <div v-if="visible" :class="['toast', toastType]">
        <i :class="iconClass">{{ icon }}</i>
        {{ message }}
      </div>
    </transition>
  </template>
  
  <script setup lang="ts">
  import { ref, computed } from 'vue';
  
  const visible = ref(false);
  const message = ref('');
  const toastType = ref('success');
  
  const show = (msg: string, type: string = 'success') => {
    message.value = msg;
    toastType.value = type;
    visible.value = true;
    setTimeout(() => {
      visible.value = false;
    }, 2000);
  };
  
  const iconClass = computed(() => {
    return toastType.value === 'success' ? 'success-icon' : 'error-icon';
  });
  
  const icon = computed(() => {
    return toastType.value === 'success' ? '✓' : '✗';
  });
  
  defineExpose({ show });
  </script>
  
  <style scoped>
  .toast {
    position: fixed;
    top: 80px;  /* 顶部导航栏下方 */
    left: 50%;
    transform: translateX(-50%);
    background-color: rgba(0, 0, 0, 0.7);
    color: white;
    padding: 12px 24px;
    border-radius: 4px;
    z-index: 9999;
    display: flex;
    align-items: center;
    gap: 8px;
  }
  
  .success-icon {
    color: #67C23A;
    font-weight: bold;
  }
  
  .error-icon {
    color: #F56C6C;
    font-weight: bold;
  }
  
  .fade-enter-active,
  .fade-leave-active {
    transition: opacity 0.3s ease;
  }
  
  .fade-enter-from,
  .fade-leave-to {
    opacity: 0;
  }
  </style>