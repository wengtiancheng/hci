import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    port: 3000,   //设定前端运行的端口
    open: true,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',


      }
    }
  },
})
