<template>
  <header class="header">
    <!-- Logo của trang -->
    <div class="logo">
      <img src="@/assets/logo.png" alt="Logo" />
      <h1>BoBa</h1>
    </div>

    <!-- Thanh điều hướng -->
    <nav class="nav">
      <router-link to="/" class="nav-link">Home</router-link>

      <div class="user-info">
        <!-- Nếu người dùng đã đăng nhập, hiển thị thông tin user -->
        <div v-if="isAuthenticated" class="logged-in-info">
          <!-- Nếu user.avatar có giá trị, sử dụng avatar từ server, nếu không thì sử dụng ảnh mặc định -->
          <img 
            :src="avatarUrl" 
            alt="User Avatar" 
            class="avatar" 
          />
          <span class="user-name">{{ nickname || 'Guest' }}</span>
          <button @click="logout" class="logout-button">Đăng xuất</button>
        </div>

        <!-- Nếu chưa đăng nhập, hiển thị nút đăng nhập -->
        <div v-else class="auth-buttons">
          <router-link to="/login" class="auth-button">Đăng nhập</router-link>
        </div>
      </div>
    </nav>
  </header>
</template>

<script>
export default {
  name: 'HeaderComponent',
  computed: {
    // Sử dụng getter từ Vuex để lấy thông tin nickname và trạng thái đăng nhập
    nickname() {
      return this.$store.getters.nickname; // Lấy từ getter nickname trong auth.js
    },
    isAuthenticated() {
      return this.$store.getters.isAuthenticated; // Lấy trạng thái đăng nhập từ Vuex
    },
    avatarUrl() {
      // Nếu có avatar từ user, dùng nó, nếu không thì dùng ảnh mặc định
      return this.user?.avatar || 'https://img6.thuthuatphanmem.vn/uploads/2022/01/27/anh-co-be-de-thuong-tuyet-dep_011758553.jpg';
    }
  },
  methods: {
    logout() {
      // Gọi action logout từ Vuex
      this.$store.dispatch('logout');
      // Điều hướng về trang chủ sau khi đăng xuất
      this.$router.push({ name: 'home' });
    }
  }
};
</script>

<style scoped>
/* CSS cho Header */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #000;
  color: white;
}

.logo {
  display: flex;
  align-items: center;
}

.logo img {
  width: 50px;
  height: 50px;
  margin-right: 10px;
}

.logo h1 {
  font-size: 1.8rem;
  color: #ffcc00;
  margin: 0;
}

.nav {
  display: flex;
  align-items: center;
  gap: 30px;
}

.nav-link {
  color: white;
  text-decoration: none;
  font-weight: bold;
  padding: 8px 15px;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.nav-link:hover {
  background-color: #ffcc00;
  color: black;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 20px;
}

.logged-in-info {
  display: flex;
  align-items: center;
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
}

.user-name {
  font-weight: bold;
  margin-right: 15px;
}

.logout-button {
  background-color: #ff4d4d;
  border: none;
  padding: 8px 12px;
  color: white;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.logout-button:hover {
  background-color: #e60000;
}

/* Auth buttons */
.auth-buttons {
  display: flex;
  gap: 15px;
}

.auth-button {
  background-color: #3498db;
  color: white;
  padding: 8px 15px;
  text-decoration: none;
  font-weight: bold;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.auth-button:hover {
  background-color: #2980b9;
}
</style>
