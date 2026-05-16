<template>
  <div>
    <!-- Bộ lọc rating -->
    <div class="filter-rating">
      <label for="rating-filter">Chọn rating:</label>
      <select id="rating-filter" v-model="selectedRating" @change="filterMovies">
        <option value="" selected>Tất cả</option>
        <option v-for="rating in 11" :key="rating" :value="rating - 1">
          {{ rating - 1 }}
        </option>
      </select>
    </div>

    <!-- Danh sách phim -->
    <div class="movies-list">
      <div v-for="movie in filteredMovies" :key="movie.id" class="movie-item" @click="goToMovieInfo(movie.id)">
        <img :src="movie.poster" alt="Poster" class="movie-poster" />
        <div class="movie-info">
          <h3 class="movie-name">{{ movie.movieName }}</h3>
          <p>Rating: {{ movie.ratingMean }}</p>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import axios from "@/axios";

export default {
  name: "RankingMovies",
  data() {
    return {
      movies: [], // Danh sách phim từ API
      selectedRating: "", // Giá trị rating được chọn, mặc định là tất cả
    };
  },
  computed: {
    // Tính toán danh sách phim được lọc theo rating
    filteredMovies() {
      if (this.selectedRating === "") {
        return this.movies; // Nếu không chọn gì, trả về toàn bộ danh sách
      }
      return this.movies.filter(
        (movie) => movie.ratingMean === parseInt(this.selectedRating)
      );
    },
  },
  created() {
    this.fetchMovies(); // Lấy danh sách phim từ API khi khởi tạo
  },
  methods: {
    goToMovieInfo(movieId) {
      this.$router.push({ 
        name: 'InfoMoviePage', 
        params: { id: movieId},
      });
    },
    async fetchMovies() {
      try {
        const response = await axios.get("http://localhost:8080/movies/ranking");
        if (response.data.success) {
          this.movies = response.data.data; // Lưu dữ liệu phim
        } else {
          console.error(response.data.desc);
        }
      } catch (error) {
        console.error("Lỗi khi tải danh sách phim:", error);
      }
    },
    filterMovies() {
      // Hàm xử lý lọc dựa trên selectedRating (đã được computed xử lý)
    },
  },
};
</script>

<style scoped>
/* Bộ lọc rating */
.filter-rating {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  gap: 10px; /* Giảm khoảng cách giữa các phần tử */
  margin-bottom: 15px;
  padding: 8px; /* Giảm kích thước padding */
  background-color: #222;
  border-radius: 6px; /* Bo tròn nhẹ hơn */
  box-shadow: 0 3px 5px rgba(0, 0, 0, 0.2);
}

.filter-rating label {
  font-size: 14px; /* Font nhỏ hơn */
  font-weight: bold;
  color: #fff;
}

.filter-rating select {
  padding: 6px 10px; /* Giảm kích thước padding */
  font-size: 14px; /* Font nhỏ hơn */
  border-radius: 6px; /* Bo tròn nhẹ hơn */
  border: 1px solid #555;
  background-color: #333;
  color: #fff;
  outline: none;
  cursor: pointer;
  transition: all 0.2s ease;
  width: 150px; /* Thu hẹp chiều rộng */
}

.filter-rating select:hover {
  background-color: #444;
  border-color: #777;
}

.filter-rating select:focus {
  border-color: #888;
  box-shadow: 0 0 4px rgba(255, 255, 255, 0.4);
}

.movies-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 20px;
  background-color: #333;
}

.movie-item {
  display: flex;
  align-items: center;
  gap: 20px;
  background-color: #444;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  transition: transform 0.2s ease, box-shadow 0.2s ease, background-color 0.2s ease;
}

.movie-item:hover {
  transform: translateY(-5px); /* Nhô lên khi hover */
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.4); /* Đổ bóng mạnh hơn */
  background-color: #555; /* Màu nền sáng hơn */
}

.movie-poster {
  width: 100px;
  height: 150px;
  object-fit: cover;
  border-radius: 5px;
  transition: transform 0.2s ease; /* Hiệu ứng khi hover ảnh */
}

.movie-item:hover .movie-poster {
  transform: scale(1.05); /* Phóng to nhẹ khi hover */
}

.movie-info {
  color: #fff;
}

.movie-name {
  margin: 0;
  font-size: 18px;
  font-weight: bold;
  transition: color 0.2s ease; /* Hiệu ứng màu chữ khi hover */
}

.movie-item:hover .movie-name {
  color: #ffcc00; /* Chữ đổi màu khi hover */
}
</style>
