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

      <div class="user-info" @click="toggleDropdown"  >
        <!-- Nếu người dùng đã đăng nhập, hiển thị thông tin user -->
        <div v-if="isAuthenticated" class="logged-in-info">
          <!-- Nếu user.avatar có giá trị, sử dụng avatar từ server, nếu không thì sử dụng ảnh mặc định -->
          <img 
            :src="avatarUrl" 
            alt="User Avatar" 
            class="avatar" 
            
          />
          <span class="user-name" >{{ nickname || 'Guest' }}</span>
          
          <!-- Menu thả xuống -->
         
          <div :class="{'dropdown-menu': true, 'active': isDropdownOpen}">
            <ul>
              <li><router-link to="/packages">Gói dịch vụ</router-link></li>
              <li><router-link to="/favouriteMovies">Tủ phim yêu thích</router-link></li>
              <li><router-link to="/settings">Cài đặt</router-link></li>
              <li v-if="role === 2"><router-link to="/manageSystem">Quản lý hệ thống</router-link></li>
              <li><a @click="logout">Đăng xuất</a></li>
            </ul>
          </div>

        </div>

        <!-- Nếu chưa đăng nhập, hiển thị nút đăng nhập -->
        <div v-else class="auth-buttons">
          <router-link to="/signin" class="auth-button">Đăng nhập</router-link>
        </div>
      </div>
    </nav>
  </header>
</template>
<script>
export default {
  name: 'HeaderComponent',
  data() {
    return {
      isDropdownOpen: false, // Trạng thái của menu thả xuống
    };
  },
  computed: {
    nickname() {
      return this.$store.getters.nickname;
    },
    role() {
      return this.$store.getters.role; // Giả sử bạn lưu userId trong store với getter tên là userId
    },
    isAuthenticated() {
      return this.$store.getters.isAuthenticated;
    },
    avatarUrl() {
      return this.user?.avatar || 'https://img6.thuthuatphanmem.vn/uploads/2022/01/27/anh-co-be-de-thuong-tuyet-dep_011758553.jpg';
    }
  },
 mounted() {
    // Đóng dropdown khi nhấp ra ngoài
    document.addEventListener('click', this.closeDropdownOnClickOutside);
  },
  beforeUnmount() {
    document.removeEventListener('click', this.closeDropdownOnClickOutside);
  },
  methods: {
    toggleDropdown() {
      this.isDropdownOpen = !this.isDropdownOpen;
    },
    closeDropdownOnClickOutside(event) {
      if (this.isDropdownOpen && !this.$el.contains(event.target)) {
        this.isDropdownOpen = false;
      }
    },
    logout() {
      this.$store.dispatch('logout');
      this.$router.push('/signin');
    }
  }
};
</script>



<style scoped>
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
/* Thêm các CSS cho dropdown */
/* Thay đổi menu thả xuống */
.dropdown-menu {
  display: none;
  position: absolute; /* Định vị tuyệt đối so với phần tử chứa nó */
  top: 80px; /* Điều chỉnh vị trí xuống dưới một chút */
  /* left: 0; Căn chỉnh về bên trái của phần tử cha */
  background-color: #333; /* Màu nền cho dropdown */
  padding: 10px;
  border-radius: 8px;
  flex-direction: column;
  min-width: 150px; /* Đặt độ rộng tối thiểu cho menu */
  z-index: 1000; /* Đảm bảo dropdown luôn nằm trên các phần tử khác */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Bóng cho menu */
}

.dropdown-menu.active {
  display: flex; /* Hiển thị dropdown khi được kích hoạt */
}

/* Định dạng cho các mục trong dropdown */
.dropdown-menu ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.dropdown-menu li {
  margin: 5px 0;
}

.dropdown-menu a {
  color: white;
  text-decoration: none;
  padding: 8px 10px;
  display: block;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.dropdown-menu a:hover {
  background-color: #444; /* Màu khi hover */
}


</style>