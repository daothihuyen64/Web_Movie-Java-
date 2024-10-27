<template>
  <div class="info-movie-page">
    <h2 v-if="movieData.movieName">{{ movieData.movieName }}</h2>
    <img v-if="movieData.poster" :src="movieData.poster" alt="Poster" />
    <p v-if="movieData.description"><strong>Mô tả:</strong> {{ movieData.description }}</p>
    <p v-if="movieData.director"><strong>Đạo diễn:</strong> {{ movieData.director }}</p>
    <p v-if="movieData.ratingMean"><strong>Rating:</strong> {{ movieData.ratingMean }}</p>
    <p v-if="movieData.genre && movieData.genre.genreName"><strong>Thể loại:</strong> {{ movieData.genre.genreName }}</p>
    <p v-if="movieData.release_year && movieData.release_year.year"><strong>Năm phát hành:</strong> {{ movieData.release_year.year }}</p>
    <p v-if="movieData.country && movieData.country.countryName"><strong>Quốc gia:</strong> {{ movieData.country.countryName }}</p>
    <p v-if="movieData.views"><strong>View:</strong> {{ movieData.views }}</p>
  </div>
</template>

<script>
import axios from '@/axios'; // Sử dụng axios đã cấu hình sẵn

export default {
  name: 'InfoMoviePage',
  data() {
    return {
      movieData: {}, // Dữ liệu phim
    };
  },
  created() {
    this.fetchMovieData(); // Gọi hàm lấy dữ liệu phim khi component được khởi tạo
  },
  methods: {
    async fetchMovieData() {
      try {
        const response = await axios.get(`http://localhost:8080/movies/${this.$route.params.id}`); // Gọi API để lấy thông tin phim
        if (response.status === 200 && response.data.success) {
          this.movieData = response.data.data; // Lưu dữ liệu phim vào biến movieData
        }
      } catch (error) {
        console.error("Lỗi khi lấy thông tin phim", error);
      }
    },
  },
};
</script>

<style scoped>
.info-movie-page {
  padding: 20px;
  background-color: #333;
  color: #fff;
}

img {
  max-width: 100%;
  height: auto;
}
</style>
