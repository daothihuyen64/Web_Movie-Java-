<template>
  <div class="settings-container">
    <h2>Cài đặt tài khoản</h2>
    <Notification
      v-if="notificationVisible"
      :message="notificationMessage"
      :type="notificationType"
      @close="notificationVisible = false"
    />
    <!-- Thông tin người dùng (không cho chỉnh sửa) -->
    <div class="form-group" v-for="(value, key) in filteredUserInfo" :key="key">
      <label :for="key">{{ key }}:</label>
      <input type="text" :value="value" :id="key" readonly />
    </div>

    <!-- Chỉnh sửa nickname -->
    <div class="form-group">
      <label for="nickname">Nickname:</label>
      <input
        type="text"
        v-model="nickname"
        id="nickname"
        :readonly="!editNickname"
      />
      <button @click="toggleEdit('nickname')">
        {{ editNickname ? 'Lưu' : 'Chỉnh sửa' }}
      </button>
    </div>

    <!-- Chỉnh sửa password -->
    <div class="form-group">
      <label>Password:</label>
    </div>

    <div class="form-group" v-if="editPassword">
      <label for="old-password">Mật khẩu cũ:</label>
      <input
        type="password"
        v-model="oldPassword"
        id="old-password"
        placeholder="Nhập mật khẩu cũ"
      />
      <label for="new-password">Mật khẩu mới:</label>
      <input
        type="password"
        v-model="newPassword"
        id="new-password"
        placeholder="Nhập mật khẩu mới"
      />
      <label for="confirm-password">Xác nhận mật khẩu mới:</label>
      <input
        type="password"
        v-model="confirmPassword"
        id="confirm-password"
        placeholder="Xác nhận mật khẩu mới"
      />
      <button @click="toggleEdit('password')">
        Lưu
      </button>
    </div>
    <button v-else @click="toggleEdit('password')">Chỉnh sửa mật khẩu</button>
  </div>
</template>

<script>
import axios from '@/axios'; // Sử dụng axios đã cấu hình sẵn
import { mapGetters } from 'vuex';
import Notification from '../components/Notification.vue';

export default {
  components: {
    Notification,
  },
  data() {
    return {
      userInfo: {},        // Lưu tất cả thông tin user
      nickname: '',
      oldPassword: '',
      newPassword: '',
      confirmPassword: '',
      editNickname: false, // Trạng thái chỉnh sửa nickname
      editPassword: false,   // Trạng thái chỉnh sửa mật khẩu
      notificationType : 'success',
      notificationMessage : '',
      notificationVisible : false
   };
  },
  computed: {
    ...mapGetters(['userId']),
    filteredUserInfo() {
      const userCopy = { ...this.userInfo }; // Tạo một bản sao của userInfo
      delete userCopy.nickName;
      delete userCopy.id; // Xóa id khỏi đối tượng
      delete userCopy.password; // Xóa password khỏi đối tượng
      return userCopy; // Trả về thông tin còn lại
    },
  },
  created() {
    // Lấy thông tin người dùng khi component được tạo
    this.fetchUserInfo();
  },
  methods: {
    async fetchUserInfo() {
      try {
        const response = await axios.get(`http://localhost:8080/user/getUser/${this.userId}`);
        if (response.status === 200) {
          this.userInfo = response.data;
          this.nickname = this.userInfo.nickName; // Giả định nickname trong userInfo
        }
      } catch (error) {
        console.error('Lỗi khi lấy thông tin người dùng', error);
      }
    },
    toggleEdit(field) {
      if (field === 'nickname') {
        if (this.editNickname) {
          this.updateNickname();
        }
        this.editNickname = !this.editNickname;
      } else if (field === 'password') {
        if (this.editPassword) {
          this.updatePassword();
        }
        this.editPassword = !this.editPassword;
      }
    },
    async updateNickname() {
      try {
        const updateData = { nickname: this.nickname }; // Cập nhật trường nickname
        const response = await axios.put(`http://localhost:8080/user/update/${this.userId}`, updateData);
        if (response.data.success) {
          this.$store.commit('SET_nickname', this.nickname);
          this.showNotification(response.data.desc, 'success');
        } else {
          this.showNotification(response.data.desc, 'error');
        }
      } catch (error) {
        console.error('Lỗi khi cập nhật nickname', error);
      }
    },
    async updatePassword() {
      try {
        const updateData = {
          oldPassword: this.oldPassword,
          password: this.newPassword,
          confirmPassword: this.confirmPassword,
        };
        const response = await axios.put(`http://localhost:8080/user/update/${this.userId}`, updateData);
        if (response.data.success) {
          this.showNotification(response.data.desc, 'success');
        } else {
          this.showNotification(response.data.desc, 'error');
        }
      } catch (error) {
        console.error('Lỗi khi cập nhật mật khẩu', error);
      }
    },
    showNotification(message, type = 'success') {
      this.notificationMessage = message;
      this.notificationType = type;
      this.notificationVisible = true;
    },
  },
};
</script>

<style scoped>
.settings-container {
  padding: 20px;
  background-color: #333;
  color: #fff;
}

.form-group {
  margin-bottom: 15px;
}

input {
  padding: 8px;
  width: 100%;
  box-sizing: border-box;
  border-radius: 5px;
  border: 1px solid #ccc;
}

button {
  padding: 10px 15px;
  background-color: #008CBA;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #005f75;
}
</style>
