<template>
  <div class="homepage">
    <!-- Phần phim thịnh hành -->
    <TrendingMovies :title="'Phim Thịnh Hành'" :movies="trendingMovies" />

    <!-- Phần phim mới -->
    <h2>Phim Mới</h2>
    <GridComponent>
      <MovieCard 
        v-for="movie in paginatedNewReleases" 
        :key="movie.id" 
        :movie="movie" 
      />
    </GridComponent>

    <PaginationComponent 
      :totalItems="newReleases.length" 
      :itemsPerPage="itemsPerPage" 
      :currentPage="currentPage" 
      @page-changed="handlePageChange" 
    />
  </div>
</template>

<script>
import TrendingMovies from '../components/TrendingMovies.vue';
import GridComponent from '../components/GridComponent.vue';
import MovieCard from '../components/MovieCard.vue';
import PaginationComponent from '../components/PaginationComponent.vue';
import axios from '@/axios';

export default {
  name: 'HomePage',
  components: {
    TrendingMovies,
    GridComponent,
    MovieCard,
    PaginationComponent
  },
  data() {
    return {
      trendingMovies: [],
      newReleases: [],
      currentPage: 1,
      itemsPerPage: 20,
    };
  },
  computed: {
    // Tính toán các phim trong trang hiện tại
    paginatedNewReleases() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.newReleases.slice(start, end);
    }
  },
  methods: {
    async fetchTrendingMovies() {
      try {
        const response = await axios.get('http://localhost:8080/api/releaseyear/trending');
        if (response.data.success) {
          this.trendingMovies = response.data.data;
        }
      } catch (error) {
        console.error("Lỗi khi lấy phim thịnh hành:", error);
      }
    },
    async fetchNewReleases() {
      try {
        const response = await axios.get('http://localhost:8080/api/releaseyear/new-releases');
        if (response.data.success) {
          this.newReleases = response.data.data;
        }
      } catch (error) {
        console.error("Lỗi khi lấy phim mới:", error);
      }
    },
    handlePageChange(page) {
      this.currentPage = page;
    }
  },
  mounted() {
    this.fetchTrendingMovies();
    this.fetchNewReleases();
  }
};
</script>

<style scoped>
.homepage {
  padding: 20px;
}
</style>
