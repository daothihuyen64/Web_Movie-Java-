<template>
 <div>
  <Notification
      v-if="notificationVisible"
      :message="notificationMessage"
      :type="notificationType"
      @close="notificationVisible = false"
    />
  <div v-if="!selectedPackage" class="packages-page">
    <h2>Giải trí bất tận bắt đầu từ đây. Các gói dịch vụ từ {{ minPackagePrice }} đ.</h2>
    

    <div class="packages-container">
      <div
        v-for="pkg in sortedPackages"
        :key="pkg.id"
        class="package-card"
      >
        <h3>{{ pkg.packageName }}</h3>
        <p class="access-duration">{{ pkg.accessDuration }} ngày</p>
        <p class="description">{{ pkg.description }}</p>
        <p class="price">{{ pkg.price.toLocaleString() }} đ / gói</p>
        <button class="register-btn" @click="registerPackage(pkg)">Đăng ký</button>
      </div>
    </div>
    <div class="transaction-history">
        <h3 v-if="transactionHistory.length > 0" class="history-title">Lịch sử Giao Dịch</h3>
        <div v-if="transactionHistory.length > 0" class="transaction-header">
          <span class="transaction-header-item">Loại Gói</span>
          <span class="transaction-header-item">Ngày bắt đầu</span>
          <span class="transaction-header-item">Ngày kết thúc</span>
        </div>
        <div v-if="transactionHistory.length > 0">
          <div v-for="transaction in transactionHistory" :key="transaction.startDate" class="transaction-item">
            <span class="transaction-data">{{ transaction.packageName }}</span>
            <span class="transaction-data">{{ transaction.startDate }}</span>
            <span class="transaction-data">{{ transaction.endDate }}</span>
          </div>
        </div>
        <div v-else>
          <p class="history-title">Không có lịch sử giao dịch.</p>
        </div>
    </div>
  </div>
  <div v-if="selectedPackage" class="package-detail">
    <h3 class="package-title">Chi tiết gói dịch vụ</h3>
    <div class="package-main-details">
      <p class="detail-value"><strong>Tên gói:</strong> {{ selectedPackage.packageName }}</p>
      <p class="detail-value"><strong>Mô tả:</strong> {{ selectedPackage.description }}</p>
      <p class="detail-value"><strong>Giá:</strong> {{ selectedPackage.price.toLocaleString() }} đ</p>
      <p class="detail-value"><strong>Thời hạn truy cập:</strong> {{ selectedPackage.accessDuration }} ngày</p>
      <p class="detail-value"><strong>QR Thanh toán:</strong></p>
    </div>
    <div class="qr-container">
      <img src="@/assets/QR.png" alt="Mã QR thanh toán" class="qr-code-image" />
    </div>
    <div class="button-group">
      <button class="pay-btn" @click="handlePayment">Thanh toán</button>
      <button class="back-btn" @click="selectedPackage = null">Quay lại</button>
    </div>
  </div>
 </div>
</template>

<script>
import axios from '@/axios';
import { mapGetters } from 'vuex';
import Notification from '../components/Notification.vue';

export default {
    name: 'PackagesPage',
  components: {
    Notification,
  },
  data() {
    return {
      packages: [], // Dữ liệu các gói dịch vụ
      selectedPackage: null, // Gói đã được chọn để hiển thị chi tiết
      transactionHistory: [],
      notificationVisible: false,
      notificationMessage: '',
      notificationType : 'success'
    };
  },
  computed: {
    // Sắp xếp các gói dịch vụ theo giá từ thấp đến cao
    sortedPackages() {
      return [...this.packages].sort((a, b) => a.price - b.price);
    },
    minPackagePrice() {
      if (this.sortedPackages.length === 0) return '0';
      return this.sortedPackages[0].price.toLocaleString(); // Lấy giá của phần tử đầu tiên
    },
    ...mapGetters(['userId'])
  },
  async created() {
    try {
      const response = await axios.get("http://localhost:8080/packages");
      if (response.status === 200 && response.data.success) {
        this.packages = response.data.data;
      }
      await this.fetchTransactionHistory();
    } catch (error) {
      console.error("Lỗi khi lấy gói dịch vụ:", error);
    }
  },
  methods: {
    // Hàm xử lý đăng ký gói dịch vụ
    registerPackage(pkg) {
      this.selectedPackage = pkg;
    },
    async handlePayment() {
      try {
      // Tạo body của request
      const requestBody = {
        packageId: this.selectedPackage.id, // ID gói dịch vụ đang chọn
        userId: this.userId, // ID người dùng giả lập, thay bằng giá trị thực tế nếu có
        paymentSuccess : true
      };

      // Gọi API đăng ký gói
      const response = await axios.post("http://localhost:8080/user/transaction/register", requestBody);

      // Xử lý kết quả trả về
      if (response.data.success) {
        this.showNotification(response.data.desc); // Hiển thị thông báo thành công

        await this.fetchTransactionHistory();
        // Đặt lại selectedPackage để quay lại giao diện danh sách gói dịch vụ
        this.selectedPackage = null;
        
      } else {
        // Hiển thị thông báo lỗi nếu không thành công
        this.showNotification(response.data.desc, 'error');
      }
    } catch (error) {
      this.showNotification("Đã xảy ra lỗi khi đăng ký gói. Vui lòng thử lại.", 'error');
    }
    },
    async fetchTransactionHistory() {
      try {
        const response = await axios.get(`http://localhost:8080/user/transaction/history/${this.userId}`);
        if (response.status === 200) {
          this.transactionHistory = response.data.reverse();
        } else {
          console.error("Lỗi khi lấy lịch sử giao dịch:", response.status);
        }
      } catch (error) {
        console.error("Lỗi khi gọi API lấy lịch sử giao dịch:", error);
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
.packages-page{
  text-align: center;
  padding: 20px;
}

h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
}

.packages-container {
  display: flex;
  justify-content: center;
  gap: 20px;
  flex-wrap: wrap;
  margin-bottom: 50px;
}

.package-card{
  background-color: #001f3f;
  color: white;
  border-radius: 8px;
  padding: 20px;
  width: 200px;
  text-align: left;
}

.package-card h3 {
  font-size: 20px;
  margin-bottom: 5px;
}

.access-duration {
  font-size: 14px;
  color: #bbb;
}

.description {
  font-size: 16px;
  margin: 10px 0;
}

.price {
  font-size: 18px;
  font-weight: bold;
}

.register-btn {
  background-color: #4CAF50;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 10px;
}

.register-btn:hover {
  background-color: #45a049;
}

.package-detail {
  max-width: 350px;
  margin: 20px auto;
  padding: 20px;
  background-color: #ffffff;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 16px;
}

.package-detail p {
  display: flex;
  justify-content: space-between;
  margin: 10px 0;
  padding: 10px 0;
  border-top: 1px solid #ddd;
}

.package-detail p:first-of-type {
  border-top: none;
}

.detail-value {
  font-weight: bold;
  color: #333;
}

.package-title {
  font-size: 26px;
  font-weight: bold;
  margin-bottom: 15px;
  text-align: center;
  color: #333
}

.package-main-details {
  margin-bottom: 15px;
}


.qr-container {
  text-align: center;
  margin: 15px 0;
}

.qr-code-image {
  width: 150px;
  height: 150px;
}

.button-group {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

.pay-btn,
.back-btn {
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.pay-btn {
  background-color: #4CAF50;
  color: white;
}

.pay-btn:hover {
  background-color: #45a049;
}

.back-btn {
  background-color: #f44336;
  color: white;
}

.back-btn:hover {
  background-color: #d32f2f;
}

.transaction-history {
  margin-top: 30px;
  padding: 20px;
  background-color: #333;
  border-radius: 8px;
  text-align: left;
}

.history-title {
  font-size: 22px;
  font-weight: bold;
  margin-bottom: 30px;
  text-align: center;
}

.transaction-header {
  display: flex;
  justify-content: space-between;
  font-weight: bold;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  margin-bottom: 10px;
}

.transaction-header-item {
  flex: 1;
  text-align: center;
}

.transaction-item {
  display: flex; /* Sử dụng flex để các thông tin nằm ngang */
  justify-content: space-between; /* Căn đều khoảng cách giữa các phần tử */
  padding: 10px 0;
  border-bottom: 1px solid #ddd;
}

.transaction-item:last-child {
  border-bottom: none;
}

.transaction-data {
  flex: 1;
  text-align: center;
}
</style>
