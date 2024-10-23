<template>
  <div class="login-container">
    <h2>Đăng nhập</h2>
    <form @submit.prevent="handleLogin">
      <div class="input-group">
        <label for="email">Email</label>
        <input 
          type="email" 
          id="email" 
          v-model="email" 
          placeholder="Nhập email" 
          required 
        />
      </div>
      
      <div class="input-group">
        <label for="password">Mật khẩu</label>
        <input 
          type="password" 
          id="password" 
          v-model="password" 
          placeholder="Nhập mật khẩu" 
          required 
        />
      </div>

      <button type="submit">Đăng nhập</button>
      <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
    </form>
    <p class="signup-link">Chưa có tài khoản? <router-link to="/signup">Đăng ký</router-link></p>
  </div>
</template>

<script>
import axios from '@/axios';
export default {
  name: 'LoginPage',
  data() {
    return {
      email: '',
      password: '',
      errorMessage: ''
    };
  },
  methods: {
    async handleLogin() {
      try {
        // Gửi yêu cầu đăng nhập đến API
        const response = await axios.post('/login/signin', {
          userName: this.email,
          password: this.password,
        });

        // Xử lý phản hồi từ API
        if (response.status === 200) {
          const { token, user } = response.data;

          // Lưu thông tin người dùng và token vào Vuex
          this.$store.commit('setUser', user);
          this.$store.commit('setToken', token);
          
          // Điều hướng đến trang chủ sau khi đăng nhập thành công
          this.$router.push({ name: 'home' });
        }
      } catch (error) {
        // Xử lý lỗi
        this.errorMessage = 'Đăng nhập thất bại. Vui lòng kiểm tra lại tên đăng nhập và mật khẩu.';
        console.error('Error during login:', error);
      }
    },
    // async handleLogin() {
    //   // Tạo đối tượng user với email và password
    //   const user = {
    //     email: this.email,
    //     password: this.password
    //   };
    //   try {
    //     // Gọi action 'login' và truyền đối tượng user
    //     await this.$store.dispatch('login', user);
    //     // Chuyển hướng sau khi đăng nhập thành công
    //     this.$router.push('/');
    //   } catch (error) {
    //      this.errorMessage = 'Đăng nhập thất bại, vui lòng kiểm tra lại thông tin.';
    //      alert('Đăng nhập thất bại: ' + error.message);
    //   }
    // },
    // handleLogin() {
    //   // Thực hiện hành động đăng nhập ở đây, như gọi API
    //   if (this.email && this.password) {
    //     const user = {
    //       email: this.email,
    //       name: 'Huyen',  // Giả lập tên người dùng
    //       avatar: 'https://img6.thuthuatphanmem.vn/uploads/2022/01/27/hinh-anh-co-be-voi-bieu-cam-dang-yeu_011802905.jpg'  // Giả lập đường dẫn avatar
    //     };
    //     // Ví dụ giả lập login
    //     alert(`Đăng nhập với email: ${this.email} và mật khẩu: ${this.password}`);
        
    //     // Thực hiện lưu thông tin người dùng vào Vuex
    //     this.$store.dispatch('login', user);

    //     // Thực hiện điều hướng nếu đăng nhập thành công
    //     this.$router.push({ name: 'home' });
    //   } else {
    //     alert('Vui lòng nhập đầy đủ thông tin.');
    //   }
//     }
  }
};
</script>

<style scoped>
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
