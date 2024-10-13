<template>
  <div id="app">
    <!-- Phần nền xám cho cả trang -->
    <div class="background">
      <!-- Bọc nội dung bên trong container -->
      <div class="container">
        <!-- Thêm HeaderComponent -->
        <HeaderComponent />
         <!-- Thêm NavigationComponent -->
        <NavigationComponent />
        <!-- Thêm router-view để hiển thị trang Home -->
        <router-view />
        
        <!-- Thêm FooterComponent -->
        <FooterComponent />
      </div>
    </div>
  </div>
</template>

<script>
import HeaderComponent from './components/Header.vue';
import FooterComponent from './components/FooterComponent.vue';
import NavigationComponent from './components/NavigationComponent.vue'; 

export default {
  name: 'App',
  components: {
    HeaderComponent,
    FooterComponent,
    NavigationComponent 
  }
};
</script>

<style>
/* Đặt màu nền xám cho phần nền */
.background {
  background-color: #2f2f2f; /* Màu xám cho nền */
  min-height: 100vh; /* Chiều cao tối thiểu để phủ kín toàn bộ trang */
}

/* Căn giữa nội dung chính và đặt giới hạn độ rộng */
.container {
  max-width: 1100px; /* Bạn có thể thay đổi giá trị này theo ý muốn */
  margin: 0 auto;    /* Căn giữa container */
  padding: 20px;     /* Tạo khoảng cách bên trong */
  background-color: #000000; /* Màu đen cho giao diện chính */
  color: #fff; /* Đặt màu chữ trắng để dễ đọc trên nền đen */
}

/* Xóa margin và padding mặc định của body */
body {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
</style>
