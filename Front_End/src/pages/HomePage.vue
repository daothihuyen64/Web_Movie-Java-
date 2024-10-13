<template>
 <TrendingMovies :title="'Thịnh Hành'" :movies="trendingMovies" />
 <h2>Phim Mới</h2>
 <GridComponent>
    <div v-for="(movie, index) in paginatedMovies" :key="index">
        <MovieCard :movie="movie" />
    </div>
  </GridComponent>
  <!-- Phần pagination -->
  <PaginationComponent 
      :totalItems="movies.length"
      :itemsPerPage="itemsPerPage"
      :currentPage="currentPage"
      @page-changed="onPageChange"
    />
</template>

<script>
import MovieCard from '../components/MovieCard.vue';
import GridComponent from '../components/GridComponent.vue';
import TrendingMovies from '../components/TrendingMovies.vue';
import PaginationComponent from '../components/PaginationComponent.vue';

export default {
  name: 'HomePage',
  components: {
    MovieCard,
    GridComponent,
    TrendingMovies,
    PaginationComponent 
  },
  data() {
    return {
      currentPage: 1,
      itemsPerPage: 20,
      trendingMovies: [
        {
          id:1,
          title: 'Inception',
          episode: "Full 24/24 Vietsub",
          year: 2010,
          // image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:1,
          title: 'Inception',
          episode: "Full 24/24 Vietsub",
          year: 2010,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:1,
          title: 'Inception',
          episode: "Full 24/24 Vietsub",
          year: 2010,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:1,
          title: 'Inception',
          episode: "Full 24/24 Vietsub",
          year: 2010,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:1,
          title: 'Inception',
          episode: "Full 24/24 Vietsub",
          year: 2010,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:1,
          title: 'Inception',
          episode: "Full 24/24 Vietsub",
          year: 2010,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:1,
          title: 'Inception',
          episode: "Full 24/24 Vietsub",
          year: 2010,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:1,
          title: 'Inception',
          episode: "Full 24/24 Vietsub",
          year: 2010,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        }
      ],
      movies: [
        {
          id:1,
          title: 'Inception',
          episode: "Full 24/24 Vietsub",
          year: 2010,
          image: 'https://image.tmdb.org/t/p/w500/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg',
        },
        {
          id:2,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:3,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:4,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },        // Thêm nhiều phim hơn vào danh sách
        {
          id:5,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:6,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:7,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:8,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:9,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:1,
          title: 'Inception',
          episode: "Full 24/24 Vietsub",
          year: 2010,
          image: 'https://image.tmdb.org/t/p/w500/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg',
        },
        {
          id:2,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:3,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:4,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },        // Thêm nhiều phim hơn vào danh sách
        {
          id:5,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:6,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:7,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:8,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:9,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:1,
          title: 'Inception',
          episode: "Full 24/24 Vietsub",
          year: 2010,
          image: 'https://image.tmdb.org/t/p/w500/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg',
        },
        {
          id:2,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:3,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:4,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },        // Thêm nhiều phim hơn vào danh sách
        {
          id:5,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:6,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:7,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:8,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
        {
          id:9,
          title: 'The Matrix',
          episode: "Full 24/24 Vietsub",
          year: 1999,
          image: 'https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg',
        },
      ]
    };
  },
  computed: {
    paginatedMovies() {
      const start = (this.currentPage - 1) * this.itemsPerPage;
      const end = start + this.itemsPerPage;
      return this.movies.slice(start, end);
    }
  },
  methods: {
    onPageChange(page) {
      this.currentPage = page;
    }
  }
};
</script>

<style scoped>
/* .movie-gallery {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  padding: 20px;
  justify-content: center;
} */
</style>
