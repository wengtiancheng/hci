import { createApp } from 'vue'
import './style.css'
import ElementPlus from 'element-plus'
import App from './App.vue'
import axios from 'axios'
import router from './router'

const app = createApp(App);

axios.defaults.baseURL = ("http://localhost:8080")

app.use(ElementPlus).use(router).mount('#app')

// 引入滑块
import VueSlider from 'vue-slider-component'
import 'vue-slider-component/theme/default.css'
app.component('VueSlider', VueSlider)  // 全局注册 VueSlider