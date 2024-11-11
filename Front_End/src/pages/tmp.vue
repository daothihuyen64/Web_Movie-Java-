<template>
  <div class="settings-container">
    <h2>Cài đặt tài khoản</h2>
    
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
      <label for="password">Mật khẩu:</label>
      <input
        type="password"
        v-model="oldPassword"
        id="old-password"
        v-if="editPassword"
        placeholder="Nhập mật khẩu cũ"
      />
      <input
        type="password"
        v-model="newPassword"
        id="new-password"
        v-if="editPassword"
        placeholder="Nhập mật khẩu mới"
      />
      <button @click="toggleEdit('password')">
        {{ editPassword ? 'Lưu' : 'Chỉnh sửa' }}
      </button>
    </div>
  </div>
</template>

<script>
import axios from '@/axios'; // Sử dụng axios đã cấu hình sẵn
import { mapGetters } from 'vuex';

export default {
  data() {
    return {
      userInfo: {},        // Lưu tất cả thông tin user
      nickname: '',
      oldPassword: '',
      newPassword: '',
      editNickname: false, // Trạng thái chỉnh sửa nickname
      editPassword: false  // Trạng thái chỉnh sửa mật khẩu
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
          this.nickname = this.userInfo.nickName;
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
        const updateData = { nickName: this.nickname };
        const response = await axios.put(`http://localhost:8080/user/update/${this.userId}`, updateData);
        if (response.data.success) {
          this.$store.commit('SET_NICKNAME', this.nickname);
          alert('Cập nhật nickname thành công');
        } else {
          alert('Cập nhật nickname thất bại');
        }
      } catch (error) {
        console.error('Lỗi khi cập nhật nickname', error);
      }
    },
    async updatePassword() {
      try {
        const updateData = { oldPassword: this.oldPassword, newPassword: this.newPassword };
        const response = await axios.put(`http://localhost:8080/user/updatePassword/${this.userId}`, updateData);
        if (response.data.success) {
          alert('Cập nhật mật khẩu thành công');
        } else {
          alert('Mật khẩu cũ không đúng');
        }
      } catch (error) {
        console.error('Lỗi khi cập nhật mật khẩu', error);
      }
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
