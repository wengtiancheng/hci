<script setup>


import Header from "./components/Header.vue";
import {useRoute} from "vue-router";

const route = useRoute();

</script>

<template>
  <Header v-if="route.meta.showHeader"/>
  <router-view></router-view>

</template>

<style scoped>

</style>