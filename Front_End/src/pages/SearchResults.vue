<template>
  <div class="movie-result-page">
    <h2 class="page-title">Kết quả tìm kiếm</h2>
    <div v-if="results.length === 0" class="no-results">
      <p>Không tìm thấy kết quả nào.</p>
    </div>
    <div v-else>
      <div class="results-grid">
        <div
          class="result-card"
          v-for="item in results"
          :key="item.id"
          @click="goToMovieInfo(item.id)"
        >
          <div class="poster-container">
            <img
              :src="item.poster "
              alt="Poster"
              class="poster"
            />
          </div>
          <div class="details">
            <h4 class="movie-title">{{ item.movieName }}</h4>
            <p class="rating">⭐ {{ item.ratingMean }}/10</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import axios from '@/axios';
export default {
  name: 'SearchResult',
  data() {
    return {
      searchType: this.$route.query.type || '',
      searchQuery: this.$route.query.q || '',
      results: [], // Kết quả tìm kiếm sẽ được lấy từ API
    };
  },
  created() {
    this.fetchResults(); // Gọi API khi trang được tải
  },
  watch: {
    // Theo dõi thay đổi trong query để gọi API lại nếu người dùng thực hiện tìm kiếm mới
    '$route.query': 'handleQueryChange',
  },
  methods: {
      async fetchResults() {
        if (!this.searchQuery.trim()) {
          this.results = [];
          return;
        }

      const endpoint = this.searchType === 'movie'
        ? 'http://localhost:8080/user/search/name'
        : 'http://localhost:8080/user/search/actor';

      const params = this.searchType === 'movie'
        ? { movieName: this.searchQuery }
        : { actorName: this.searchQuery };

      try {
        const response = await axios.get(endpoint, { params });
        if (response.status === 200) {
          this.results = response.data; // Gán dữ liệu API vào biến results
        } else {
          this.results = [];
        }
      } catch (error) {
        console.error('Lỗi khi gọi API:', error);
        this.results = [];
      }
    },
    handleQueryChange() {
      // Cập nhật dữ liệu khi query thay đổi
      this.searchType = this.$route.query.type || '';
      this.searchQuery = this.$route.query.q || '';
      this.fetchResults(); // Gọi lại API
    },
    goToMovieInfo(movieId) {
      this.$router.push({ 
        name: 'InfoMoviePage', 
        params: { id: movieId },
      });
    },
  },
};

</script>

<style scoped>
.movie-result-page {
  padding: 20px;
  font-family: Arial, sans-serif;
  background-color: #333;
  min-height: 100vh;
}

.page-title {
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
  color: #fff;
}

.no-results {
  text-align: center;
  color: #aaa;
  font-size: 18px;
  margin-top: 50px;
}

.results-grid {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.result-card {
  display: flex;
  background-color: #444;
  border-radius: 10px;
  overflow: hidden;
  transition: transform 0.2s, box-shadow 0.2s;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  padding: 10px;
}

.result-card:hover {
  transform: scale(1.02);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.4);
}

.poster-container {
  flex-shrink: 0;
  width: 150px;
  height: 225px;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #222;
  margin-right: 15px;
}

.poster {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.details {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.movie-title {
  font-size: 18px;
  font-weight: bold;
  color: #fff;
  margin: 0 0 5px 0;
}

.rating {
  font-size: 16px;
  color: #ffcc00;
  margin-top: 5px;
}
</style>