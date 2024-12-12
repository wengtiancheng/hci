import { createApp } from 'vue'
import './style.css'
import ElementPlus from 'element-plus'
import App from './App.vue'
import axios from 'axios'
import router from './router'
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

const app = createApp(App);

const vuetify = createVuetify({
    components,
    directives,
})

axios.defaults.baseURL = ("http://localhost:8080")

app.use(ElementPlus).use(router).use(vuetify).mount('#app')

// 引入滑块
import VueSlider from 'vue-slider-component'
import 'vue-slider-component/theme/default.css'
app.component('VueSlider', VueSlider)  // 全局注册 VueSlider