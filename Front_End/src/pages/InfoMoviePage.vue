<template>
  <div v-if="!movieDataFound" class="error-message">
      <p>Không tìm thấy phim hoặc phim đã bị ẩn!</p>
  </div>
  <div v-if="movieDataFound" class="info-movie-page">
    <Notification
      v-if="notificationVisible"
      :message="notificationMessage"
      :type="notificationType"
      @close="notificationVisible = false"
    />
      <div class="left-section">
        <img v-if="movieData.poster" :src="movieData.poster" alt="Poster" />
      </div>
      <div class="right-section">
        <h2 v-if="movieData.movieName">{{ movieData.movieName }}</h2>
        <p class="episode-info">{{ movieData.totalEpisodes }} Tập</p>
        <p v-if="movieData.country && movieData.country.countryName">{{ movieData.release_year.year }} · {{ movieData.country.countryName }}</p>
        <p v-if="movieData.director">
          <strong>Đạo diễn:</strong> {{ movieData.director }}
        </p>
        <p v-if="movieData.genre && movieData.genre.genreName">
          <strong>Thể Loại:</strong> {{ movieData.genre.genreName }}
        </p>
        <p v-if="movieData.ratingMean">
          <strong>Đánh giá: </strong>
          <span
            v-for="star in 10"
            :key="star"
            class="rating-star"
            :class="{ filled: isStarFilled(star) }"
            @mouseover="hoveredStar = star"
            @mouseleave="hoveredStar = null"
            @click="rateMovie(star)"
          >
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" class="star-icon">
              <path d="M12 .587l3.668 7.43 8.292 1.207-6.004 5.869 1.417 8.267L12 18.897l-7.373 3.872 1.417-8.267-6.004-5.869 8.292-1.207z" />
            </svg>
          </span>
          <span>{{ movieData.ratingMean }}/10</span>
        </p>
        <p v-if="movieData.views">
          <strong>Lượt xem : </strong> {{ movieData.views }}
        </p>
        <div class="button-group">
          <button class="watch-now-btn">Xem Ngay</button>
          <button class="follow-btn" @click="addToFavorites">+ Thêm phim yêu thích</button>
        </div>
      </div>
  </div>

  <div v-if="movieDataFound" class="summary-section">
    <h3>Tóm Tắt</h3>
    <p v-if="movieData.description">{{ movieData.description }}</p>
  </div>

  <div v-if="movieDataFound" class="trailer-section">
    <h3>Trailer</h3>
    <iframe
      v-if="isValidURL(movieData.trailer)"
      :src="movieData.trailer"
      title="Trailer"
      width="100%"
      height="400"
      frameborder="0"
      allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
      allowfullscreen
    ></iframe>
    <p v-else>No Trailer</p>
  </div>
</template>

<script>
import axios from '@/axios';
import { mapGetters } from 'vuex';
import Notification from '../components/Notification.vue';

export default {
  name: 'InfoMoviePage',
  components: {
    Notification,
  },
  data() {
    return {
      movieData: {
        ratingMean: 0,
      },
      hoveredStar: null, // Sao hiện đang được hover bởi người dùng
      notificationVisible: false,
      notificationMessage: '',
      notificationType : 'success',
      movieDataFound: true,
    };
  },
  computed: {
    ...mapGetters(['userId']), // Lấy userId từ Vuex store
  },
  created() {
    this.fetchMovieData();
  },
  methods: {
    async fetchMovieData() {
      try {
        const response = await axios.get(`http://localhost:8080/movies/${this.$route.params.id}`);
        if (response.status === 200 && response.data.success) {
          this.movieData = response.data.data;
          this.movieDataFound = true; 
        } else {
          this.movieDataFound = false; // Phim không tìm thấy
          // this.showNotification(response.data.desc || 'Không tìm thấy phim hoặc phim đã bị ẩn!', 'error');
        }
      } catch (error) {
        console.error("Lỗi khi lấy thông tin phim", error);
      }
    },
    async rateMovie(star) {
      try {
        const response = await axios.post(
          `http://localhost:8080/api/ratings/addOrUpdate?userId=${this.userId}&movieId=${this.$route.params.id}&star=${star}`
        );
        if (response.status === 200 && response.data.success) {
          await this.fetchMovieData();
          this.showNotification('Bạn đã đánh giá thành công!');
          this.hoveredStar = null;
          // alert(response.data.desc); // Hiển thị thông báo thành công
        }
      } catch (error) {
        console.error("Lỗi khi đánh giá phim", error);
      }
    },
    async addToFavorites() {
      try {
        const response = await axios.post('http://localhost:8080/user/favoriteMovie/add', {
          userId: this.userId,
          movieId: this.$route.params.id,
        });
        if (response.status === 200 && response.data.success) {
          this.showNotification('Đã thêm phim vào danh sách yêu thích');
        }
        else{
          this.showNotification(response.data.desc, 'error');
        }
      } catch (error) {
        console.error("Lỗi khi thêm phim vào danh sách yêu thích", error);
      }
    },
    showNotification(message, type = 'success') {
      this.notificationMessage = message;
      this.notificationType = type;
      this.notificationVisible = true;
    },
    isStarFilled(star) {
      // Kiểm tra nếu `hoveredStar` tồn tại thì dùng nó, nếu không thì dùng `ratingMean`
      const currentRating = this.hoveredStar !== null ? this.hoveredStar : this.movieData.ratingMean;
      console.log(`Star: ${star}, Current Rating: ${currentRating}, Filled: ${star <= currentRating}`);
      return star <= currentRating;
    },
    isValidURL(url) {
      const pattern = new RegExp('^(https?:\\/\\/)?'+ // protocol
        '((([a-zA-Z\\d]([a-zA-Z\\d-]*[a-zA-Z\\d])*))\\.?)+(:\\d+)?(\\/[-a-zA-Z\\d%_.~+]*)*'+ // domain and port
        '(\\?[;&a-zA-Z\\d%_.~+=-]*)?'+ // query string
        '(#[-a-zA-Z\\d_]*)?$','i'); // fragment locator
      return !!pattern.test(url);
    },
  },
};
</script>

<style scoped>
.info-movie-page {
  display: flex;
  gap: 20px;
  padding: 20px;
  background-color: #333;
  color: #fff;
}

.left-section {
  flex: 1;
  max-width: 250px;
}

.left-section img {
  width: 100%;
  height: auto;
  border-radius: 8px;
}

.right-section {
  flex: 3;
  display: flex;
  flex-direction: column;
  gap: 2px;
  line-height: 0.3;
}

.right-section h2 {
  font-size: 1.5em;
  margin-bottom: 5px;
}

.episode-info {
  background-color: #c5a880;
  color: #000;
  padding: 5px 10px;
  border-radius: 4px;
  width: fit-content;
}

.rating-star {
  width: 24px;
  height: 24px;
  cursor: pointer;
}

.star-icon {
  fill: #d1d1d1; /* Màu mặc định cho sao */
}

.rating-star.filled .star-icon {
  fill: #ffd700; /* Màu cho sao đã đánh giá */
}

.button-group {
  display: flex;
  gap: 10px;
  margin-top: 10px;
}

button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
}
.button-group button:hover {
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.5); /* Hiệu ứng đổ bóng khi hover */
}

.watch-now-btn {
  background-color: #e50914;
  color: #fff;
}

.follow-btn {
  background-color: #555;
  color: #fff;
}
.watch-now-btn:hover {
  background-color: #d40812b1; /* Màu nền đậm hơn khi hover */
}

.follow-btn:hover {
  background-color: #444; /* Màu nền đậm hơn khi hover */
}
.summary-section {
  background-color: #444;
  padding: 15px;
  border-radius: 5px;
}

.trailer-section {
  margin-top: 20px;
}
.error-message {
  display: block; /* hoặc inline-block nếu bạn cần */
  width: 100%; /* Đảm bảo rằng nó không làm giảm chiều rộng của container */
  margin-bottom: 20px; /* Khoảng cách giữa thông báo và phần tử bên dưới */
  color: red; /* Màu sắc cho thông báo lỗi */
}
</style>
