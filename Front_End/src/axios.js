// src/axios.js
import axios from 'axios';

// Tạo instance Axios
const instance = axios.create({
  baseURL: 'http://localhost:8081/', // URL cơ bản cho tất cả các yêu cầu
  timeout: 10000, // Thời gian timeout
  headers: {
    'Content-Type': 'application/json', // Định dạng dữ liệu
  },
});

// Có thể thêm interceptor nếu cần
instance.interceptors.response.use(
  response => response,
  error => {
    // Xử lý lỗi toàn cục tại đây
    return Promise.reject(error);
  }
);

export default instance;
