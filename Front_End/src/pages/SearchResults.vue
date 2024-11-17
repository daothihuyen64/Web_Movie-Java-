<template>
  <div class="search-result-page">
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
        >
          <div class="poster-container">
            <img 
              :src="item.poster || 'https://via.placeholder.com/150'" 
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
  },
};

</script>

<style scoped>
/* Tổng quan cho trang */
.search-result-page {
  padding: 20px;
  font-family: Arial, sans-serif;
  background-color: #f9f9f9;
  min-height: 100vh;
}

.page-title {
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}

/* Khi không có kết quả */
.no-results {
  text-align: center;
  color: #888;
  font-size: 18px;
  margin-top: 50px;
}

/* Lưới kết quả */
.results-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

/* Card cho từng phim */
.result-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  border: 1px solid #ddd;
  border-radius: 10px;
  overflow: hidden;
  background-color: #fff;
  transition: transform 0.2s, box-shadow 0.2s;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.result-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

/* Ảnh Poster */
.poster-container {
  width: 100%;
  height: 300px;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f0f0f0;
}

.poster {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* Chi tiết phim */
.details {
  padding: 10px;
  text-align: center;
}

.movie-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
  color: #333;
}

.rating {
  font-size: 14px;
  color: #666;
  margin-bottom: 10px;
}
</style>
