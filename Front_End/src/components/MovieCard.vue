<template>
  <div class="movie-card" 
       @mouseover="hover = true" 
       @mouseleave="hover = false">
    <img :src="movie.image" :alt="movie.title" class="movie-card__image" />
    <div class="info">
      <span>{{ movie.episode }}</span>
      <h3>{{ movie.title }}</h3>
      <span>{{ movie.year }}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "MovieCard",
  props: {
    movie: {
      type: Object, // Chỉ nhận một đối tượng phim
      required: true,
    }
  },
  data() {
    return {
      hover: false, // Biến để điều khiển trạng thái hover
    };
  }
};
</script>

<style scoped>
.movie-card {
  background-color: #1e1e1e;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  transition: transform 0.3s ease;
  cursor: pointer;
  position: relative;
}

.movie-card:hover {
  transform: scale(1.05); /* Phóng to khi hover */
}

.movie-card__image {
  width: 100%;
  height: 252px;
  object-fit: cover;
}

.info {
  padding: 10px;
}

.info span {
  display: block;
  font-size: 14px;
  color: #ccc;
}

.info h3 {
  font-size: 16px;
  margin: 5px 0;
  color: #fff;
}
</style>
