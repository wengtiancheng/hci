<script setup>
import ConfigList from '../components/ConfigList.vue';
import RightPanel from '../components/RightPanel.vue';
</script>



<template>
  <div class="self-service">
    <ConfigList class="left-panel" />
    <RightPanel class="right-panel" />
  </div>
</template>

<style scoped>
.self-service {
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
}
.left-panel {
  width: 80%;
  justify-content: flex-start;
  align-items: flex-start;
  margin: 0;
  padding: 0;
}
.right-panel {
  width: 20%;
}
</style>