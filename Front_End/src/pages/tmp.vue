<template>
  <div class="login-container">
      <h2>Đăng nhập</h2>
      <form @submit.prevent="handleLogin">
        <div class="input-group">
          <label for="username">Email</label>
          <input 
            type="text" 
            id="username" 
            v-model="username" 
            placeholder="Nhập email" 
            required 
          />
        </div>
        
        <div class="input-group">
          <label for="password">Password</label>
          <input 
            type="password" 
            id="password" 
            v-model="password" 
            placeholder="Nhập password" 
            required 
          />
        </div>

        <button type="submit">Đăng nhập</button>
        <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
      </form>
      <p class="signup-link">Chưa có tài khoản? <router-link to="/signup">Đăng ký</router-link></p>
    </div>
  </div>
</template>

<script>
import axios from '@/axios'; // Import axios instance
export default {
  name: 'LoginPage',
  data() {
    return {
      username: '', // Thay đổi từ email sang username
      password: '',
      errorMessage: '',
      avatarUrl: 'https://img6.thuthuatphanmem.vn/uploads/2022/01/27/anh-co-be-de-thuong-tuyet-dep_011758553.jpg' // Đường dẫn tới avatar mặc định
    };
  },
  computed: {
    // Sử dụng getter từ Vuex để kiểm tra xem người dùng đã đăng nhập chưa
    isAuthenticated() {
      return this.$store.getters.isAuthenticated;
    }
  },
  methods: {
    async handleLogin() {
      try {
        // Gửi yêu cầu đăng nhập đến API với thông tin username và password qua query params
        const response = await axios.post(`/login/signin?username=${this.username}&password=${this.password}`);

        // Xử lý phản hồi từ API
        if (response.status === 200 && response.data.success !== false) {
          const token = response.data.data; // Nhận token từ API

          // Lưu token vào Vuex
          this.$store.commit('setToken', token);

          // Lưu token vào localStorage
          localStorage.setItem('token', token);

          // Điều hướng đến trang chủ sau khi đăng nhập thành công
          this.$router.push({ name: 'home' });
        } else {
          this.errorMessage = 'Đăng nhập thất bại. Vui lòng kiểm tra lại tên đăng nhập và mật khẩu.';
        }
      } catch (error) {
        // Xử lý lỗi nếu có vấn đề với yêu cầu đăng nhập
        this.errorMessage = 'Đăng nhập thất bại. Vui lòng thử lại.';
        console.error('Error during login:', error);
      }
    },
    logout() {
      // Gọi action đăng xuất trong Vuex
      this.$store.dispatch('logout');
      // Xóa token khỏi localStorage
      localStorage.removeItem('token');
      // Reset lại form
      this.username = '';
      this.password = '';
      this.$router.push({ name: 'home' });
    }
  }
};
</script>

<style scoped>
/* CSS cho Avatar và User Info */
.user-info {
  text-align: center;
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  margin-bottom: 10px;
}

.login-container {
  max-width: 400px;
  margin: 100px auto;
  padding: 20px;
  background-color: #f4f4f4;
  border-radius: 8px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 20px;
  color : #000;
}

.input-group {
  margin-bottom: 15px;
}

.input-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color : #000;
}

.input-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

button:hover {
  background-color: #2980b9;
}

.signup-link {
  text-align: center;
  margin-top: 15px;
  color : #000;
}

.signup-link a {
  color: #3498db;
}

.error {
  color: red;
  margin-top: 10px;
}
</style>
