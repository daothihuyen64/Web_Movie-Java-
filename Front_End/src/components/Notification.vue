<template>
  <div v-if="isVisible" class="notification" :class="type">
    <p>{{ message }}</p>
    <button @click="close">Đóng</button>
  </div>
</template>

<script>
export default {
  name: 'NotificationComponent',
  props: {
    message: {
      type: String,
      required: true,
    },
    type: {
      type: String,
      default: 'success', // Có thể là 'success', 'error', 'info', ...
    },
    duration: {
      type: Number,
      default: 1200, // Thời gian hiển thị (mili giây)
    },
  },
  data() {
    return {
      isVisible: true,
    };
  },
  mounted() {
    setTimeout(this.close, this.duration);
  },
  methods: {
    close() {
      this.isVisible = false;
      this.$emit('close'); // Phát sự kiện khi thông báo đóng
    },
  },
};
</script>

<style scoped>
.notification {
  position: fixed;
  top: 20px;
  left: 50%; /*cạnh trái của hộp thông báo nằm giữa màn hình */
  transform: translateX(-50%); 
  /* right: 20px; */
  padding: 10px 20px;
  border-radius: 5px;
  z-index: 9999;
}

.notification.success {
  background-color: #4caf50; /* Màu xanh cho thông báo thành công */
  color: white;
}

.notification.error {
  background-color: #f44336; /* Màu đỏ cho thông báo lỗi */
  color: white;
}

.notification.info {
  background-color: #2196F3; /* Màu xanh dương cho thông báo thông tin */
  color: white;
}

button {
  margin-left: 10px;
  background: transparent;
  border: none;
  color: white;
  cursor: pointer;
}
</style>
