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
    <div v-if="hover" class="hover-info">
      <!-- Bạn có thể thêm thông tin hiển thị khi hover ở đây -->
    </div>
  </div>
</template>

<script>
import { useRouter } from 'vue-router'; // Sử dụng vue-router để điều hướng
import { ref } from 'vue'; // Nhập ref để theo dõi trạng thái

export default {
  name: "MovieCard",
  props: {
    movie: {
      type: Object, // Chỉ nhận một đối tượng phim
      required: true,
    }
  },
  setup(props) {
    const router = useRouter();
    const hover = ref(false); // Sử dụng ref để theo dõi trạng thái hover

    const goToMovieInfo = () => {
      // Chỉ điều hướng đến trang InfoMoviePage mà không gọi API
      router.push({ 
        name: 'InfoMoviePage', // Đặt tên route tương ứng
        params: { id: props.movie.id } // Truyền ID phim làm tham số
      });
    };

    return {
      hover,
      goToMovieInfo,
    };
  },
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
