import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import {router} from './router/index.ts'
import ElementPlus from 'element-plus'

const app = createApp(App)
app.use(ElementPlus)
app.use(router).mount('#app')