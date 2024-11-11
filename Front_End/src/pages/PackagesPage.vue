<template>
  <div class="packages-page">
    <h2>Giải trí bất tận bắt đầu từ đây. Các gói dịch vụ từ 10.000 đ.</h2>
    <p>Hủy hoặc thay đổi gói dịch vụ bất kỳ lúc nào.</p>

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
  </div>
</template>

<script>
import axios from '@/axios';

export default {
    name: 'PackagesPage',
  data() {
    return {
      packages: [], // Dữ liệu các gói dịch vụ
    };
  },
  computed: {
    // Sắp xếp các gói dịch vụ theo giá từ thấp đến cao
    sortedPackages() {
      return [...this.packages].sort((a, b) => a.price - b.price);
    },
  },
  async created() {
    try {
      const response = await axios.get("http://localhost:8080/packages");
      if (response.status === 200 && response.data.success) {
        this.packages = response.data.data;
      }
    } catch (error) {
      console.error("Lỗi khi lấy gói dịch vụ:", error);
    }
  },
  methods: {
    // Hàm xử lý đăng ký gói dịch vụ
    registerPackage(pkg) {
      console.log("Đăng ký gói:", pkg.packageName);
      // Bạn có thể thêm logic đăng ký tại đây
    },
  },
};
</script>
<style scoped>
.packages-page {
  text-align: center;
  padding: 20px;
}

h2 {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
}

.packages-container {
  display: flex;
  justify-content: center;
  gap: 20px;
  flex-wrap: wrap;
}

.package-card {
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
</style>
