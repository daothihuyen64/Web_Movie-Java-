<template>
  <div class="signup-container">
    <h2>Đăng ký</h2>
    <form @submit.prevent="signup">
      <div>
        <label for="username">Email:</label>
        <input type="email" v-model="username" id="username" required />
      </div>

      <div>
        <label for="nickname">Biệt danh:</label>
        <input type="text" v-model="nickname" id="nickname" required />
      </div>

      <div>
        <label for="phone">Số điện thoại:</label>
        <input type="tel" v-model="phone" id="phone" required />
      </div>

      <div>
        <label for="password">Mật khẩu:</label>
        <input type="password" v-model="password" id="password" required />
      </div>

      <div>
        <label for="confirmPassword">Xác nhận mật khẩu:</label>
        <input type="password" v-model="confirmPassword" id="confirmPassword" required />
      </div>

      <button type="submit">Đăng ký</button>
    </form>

    <!-- Hiển thị thông báo lỗi hoặc thành công -->
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    <p v-if="successMessage" class="success">{{ successMessage }}</p>
  </div>
</template>

<script>
import axios from 'axios'; // Đặt import axios ở đây

export default {
  data() {
    return {
      username: '',
      nickname: '',
      phone: '',
      password: '',
      confirmPassword: '',
      errorMessage: '',
      successMessage: '',
    };
  },
  methods: {
    async signup() {
      // Kiểm tra xem mật khẩu và xác nhận mật khẩu có khớp không
      if (this.password !== this.confirmPassword) {
        this.errorMessage = 'Mật khẩu và xác nhận mật khẩu không khớp!';
        return;
      }

      try {
        const response = await axios.post('http://localhost:8080/login/signup', {
          username: this.username,
          password: this.password,
          confirmPassword: this.confirmPassword,
          nickname: this.nickname,
          phone: this.phone,
        });

        // Kiểm tra kết quả trả về từ API
        if (response.data.success) {
          this.successMessage = 'Đăng ký thành công!';
          this.errorMessage = '';
          // Điều hướng đến trang đăng nhập sau khi đăng ký thành công
          this.$router.push('/signin');
        } else {
          this.errorMessage = response.data.desc || 'Đăng ký thất bại';
          this.successMessage = '';
        }
      } catch (error) {
        // Kiểm tra xem có lỗi cụ thể từ máy chủ không
        if (error.response && error.response.data) {
          this.errorMessage = error.response.data.desc || 'Đăng ký thất bại';
        } else {
          this.errorMessage = 'Lỗi khi đăng ký.';
        }
        this.successMessage = '';
      }
    }
  }
};
</script>

<style scoped>
.signup-container {
  max-width: 400px; /* Tăng chiều rộng tối đa để làm hộp lớn hơn */
  width: 100%;      /* Đảm bảo hộp đăng ký chiếm toàn bộ chiều rộng tối đa */
  margin: 50px auto; /* Căn giữa hộp đăng ký trên trang */
  padding: 30px;     /* Tăng padding để tạo thêm khoảng trống bên trong hộp */
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #000;
}

form div {
  margin-bottom: 15px;
}

form label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #000;
}

form input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

button {
  background-color: #3498db;
  color: white;
  padding: 10px;
  border: none;
  cursor: pointer;
  width: 100%;
  font-size: 16px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

button:hover {
  background-color: #2980b9;
}

button:disabled {
  background-color: #95a5a6;
}

.error {
  color: red;
  margin-top: 10px;
}

.success {
  color: green;
  margin-top: 10px;
}
</style>
