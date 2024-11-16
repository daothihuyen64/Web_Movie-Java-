<template>
  <div class="actor-management">
    <h2>Danh sách Diễn Viên</h2>
    <div v-if="groupedActors">
      <div v-for="(actors, letter) in groupedActors" :key="letter" class="actor-group">
        <h3 class="group-title">{{ letter }}</h3>
        <div class="actor-cards">
          <div 
            v-for="actor in actors" 
            :key="actor.id" 
            class="actor-card" 
            @click="openActorModal(actor)"
          >
            <h3>{{ actor.nameActor }}</h3>
           
          </div>
        </div>
      </div>
    </div>

    <!-- Modal chi tiết diễn viên -->
    <div v-if="showModal" class="modal-overlay">
      <div class="modal-content">
        <button class="close-btn" @click="closeModal">X</button>
        <h3>Thông tin chi tiết Diễn Viên</h3>
        <p><strong>Tên:</strong> {{ selectedActor.nameActor }}</p>
    
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/axios.js";

export default {
  data() {
    return {
      actors: [], // Danh sách diễn viên
      selectedActor: {}, // Thông tin diễn viên được chọn
      showModal: false, // Trạng thái hiển thị modal
    };
  },
  computed: {
    // Nhóm diễn viên theo chữ cái đầu tiên của tên
    groupedActors() {
      return this.actors.reduce((groups, actor) => {
        const firstLetter = actor.nameActor.charAt(0).toUpperCase();
        if (!groups[firstLetter]) {
          groups[firstLetter] = [];
        }
        groups[firstLetter].push(actor);
        return groups;
      }, {});
    },
  },
  created() {
    this.fetchActors();
  },
  methods: {
    async fetchActors() {
      try {
        const response = await axios.get("http://localhost:8080/actors/all");
        this.actors = response.data.data; // Lấy danh sách diễn viên từ API
      } catch (error) {
        console.error("Lỗi khi lấy danh sách diễn viên:", error);
      }
    },
    openActorModal(actor) {
      this.selectedActor = actor; // Lưu thông tin diễn viên được chọn
      this.showModal = true; // Hiển thị modal
    },
    closeModal() {
      this.showModal = false; // Đóng modal
    },
  },
};
</script>

<style scoped>
.actor-management {
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.actor-management h2 {
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
  color: #007bff;
}

.actor-group {
  margin-bottom: 20px;
}

.group-title {
  font-size: 20px;
  color: #555;
  margin-bottom: 10px;
  text-decoration: underline;
}

.actor-cards {
  display: flex;
  flex-direction: column; /* Các card chiếm 1 hàng */
  gap: 10px;
}

.actor-card {
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #ffffff;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s, box-shadow 0.2s;
  text-align: left;
  cursor: pointer;
}

.actor-card h3 {
  font-size: 18px;
  color: #333;
  margin-bottom: 5px;
}

.actor-card p {
  font-size: 14px;
  color: #555;
}

.actor-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
}

/* Modal Overlay */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5); /* Nền mờ */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

/* Modal nội dung */
.modal-content {
  background-color: #ffffff;
  border-radius: 8px;
  padding: 20px;
  max-width: 400px;
  width: 90%;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  position: relative;
  text-align: center;
}

.modal-content h3 {
  font-size: 22px;
  color: #333;
  margin-bottom: 15px;
}

.modal-content p {
  font-size: 16px;
  color: #555;
}

.close-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 20px;
  background: transparent;
  border: none;
  color: #333;
  cursor: pointer;
}

.close-btn:hover {
  color: red;
}
</style>
