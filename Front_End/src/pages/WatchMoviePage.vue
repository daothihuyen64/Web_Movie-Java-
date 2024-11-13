<template>
  <div class="watch-movie-container">
    <div class="video-section">
      <video v-if="videoUrl" :src="videoUrl" controls autoplay></video>
      <p v-else>{{ errorMessage }}</p>
    </div>
    <div class="movie-info" v-if="movieData">
      <h3>{{ movieData.movieName }} - {{ movieData.release_year.year }}</h3>
    </div>

    <div class="episode-buttons" v-if="movieData && movieData.totalEpisodes">
      <!-- Tạo nút cho mỗi tập phim dựa trên tổng số episodes -->
      <button
        v-for="index in movieData.totalEpisodes"
        :key="index"
        @click="playEpisode(index)"
      >
        Tập {{ index }}
      </button>
    </div>
  </div>
</template>

<script>
import axios from '@/axios';
import { mapGetters } from 'vuex';

export default {
  name: 'WatchMoviePage',
  data() {
    return {
      videoUrl: null,
      errorMessage: '',
      movieData: null, // Khai báo movieData
    };
  },
  computed: {
    ...mapGetters(["userId"]), // Lấy userId từ state
  },
  created() {
    // Lấy dữ liệu phim khi component được khởi tạo
    this.fetchMovieData();
    this.playEpisode(1);
  },
  methods: {
    async playEpisode(episodeNumber) {
      try {
        const response = await axios.post(`http://127.0.0.1:8080/episodes/start/${this.userId}/${episodeNumber}/${this.$route.params.id}`);
        
        if (response.data.success) {
          this.videoUrl = response.data.data; // URL của video
          this.errorMessage = ''; // Xóa thông báo lỗi trước đó
        } else {
          this.videoUrl = null;
          this.errorMessage = response.data.desc; // Hiển thị thông báo lỗi
        }
      } catch (error) {
        console.error('Error starting episode:', error);
        this.errorMessage = 'Failed to load the episode.'; // Hiển thị thông báo lỗi
      }
    },
    async fetchMovieData() {
      try {
        const response = await axios.get(`http://localhost:8080/movies/${this.$route.params.id}`);
        if (response.status === 200 && response.data.success) {
          this.movieData = response.data.data;
        } else {
          this.movieData = null;
          this.errorMessage = 'Movie not found or is hidden.'; // Hiển thị thông báo lỗi nếu không tìm thấy phim
        }
      } catch (error) {
        console.error("Lỗi khi lấy thông tin phim", error);
        this.errorMessage = 'Failed to fetch movie data.'; // Hiển thị thông báo lỗi
      }
    },
  }
};
</script>

<style scoped>
.watch-movie-container {
  margin: 16px;
  font-family: Arial, sans-serif;
}

.video-section video {
  width: 100%;
  max-height: 500px;
  border: 1px solid #ccc;
  border-radius: 8px;
  margin-bottom: 16px;
}

.movie-info {
  margin-top: 12px;
  font-size: 18px;
  color: #FFFFFF;
}

.movie-info h3 {
  font-size: 24px;
  font-weight: bold;
  margin: 0;
}

.episode-buttons {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 16px;
}

.episode-buttons button {
  padding: 8px 16px;
  background-color: #f3f3f3;
  border: 1px solid #ccc;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s;
}

.episode-buttons button:hover {
  background-color: #e0e0e0;
  transform: translateY(-2px);
}

.episode-buttons button:active {
  background-color: #d1d1d1;
  transform: translateY(0);
}

.episode-buttons button:focus {
  outline: none;
  box-shadow: 0 0 3px 2px rgba(100, 149, 237, 0.5); /* Highlight focus state */
}
</style>

