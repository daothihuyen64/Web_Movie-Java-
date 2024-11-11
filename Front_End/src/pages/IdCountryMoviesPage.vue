<!-- IdGenreMoviesPage.vue -->
<template>
  <div>
    <h2> {{ countryName }}</h2>
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
  name: 'IdCountryMoviesPage',
  components: {
    GridComponent,
    MovieCard
  },
  props: {
    countryId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      movies: [], // Danh sách phim sẽ được lấy từ API
      countryName: '' 
    };
  },
  created() {
    this.fetchMoviesByCountry(); // Gọi hàm lấy danh sách phim khi component được tạo
  },
  watch: {
    countryId() {
      this.fetchMoviesByCountry(); // Gọi lại hàm khi genreId thay đổi
    }
  },
  methods: {
    async fetchMoviesByCountry() {
      try {
        const response = await axios.get(`http://localhost:8080/api/countries/${this.countryId}`);
        if (response.status === 200 && response.data.success) {
          // Gán danh sách phim vào biến movies
          this.movies = response.data.data.movieList;
          this.countryName = response.data.data.countryName;
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
