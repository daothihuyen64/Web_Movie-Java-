<template>
  <div class="trending-movies-section">
    <h2>{{ title }}</h2>
    <div class="trending-movies-container">
      <button @click="scrollLeft" class="arrow left-arrow">&lt;</button>
      <div class="trending-movies-list" ref="moviesList">
        <div class="trending-movie-card" v-for="(movie, index) in movies" :key="index">
          <img :src="movie.image" alt="Movie Poster" class="movie-card__image" />
          
          <!-- Phần thông tin episode -->
          <div class="episode">{{ movie.episode }}</div>
          
          <!-- Phần thông tin phim khác -->
          <div class="movie-info">
            <p class="title">{{ movie.title }}</p>
            <p class="year">{{ movie.year }}</p>
          </div>
        </div>
      </div>
      <button @click="scrollRight" class="arrow right-arrow">&gt;</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TrendingMovies',
  props: {
    title: {
      type: String,
      required: true,
    },
    movies: {
      type: Array,
      required: true,
    },
  },
  methods: {
    scrollLeft() {
      const container = this.$refs.moviesList;
      container.scrollLeft -= 300; // Di chuyển sang trái
    },
    scrollRight() {
      const container = this.$refs.moviesList;
      container.scrollLeft += 300; // Di chuyển sang phải
    },
  },
};
</script>

<style scoped>
.trending-movies-section {
  margin: 20px 0;
}

.trending-movies-container {
  display: flex;
  align-items: center;
}

.trending-movies-list {
  display: flex;
  overflow: hidden;
  padding: 10px 0;
  flex: 1;
}

.trending-movie-card {
  position: relative; /* Để phần "episode" và "movie-info" có thể đè lên ảnh */
  margin: 0 10px;
  flex: 0 0 auto;
  width: 150px;
  height: 250px;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  overflow: hidden;
}

.trending-movie-card:hover {
  transform: scale(1.1);
  box-shadow: 0px 8px 15px rgba(0, 0, 0, 0.3);
}

.trending-movie-card img {
  border-radius: 5px;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* Định dạng phần episode */
.episode {
  position: absolute;
  top: 5px;
  left: 1px;
  background-color: rgba(128, 128, 128, 0.8); /* Màu nền xám trong suốt */
  color: white;
  padding: 3px 6px;
  font-size: 0.8em;
  border-radius: 3px;
  z-index: 2; /* Đảm bảo hiển thị phía trên ảnh */
}

/* Định dạng phần thông tin phim */
.movie-info {
  position: absolute;
  bottom: 0; /* Đẩy movie info xuống đáy */
  left: 0;
  width: 100%;
  background: rgba(0, 0, 0, 0.6); /* Nền màu đen trong suốt */
  color: white;
  text-align: center;
  padding: 5px 0;
}

.movie-info .title {
  font-weight: bold;
  font-size: 1.0em;
}

.movie-info .year {
  font-size: 0.9em;
  color: #ccc;
}

.arrow {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 24px;
  color: white;
}

.left-arrow {
  margin-right: 10px;
}

.right-arrow {
  margin-left: 10px;
}
</style>
