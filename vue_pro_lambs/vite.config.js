import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
    },
  },
  /* 创建服务，采用代理，对于/lambs的请求自动到指定的服务上，并且改变数据源 */
  server: {
    proxy: {
      '/lambs': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
    port:80,
    host: '0.0.0.0'	// ← 新增内容 ←
  },
})
