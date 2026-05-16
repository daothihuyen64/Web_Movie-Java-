<!-- IdGenreMoviesPage.vue -->
<template>
  <div>
    <h2>{{genreName}}</h2>
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
  name: 'IdGenreMoviesPage',
  components: {
    GridComponent,
    MovieCard
  },
  props: {
    genreId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      movies: [], // Danh sách phim sẽ được lấy từ API
      genreName : ''
    };
  },
  created() {
    this.fetchMoviesByGenre(); // Gọi hàm lấy danh sách phim khi component được tạo
  },
  watch: {
    genreId() {
      this.fetchMoviesByGenre(); // Gọi lại hàm khi genreId thay đổi
    }
  },
  methods: {
    async fetchMoviesByGenre() {
      try {
        const response = await axios.get(`http://localhost:8080/api/genres/${this.genreId}`);
        if (response.status === 200 && response.data.success) {
          // Gán danh sách phim vào biến movies
          this.movies = response.data.data.movieList;
          this.genreName = response.data.data.genreName;
        }
      } catch (error) {
        console.error('Lỗi khi lấy danh sách phim của thể loại', error);
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
