<template>
  <div id="app" :class="{ 'login-page': isAuthPage }">
    <!-- Nếu không phải trang đăng nhập hoặc đăng ký, hiển thị phần nền và các thành phần khác -->
    <div v-if="!isAuthPage" class="background">
      <div class="container">
        <HeaderComponent />
        <NavigationComponent />
        <router-view />
        <FooterComponent />
      </div>
    </div>

    <!-- Nếu là trang đăng nhập hoặc đăng ký, hiển thị nội dung trang đăng nhập/đăng ký -->
    <div v-else class="login-container">
      <router-view />
    </div>
  </div>
</template>

<script>
import HeaderComponent from './components/Header.vue';
import FooterComponent from './components/FooterComponent.vue';
import NavigationComponent from './components/NavigationComponent.vue';
import { mapGetters } from 'vuex';

export default {
  name: 'App',
  components: {
    HeaderComponent,
    FooterComponent,
    NavigationComponent 
  },
  computed: {
    ...mapGetters(['isAuthenticated']),
    isAuthPage() {
      // Kiểm tra xem route hiện tại có phải là trang đăng nhập hoặc đăng ký không
      return this.$route.name === 'login' || this.$route.name === 'signup';
    }
  }
};
</script>

<style>
/* Đặt màu nền xám cho phần nền không phải trang đăng nhập/đăng ký */
.background {
  background-color: #2f2f2f; /* Màu xám cho nền */
  min-height: 100vh; /* Chiều cao tối thiểu để phủ kín toàn bộ trang */
}

/* Căn giữa nội dung chính và đặt giới hạn độ rộng cho các trang không phải trang đăng nhập/đăng ký */
.container {
  max-width: 1100px; /* Bạn có thể thay đổi giá trị này theo ý muốn */
  margin: 0 auto;    /* Căn giữa container */
  padding: 20px;     /* Tạo khoảng cách bên trong */
  background-color: #000000; /* Màu đen cho giao diện chính */
  color: #fff; /* Đặt màu chữ trắng để dễ đọc trên nền đen */
}

/* Đặt màu nền đen cho trang đăng nhập/đăng ký */
.login-container {
  background-color: hsl(0, 0%, 0%); /* Màu đen cho trang đăng nhập/đăng ký */
  min-height: 100vh; /* Chiều cao tối thiểu để phủ kín toàn bộ trang */
  display: flex; 
  align-items: center;
  justify-content: center; 
}

/* Xóa margin và padding mặc định của body */
body {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
</style>
