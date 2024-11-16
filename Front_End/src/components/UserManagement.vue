<template>
  <div class="user-management">
    <div class="admin-section">
      <h2>Danh sách Admin</h2>
      <div v-if="admins.length" class="user-cards">
        <div v-for="admin in admins" :key="admin.id" class="user-card admin-card">
          <h3>{{ admin.nickName }}</h3>
          <p>Email: {{ admin.userName }}</p>
          <p>SĐT: {{ admin.phoneNumber || 'Chưa cập nhật' }}</p> <!-- Hiển thị SDT hoặc thông báo nếu không có -->
        </div>
      </div>
      <p v-else class="empty-message">Không có admin nào.</p>
    </div>

    <div class="user-section">
      <h2>Danh sách Người dùng</h2>
      <div v-if="users.length" class="user-cards">
        <div v-for="user in users" :key="user.id" class="user-card">
          <h3>{{ user.nickName }}</h3>
          <p>Email: {{ user.userName }}</p>
          <p>SĐT: {{ user.phoneNumber || 'Chưa cập nhật' }}</p> <!-- Hiển thị SDT hoặc thông báo nếu không có -->
        </div>
      </div>
      <p v-else class="empty-message">Không có người dùng nào.</p>
    </div>

    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
  </div>
</template>

<script>
import axios from '@/axios.js';

export default {
  data() {
    return {
      admins: [],
      users: [],
      errorMessage: '', // Thêm để lưu lỗi nếu có
    };
  },
  created() {
    this.fetchUsers();
  },
  methods: {
    async fetchUsers() {
      try {
        const response = await axios.get('http://localhost:8080/user/getAllUser');
        const allUsers = response.data;

        // Phân loại người dùng thành admin và user
        this.admins = allUsers.filter(user => user.role === 2);
        this.users = allUsers.filter(user => user.role === 1);
      } catch (error) {
        this.errorMessage = 'Không thể tải danh sách người dùng. Vui lòng thử lại sau.';
        console.error('Lỗi khi tải danh sách người dùng:', error);
      }
    },
  },
};
</script>

<style scoped>
.user-management {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  max-width: 1200px;
  margin: 20px auto;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.admin-section,
.user-section {
  flex: 1 1 48%;
  padding: 20px;
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

h2 {
  font-size: 24px;
  color: #333;
  margin-bottom: 15px;
  text-align: center;
  border-bottom: 2px solid #eee;
  padding-bottom: 10px;
}

.user-cards {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.user-card {
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s, box-shadow 0.2s;
}

.user-card h3 {
  font-size: 18px;
  color: #007bff;
  margin: 0 0 5px;
}

.user-card p {
  font-size: 14px;
  color: #555;
  margin: 0;
}

.user-card p:first-child {
  margin-top: 10px;
}

.user-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

.admin-card {
  background-color: #e8f5e9;
  border-color: #c8e6c9;
}

.empty-message {
  text-align: center;
  color: #999;
  font-size: 16px;
  margin-top: 20px;
}

.error-message {
  color: red;
  text-align: center;
  font-size: 16px;
  margin-top: 20px;
}
</style>
