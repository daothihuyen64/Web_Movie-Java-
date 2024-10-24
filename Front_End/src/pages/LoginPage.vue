<template>
  <div class="login-page">
    <h2>Đăng nhập</h2>
    <form @submit.prevent="login">
      <div class="input-group">
        <label for="username">Email</label>
        <input type="text" id="username" v-model="username" required />
      </div>

      <div class="input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="password" required />
      </div>

      <button type="submit" :disabled="loading">Đăng nhập</button>

      <p v-if="error" class="error">{{ error }}</p>
    </form>
    <p class="signup-link">Chưa có tài khoản? <router-link to="/signup">Đăng ký</router-link></p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: '',
      error: '',
      loading: false
    };
  },
  methods: {
    async login() {
      this.loading = true;
      this.error = '';
      try {
        await this.$store.dispatch('login', {
          username: this.username,
          password: this.password
        });
        // Nếu đăng nhập thành công, chuyển hướng người dùng
        this.$router.push('/');
      } catch (err) {
        this.error = 'Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin!';
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>

<style scoped>
.login-page {
  max-width: 400px;
  margin: 100px auto;
  padding: 20px;
  background-color: #fff;
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
.error {
  color: red;
  margin-top: 10px;
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

button:disabled {
  background-color: #95a5a6;
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
</style>
