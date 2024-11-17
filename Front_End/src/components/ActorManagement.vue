<template>
  <div class="actor-management">
    <div v-if="!showModal">
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
    </div>

     <!-- Modal chi tiết diễn viên -->
    <div v-if="showModal">
      <button @click="closeModal" class="back-btn">Quay lại</button>
      <h2>Danh sách phim của {{ selectedActor.nameActor }}</h2>
      <GridComponent>
        <MovieCard 
          v-for="movie in movies" 
          :key="movie.id" 
          :movie="movie" 
        />
      </GridComponent>
    </div>
  </div>
</template>

<script>
import axios from "@/axios.js";
import GridComponent from "./GridComponent.vue";
import MovieCard from "./MovieCard.vue";

export default {
  components: {
    GridComponent,
    MovieCard,
  },
  data() {
    return {
      actors: [], // Danh sách diễn viên
      selectedActor: {}, // Thông tin diễn viên được chọn
      showModal: false, // Trạng thái hiển thị modal
      movies: [], // Danh sách phim của diễn viên
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
    async fetchMovies(actorId) {
      try {
        const response = await axios.get(`http://localhost:8080/actors/${actorId}/movies`);
        this.selectedActor = response.data.data;
        this.movies = this.selectedActor.movies; // Lưu danh sách phim
      } catch (error) {
        console.error("Lỗi khi lấy danh sách phim:", error);
      }
    },
    openActorModal(actor) {
      this.selectedActor = actor; // Lưu thông tin diễn viên được chọn
      this.showModal = true; // Hiển thị modal
      this.fetchMovies(actor.id); // Lấy phim của diễn viên
    },
    closeModal() {
      this.showModal = false; // Đóng modal
      this.selectedActor = {};
      this.movies = [];
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

.actor-management h2{
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
  color: #007bff;
}

.actor-management h3{
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
</style>
