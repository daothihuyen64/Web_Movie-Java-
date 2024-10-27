<template>
  <div class="favorite-movies-container">
    <h2>Tủ phim yêu thích</h2>
    <GridComponent>
      <MovieCard 
        v-for="movie in favoriteMovies" 
        :key="movie.id" 
        :movie="movie" 
      />
    </GridComponent>
    <p v-if="!favoriteMovies.length">Không có phim yêu thích nào.</p>
  </div>
</template>

<script>
import axios from "@/axios"; // Sử dụng axios đã cấu hình sẵn
import { mapGetters } from "vuex";
import MovieCard from '../components/MovieCard.vue'; // Nhập MovieCard
import GridComponent from '../components/GridComponent.vue'; // Nhập GridComponent

export default {
  data() {
    return {
      favoriteMovies: [], // Lưu danh sách phim yêu thích
    };
  },
  computed: {
    ...mapGetters(["userId"]), // Lấy userId từ state
  },
  created() {
    // Gọi API để lấy danh sách phim yêu thích khi component được tạo
    this.fetchFavoriteMovies();
  },
  methods: {
    async fetchFavoriteMovies() {
      try {
        const response = await axios.get(`user/favoriteMovie/${this.userId}`);
        if (response.status === 200) {
          this.favoriteMovies = response.data;
        }
      } catch (error) {
        console.error("Lỗi khi lấy danh sách phim yêu thích", error);
      }
    },
  },
  components: {
    MovieCard, // Đăng ký MovieCard
    GridComponent, // Đăng ký GridComponent
  },
};
</script>

<style scoped>
.favorite-movies-container {
  padding: 20px;
  background-color: #333;
  color: #fff;
}

p {
  color: #ddd; /* Đặt màu cho văn bản khi không có phim yêu thích */
}
</style>
