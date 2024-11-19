<template>
  <Notification
      v-if="notificationVisible"
      :message="notificationMessage"
      :type="notificationType"
      @close="notificationVisible = false"
    />
  <div class="package-management">
    <!-- Hiển thị nút + và danh sách gói dịch vụ nếu form thêm gói không hiển thị -->
    <div v-if="!isAddPackageFormVisible">
      <h2>Danh Sách Gói Dịch Vụ</h2>
      <button class="add-package-btn" @click="openAddPackageForm">+ Thêm Gói</button>

      <div class="package-cards">
        <div
          v-for="packageItem in packages"
          :key="packageItem.id"
          class="package-card"
          @mouseenter="hoveredPackageId = packageItem.id"
          @mouseleave="hoveredPackageId = null"
        >
          <h3>{{ packageItem.packageName }}</h3>
          <p>Giá: {{ formatPrice(packageItem.price) }}</p>
          <p>Thời hạn: {{ packageItem.accessDuration }} ngày</p>
          <p>Mô tả: {{ packageItem.description }}</p>
          <button
            v-if="hoveredPackageId === packageItem.id"
            class="delete-btn"
            @click="deletePackage(packageItem.id)"
          >
            Xóa
          </button>
        </div>
      </div>
    </div>

    <!-- Form Thêm Gói -->
    <div v-if="isAddPackageFormVisible" class="add-package-form">
      <h2>Thêm Gói Dịch Vụ</h2>
      <form @submit.prevent="addPackage">
        <label for="packageName">Tên Gói:</label>
        <input
          type="text"
          id="packageName"
          v-model="newPackage.packageName"
          required
        />

        <label for="price">Giá:</label>
        <input
          type="number"
          id="price"
          v-model="newPackage.price"
          required
        />

        <label for="accessDuration">Thời Hạn (ngày):</label>
        <input
          type="number"
          id="accessDuration"
          v-model="newPackage.accessDuration"
          required
        />

        <label for="description">Mô Tả:</label>
        <textarea
          id="description"
          v-model="newPackage.description"
          required
        ></textarea>

        <button type="submit" class="submit-btn">Lưu</button>
        <button type="button" class="cancel-btn" @click="closeAddPackageForm">Hủy</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "@/axios";
import Notification from './Notification.vue';

export default {
  components: {
    Notification,
  },
  data() {
    return {
      packages: [], // Danh sách các gói dịch vụ
      isAddPackageFormVisible: false, // Hiển thị form thêm gói
      hoveredPackageId: null, // Gói dịch vụ đang được hover
      newPackage: {
        packageName: "",
        price: 0,
        accessDuration: 0,
        description: "",
      }, // Gói dịch vụ mới
      notificationVisible: false,
      notificationMessage: '',
      notificationType : 'success',
    };
  },
  created() {
    this.fetchPackages();
  },
  methods: {
    // Lấy danh sách gói dịch vụ
    async fetchPackages() {
      try {
        const response = await axios.get("http://localhost:8080/packages/all/packages");
        this.packages = response.data.data || [];
      } catch (error) {
        console.error("Lỗi khi tải danh sách gói dịch vụ:", error);
      }
    },
    // Mở form thêm gói dịch vụ
    openAddPackageForm() {
      this.isAddPackageFormVisible = true;
    },
    // Đóng form thêm gói dịch vụ
    closeAddPackageForm() {
      this.isAddPackageFormVisible = false;
      this.resetNewPackageForm();
    },
    // Reset thông tin form thêm gói
    resetNewPackageForm() {
      this.newPackage = {
        packageName: "",
        price: 0,
        accessDuration: 0,
        description: "",
      };
    },

   // Thêm gói dịch vụ mới
    async addPackage() {
      try {
        const response = await axios.post(
          "http://localhost:8080/packages/add",
          this.newPackage
        );

        if (response.data.success) {
          // Nếu success là true, tiến hành các bước tiếp theo
          this.packages.push(response.data.data);
          this.closeAddPackageForm();
          this.showNotification("Thêm gói thành công!");
        } else {
          // Nếu success là false, hiển thị thông báo lỗi từ response
          this.showNotification(response.data.desc, "error");
        }
      } catch (error) {
        console.error("Lỗi khi thêm gói dịch vụ:", error);
        this.showNotification("Có lỗi xảy ra khi thêm gói.", "error");
      }
    },

    // Xóa gói dịch vụ
    async deletePackage(packageId) {
      try {
        const response = await axios.delete(
          `http://localhost:8080/packages/delete/${packageId}`
        );

        if (response.data.success) {
          // Nếu success là true, tiến hành xóa gói và hiển thị thông báo
          this.packages = this.packages.filter((pkg) => pkg.id !== packageId);
          this.showNotification(response.data.desc);
        } else {
          // Nếu success là false, hiển thị thông báo lỗi từ response
          this.showNotification(response.data.desc, "error");
        }
      } catch (error) {
        console.error("Lỗi khi xóa gói dịch vụ:", error);
        this.showNotification("Có lỗi xảy ra khi xóa gói.", "error");
      }
    },

    showNotification(message, type = 'success') {
      this.notificationMessage = message;
      this.notificationType = type;
      this.notificationVisible = true;
    },
    // Định dạng giá tiền
    formatPrice(price) {
      return new Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND",
      }).format(price);
    },
  },
};
</script>
<style scoped>
.package-management {
  max-width: 1200px;
  margin: 20px auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  font-size: 24px;
  color: #333;
  margin-bottom: 20px;
}

.add-package-btn {
  display: block;
  margin: 0 auto 20px;
  padding: 10px 20px;
  font-size: 16px;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.add-package-btn:hover {
  background-color: #0056b3;
}

.package-cards {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.package-card {
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #fff;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s, box-shadow 0.2s;
  color:#333;
}

.package-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.package-card h3 {
  font-size: 18px;
  color: #007bff;
  margin-bottom: 10px;
}

.delete-btn {
  display: block;
  margin-top: 10px;
  padding: 5px 10px;
  font-size: 14px;
  color: #fff;
  background-color: #dc3545;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.delete-btn:hover {
  background-color: #a71d2a;
}

/* Form Thêm Gói */
.add-package-form {
  margin-top: 30px;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.add-package-form h2 {
  text-align: center;
  margin-bottom: 20px;
}

.add-package-form label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color:#333;
}

.add-package-form input,
.add-package-form textarea {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.submit-btn {
  padding: 10px 20px;
  font-size: 16px;
  color: #fff;
  background-color: #28a745;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.submit-btn:hover {
  background-color: #218838;
}

.cancel-btn {
  padding: 10px 20px;
  font-size: 16px;
  color: #fff;
  background-color: #6c757d;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-left: 10px;
}

.cancel-btn:hover {
  background-color: #5a6268;
}
</style>
