import axios from "axios";

// 设置基础URL
let base = "http://localhost:8084";

export const postRequest = (url, params) => {
  return axios.post(`${base}${url}`, params, {
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    transformRequest: [
      data => {
        let ret = "";
        for (let it in data) {
          ret +=
            encodeURIComponent(it) + "=" + encodeURIComponent(data[it]) + "&";
        }
        return ret;
      }
    ]
  });
};

export const uploadFileRequest = (url, params) => {
  // 使用FormData发送文件
  let formData = new FormData();
  Object.keys(params).forEach(key => {
    formData.append(key, params[key]);
  });
  return axios.post(`${base}${url}`, formData, {
    headers: { "Content-Type": "multipart/form-data" }
  });
};

export const putRequest = (url, params) => {
  return axios.put(`${base}${url}`, params, {
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    transformRequest: [
      data => {
        let ret = "";
        for (let it in data) {
          ret +=
            encodeURIComponent(it) + "=" + encodeURIComponent(data[it]) + "&";
        }
        return ret;
      }
    ]
  });
};

export const deleteRequest = url => {
  return axios.delete(`${base}${url}`);
};

export const getRequest = (url, params) => {
  // 对于GET请求，将params附加到URL上
  let queryString = new URLSearchParams(params).toString();
  return axios.get(`${base}${url}?${queryString}`, {
    headers: { "Content-Type": "application/x-www-form-urlencoded" }
  });
};
