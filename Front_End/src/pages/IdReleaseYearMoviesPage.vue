<!-- IdGenreMoviesPage.vue -->
<template>
  <div>
    <h2>{{year}}</h2>
    <GridComponent>
      <!-- Lặp qua danh sách phim và truyền từng phim vào MovieCard -->
      <MovieCard v-for="movie in movies" :key="movie.id" :movie="movie" />
    </GridComponent>
  </div>
</template>

<script>
import axios from '@/axios';
import GridComponent from '../components/GridComponent.vue';
import MovieCard from '../components/MovieCard.vue';

export default {
  name: 'IdReleaseYearMoviesPage',
  components: {
    GridComponent,
    MovieCard
  },
  props: {
    releaseYearId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      movies: [], // Danh sách phim sẽ được lấy từ API
      year : ''
    };
  },
  created() {
    this.fetchMoviesByReleaseYear(); // Gọi hàm lấy danh sách phim khi component được tạo
  },
  watch: {
    releaseYearId() {
      this.fetchMoviesByReleaseYear(); // Gọi lại hàm khi genreId thay đổi
    }
  },
  methods: {
    async fetchMoviesByReleaseYear() {
      try {
        const response = await axios.get(`http://localhost:8080/api/releaseyear/${this.releaseYearId}`);
        if (response.status === 200 && response.data.success) {
          // Gán danh sách phim vào biến movies
          this.movies = response.data.data.movies;
          this.year = response.data.data.year;
        }
      } catch (error) {
        console.error('Lỗi khi lấy danh sách phim theo năm', error);
      }
    }
  }
};
</script>

<style scoped>
h2 {
  text-align: center;
  color: #fff;
  margin-top: 20px;
}
</style>
