<template>
  <div class="movie-card" 
       @click="goToMovieInfo"
       @mouseover="hover = true" 
       @mouseleave="hover = false">
    <img :src="movie.poster" :alt="movie.movieName" class="movie-card__image" />
    <div class="info">
      <span>{{ movie.ratingMean }}</span>
      <h3>{{ movie.movieName }}</h3>
    </div>
    <div v-if="hover && showDeleteButton" class="hover-info">
      <button class="delete-btn" @click.stop="handleDelete">Xóa</button>
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
    },
    showDeleteButton: {
      type: Boolean,
      default: false,  // Mặc định không hiển thị nút xóa
    }
  },
  emits: ['delete-movie'],
  methods: {
    goToMovieInfo() {
      this.$router.push({ 
        name: 'InfoMoviePage', 
        params: { id: this.movie.id },
      });
    },
    handleDelete() {
      this.$emit('delete-movie', this.movie.id); // Phát sự kiện xóa đến component cha
    },
  },
  data() {
    return {
      hover: false, // Theo dõi trạng thái hover
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
.delete-btn {
  background-color: #f44336;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 5px 10px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.delete-btn:hover {
  background-color: #d32f2f;
}
</style>
