import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url))
    }
  },
  server: {
    proxy: {
      // 获取路径中包含'/api'的请求
      "/api": {
        // 后端服务器所在的源
        target: "http://localhost:8084/",
        // 修改源
        changeOrigin: true,
        secure: true,
        // 将'/api'替换为''
        rewrite: path => path.replace(/^\/api/, "")
      }
    }
  }
});
