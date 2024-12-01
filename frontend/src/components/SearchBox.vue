<template>
  <div class="search-box">
    <input 
      type="text" 
      :value="modelValue"
      @input="$emit('update:modelValue', ($event.target as HTMLInputElement).value)"
      placeholder="输入名称来查找配件"
      class="search-input"
    />
  </div>
</template>

<script setup lang="ts">
defineProps<{
  modelValue: string
}>();

defineEmits<{
  (e: 'update:modelValue', value: string): void
}>();
</script>

<style scoped>
.search-box {
  margin: 1em 0;
  width: 100%;
}

.search-input {
  width: 100%;
  padding: 0.75em 1em;
  border: 1px solid #ddd;
  border-radius: 0.25em;
  font-size: 1em;
  outline: none;
  transition: border-color 0.2s;
}

.search-input:focus {
  border-color: #007bff;
}
</style>
