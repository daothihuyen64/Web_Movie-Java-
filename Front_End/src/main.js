// src/main.js
import { createApp } from 'vue';
import App from './App.vue';
import router from './router'; // Import router đã cấu hình

createApp(App)
  .use(router) // Sử dụng router
  .mount('#app'); // Mount ứng dụng vào phần tử có ID #app
