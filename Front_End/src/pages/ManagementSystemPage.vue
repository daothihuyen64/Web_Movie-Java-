<template>
  <div class="management-system-page">
    <button class="toggle-sidebar-btn" @click="toggleSidebar">
     {{ isSidebarOpen ? '<>' : '<>' }}
    </button>
    <!-- Sidebar Navigation -->
    <div class="sidebar" v-if="isSidebarOpen">
      <ul>
        <li 
          v-for="(item, index) in menuItems" 
          :key="index" 
          :class="{ active: activeMenuItem === item.name }"
          @click="selectMenuItem(item.name)"
        >
          {{ item.label }}
        </li>
      </ul>
    </div>

    <!-- Main Content Area -->
    <div class="content">
      <div v-if="activeMenuItem === 'packages'">
        <h2>Quản lý Gói dịch vụ</h2>
        <!-- Nội dung quản lý gói dịch vụ -->
      </div>
      <div v-else-if="activeMenuItem === 'users'">
        <h2>Quản lý Người dùng</h2>
        <!-- Nội dung quản lý người dùng -->
      </div>
      <div v-else-if="activeMenuItem === 'movies'">
        <h2>Quản lý Phim</h2>
        <!-- Nội dung quản lý phim -->
      </div>
      <div v-else-if="activeMenuItem === 'actors'">
        <h2>Quản lý Diễn viên</h2>
        <!-- Nội dung quản lý diễn viên -->
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ManagementSystemPage',
  data() {
    return {
      // Các mục trong menu
      menuItems: [
        { name: 'packages', label: 'Gói dịch vụ' },
        { name: 'users', label: 'Người dùng' },
        { name: 'movies', label: 'Phim' },
        { name: 'actors', label: 'Diễn viên' },
      ],
      // Mục đang được chọn
      activeMenuItem: 'packages',
      isSidebarOpen: true,
    };
  },
  methods: {
    selectMenuItem(item) {
      this.activeMenuItem = item; // Cập nhật mục đang chọn
    },
    toggleSidebar() {
      this.isSidebarOpen = !this.isSidebarOpen; // Đóng/mở sidebar
    },
  },
};
</script>

<style scoped>
.management-system-page {
  display: flex;
}

.toggle-sidebar-btn {
  position: fixed;
  z-index: 1000;
  padding: 4px 10px;
  font-size: 15px; 
  background-color: #808080;
  color: white;
  border: none;
  border-radius: 3px;
  cursor: pointer;
}

.sidebar {
  width: 200px;
  background-color: #808080;
  padding: 10px;
  border-right: 1px solid  #808080;
}

.sidebar ul {
  list-style-type: none;
  padding: 0;
}

.sidebar li {
  padding: 10px;
  font-size: 16px;
  color: #333;
  cursor: pointer;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.sidebar li.active {
  color: #e9ecef;
  font-weight: bold;
  border-left: 3px solid #e9ecef;
  padding-left: 7px;
}

.sidebar li:hover {
  background-color: #e9ecef;
}

.content {
  flex: 1;
  padding: 20px;
}

.content-full {
  margin-left: 0; /* Khi sidebar bị đóng, phần nội dung sẽ tràn ra hết chiều rộng */
}
</style>
