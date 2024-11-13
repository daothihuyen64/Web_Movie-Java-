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
          <button class="watch-now-btn" @click="goToWatchMovie">Xem Ngay</button>
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
  <div class="comments-section">
    <h3>{{ comments.length }} bình luận</h3>
    <div class="comment-input">
      <input v-model="newComment" placeholder="Viết bình luận..." />
      <button @click="postComment">Đăng</button>
    </div>
    <div v-for="comment in comments" :key="comment.id" class="comment">
      <div class="comment-header">
        <span class="nickname">{{ comment.nickName }}</span>
        <span class="time">{{ formatTime(comment.time) }}</span>
      </div>
      <p class="content">{{ comment.content }}</p>
      <div class="comment-actions" v-if="comment.reactions">
        <span v-for="emoji in ['ANGRY', 'HEART', 'SURPRISED', 'THUMBS_UP']" 
              :key="emoji"
              @click="reactToComment(comment.id, emoji)">
          {{ comment.reactions[emoji] || 0 }} {{ getEmoji(emoji) }}
        </span>
        <span v-if="comment.nickName === this.nickname" @click="deleteComment(comment.id)">Xóa bình luận</span>
      </div>
    </div>
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
      comments: [],
      newComment: '',
      currentUser: "",
    };
  },
  computed: {
    ...mapGetters(['userId']), // Lấy userId từ Vuex store
    ...mapGetters(['nickname']),
  },
  created() {
    this.fetchMovieData();
    this.fetchComments();
  },
  
  methods: {
    async fetchComments() {
      try {
        const response = await axios.get(`http://localhost:8080/comment/movie/${this.$route.params.id}`);
        if (response.status === 200) {
          this.comments = response.data;
          await Promise.all(
            this.comments.map(async (comment) => {
              try {
                const reactResponse = await axios.get(`http://localhost:8080/react/${comment.id}`);
                if (reactResponse.status === 200) {
                  const defaultReactions = { SURPRISED: 0, HEART: 0, THUMBS_UP: 0, ANGRY: 0 };
                  comment.reactions = {
                    ...defaultReactions,
                    ...reactResponse.data
                  };
                } else {
                  comment.reactions = { SURPRISED: 0, HEART: 0, THUMBS_UP: 0, ANGRY: 0 }; // Giá trị mặc định nếu lỗi
                }
              } catch (error) {
                console.error("Lỗi khi lấy react cho bình luận", error);
                comment.reactions = { SURPRISED: 0, HEART: 0, THUMBS_UP: 0, ANGRY: 0 }; // Giá trị mặc định nếu lỗi
              }
            })
          );
        }
      } catch (error) {
        console.error("Lỗi khi lấy bình luận", error);
      }
    },
    async postComment() {
      if (!this.newComment.trim()) return;
      try {
        const response = await axios.post(`http://localhost:8080/comment/add`, {
          movieId: this.$route.params.id,
          content: this.newComment,
          userId: this.userId, // Giả sử bạn đã lấy userId từ Vuex
        });
        if (response.status === 200) {
          this.newComment = '';
          this.fetchComments(); // Load lại bình luận sau khi đăng
        }
      } catch (error) {
        console.error("Lỗi khi đăng bình luận", error);
      }
    },
    async deleteComment(commentId) {
      try {
        const response = await axios.delete(`http://localhost:8080/comment/delete/${commentId}`);
        if (response.status === 200 && response.data.success) {
          this.fetchComments();
        }
      } catch (error) {
        console.error("Lỗi khi lấy bình luận", error);
      }
    },
    async reactToComment(commentId, reactType) {
      try {
        // Bước 1: Xóa react cũ
        const deleteResponse = await axios.delete(`http://localhost:8080/react/delete`, {
          params: {
            userId: this.userId,
            commentId: commentId
          }
        });

        if (deleteResponse.status === 200) {
          console.log('Xóa react cũ thành công');

          // Bước 2: Thêm react mới
          const addResponse = await axios.post(`http://localhost:8080/react/add`, null, {
            params: {
              userId: this.userId,
              commentId: commentId,
              reactType: reactType
            }
          });

          if (addResponse.status === 200 && addResponse.data.success) {
            console.log('Thêm react mới thành công');
            this.fetchComments(); // Load lại danh sách bình luận để cập nhật react
          } else {
            console.error('Thêm react mới thất bại');
          }
        } else {
          console.error('Xóa react cũ thất bại');
        }
      } catch (error) {
        console.error("Lỗi khi thêm/xóa react", error);
      }
    },
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
          this.show = false,
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
     formatTime(time) {
      // Định dạng thời gian thành kiểu dễ đọc, ví dụ: 1 tuần trước
      const date = new Date(time);
      return date.toLocaleString("vi-VN", { year: 'numeric', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit' });
    },
    getEmoji(reactType) {
      const emojis = {
        ANGRY: '😠',
        HEART: '❤️',
        SURPRISED: '😮',
        THUMBS_UP: '👍'
      };
      return emojis[reactType] || '';
    },
    goToWatchMovie() {
      if (this.movieData) {
        this.$router.push({
          name: 'WatchMoviePage',
          params: {
            id: this.movieData.id,
          },
        });
      }
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
.comments-section {
  margin-top: 20px;
  background-color: #444;
  padding: 15px;
  border-radius: 5px;
}

.comment-input {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.comment-input input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.comment-input button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #e50914;
  color: #fff;
  cursor: pointer;
}

.comment {
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 1px solid #555;
}

.comment-header {
  font-size: 0.9em;
  color: #bbb;
  display: flex;
  justify-content: space-between;
}

.nickname {
  font-weight: bold;
}

.time {
  font-size: 0.8em;
  color: #888;
}

.content {
  margin: 5px 0;
}

.comment-actions span {
  color: #888;
  cursor: pointer;
  margin-right: 15px;
}
.comment-actions span:hover {
  text-decoration: underline;
}
.comment-actions span {
  font-size: 1.2em;
  margin-right: 10px;
  cursor: pointer;
}
.comment-actions span:hover {
  transform: scale(1.2); /* Phóng to emoji khi hover */
}
</style>
