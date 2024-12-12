<template>
    <Teleport to="body">
      <div v-if="visible" class="confirm-dialog-overlay">
        <div class="confirm-dialog">
          <div class="confirm-dialog-header">
            <h3>{{ dialogTitle }}</h3>
          </div>
          <div class="confirm-dialog-content">
            <p>{{ dialogMessage }}</p>
          </div>
          <div class="confirm-dialog-buttons">
            <button class="cancel-btn" @click="handleCancel">取消</button>
            <button class="confirm-btn" @click="handleConfirm">确认</button>
          </div>
        </div>
      </div>
    </Teleport>
  </template>

  <script setup>
  import { ref } from 'vue';

  const visible = ref(false);
  const resolvePromise = ref(null);
  const dialogTitle = ref('');
  const dialogMessage = ref('');

  const show = (title, message) => {
    dialogTitle.value = title;
    dialogMessage.value = message;
    visible.value = true;
    return new Promise((resolve) => {
      resolvePromise.value = resolve;
    });
  };

  const handleConfirm = () => {
    visible.value = false;
    resolvePromise.value?.(true);
  };

  const handleCancel = () => {
    visible.value = false;
    resolvePromise.value?.(false);
  };

  defineExpose({ show });
  </script>

  <style scoped>
  .confirm-dialog-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 9999;
  }

  .confirm-dialog {
    background-color: white;
    border-radius: 8px;
    min-width: 400px;
    max-width: 90%;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.15);
  }

  .confirm-dialog-header {
    padding: 20px 20px 0;
  }

  .confirm-dialog-header h3 {
    margin: 0;
    font-size: 18px;
    color: #333;
  }

  .confirm-dialog-content {
    padding: 20px;
  }

  .confirm-dialog-content p {
    margin: 0;
    font-size: 14px;
    color: #666;
    line-height: 1.5;
  }

  .confirm-dialog-buttons {
    padding: 0 20px 20px;
    display: flex;
    justify-content: flex-end;
    gap: 12px;
  }

  .confirm-btn, .cancel-btn {
    padding: 8px 20px;
    border-radius: 4px;
    border: none;
    cursor: pointer;
    font-size: 14px;
    transition: all 0.3s ease;
  }

  .cancel-btn {
    background-color: #f5f5f5;
    color: #333;
  }

  .confirm-btn {
    background-color: #ff4d4f;
    color: white;
  }

  .cancel-btn:hover {
    background-color: #e8e8e8;
  }

  .confirm-btn:hover {
    opacity: 0.9;
  }
  </style>