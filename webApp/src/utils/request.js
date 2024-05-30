/*
 * axios请求基本配置、请求拦截器、相应拦截器
 * 创建请求实例
 */

import axios from "axios";
import qs from "qs";
import AESUtil from "./AESUtil";
// 解决跨域问题，代理设置在`vite.config.js`
const baseURL = "";

const instance = axios.create({
  baseURL: baseURL // 替换为你的后端API基础URL
  //   headers: {
  //     "Content-Type": "application/json"
  //   }
  // withCredentials: true,  // 允许发送凭证
});

// 添加请求拦截器
instance.interceptors.request.use(
  config => {
    if (
      config.headers["Content-Type"] ===
      "application/x-www-form-urlencoded;charset=UTF-8"
    ) {
      config.data = qs.stringify(config.data);
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// // 响应拦截器
// instance.interceptors.response.use(
//     result => {
//         if (result.status === 200) {
//             if(result.data.msg === 'token无效'){
//                 ElMessage.error('请先登录！')
//                 router.push('login')
//                 return Promise.reject(result.data);
//             }
//             return result.data;
//         } else {
//             ElMessage.error('服务异常')
//             // 异步的状态转化成失败的状态
//             return Promise.reject(result.data);
//         }
//     },
//     err => {
//         if (err.response.status === 401) {
//             ElMessage.error('请先登录！')
//             router.push('login')
//         } else {
//             ElMessage.error('服务异常')

//         }

//         // 异步的状态转化成失败的状态
//         return Promise.reject(err);
//     }
// )
