<template>
  <div class="rank-container">
    <h2>Xếp Hạng Phim Hot</h2>
    <ul>
      <li v-for="(movie, index) in movies" :key="movie.id" class="rank-item">
        <span class="rank">{{ index + 1 }}</span> <!-- Số thứ tự xếp hạng -->
        <span class="title">{{ movie.title }}</span> <!-- Tên phim -->
        <span class="rating">{{ movie.rating }} ★</span> <!-- Đánh giá phim -->
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'RankComponent',
  data() {
    return {
      movies: [
        { id: 1, title: 'Phim A', rating: 9.1 },
        { id: 2, title: 'Phim B', rating: 8.8 },
        { id: 3, title: 'Phim C', rating: 8.7 },
        { id: 4, title: 'Phim D', rating: 8.5 },
        { id: 5, title: 'Phim E', rating: 8.4 }
      ]
    };
  }
};
</script>

<style scoped>
.rank-container {
  padding: 20px;
  background-color: #1c1c1c; /* Nền tối */
  color: #fff; /* Màu chữ trắng */
  border-radius: 8px;
}

.rank-container h2 {
  font-size: 1.5rem;
  margin-bottom: 15px;
  text-align: center;
}

ul {
  list-style-type: none; /* Xóa dấu đầu dòng */
  padding: 0;
}

.rank-item {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  border-bottom: 1px solid #444; /* Đường viền giữa các mục */
}

.rank {
  font-size: 1.2rem;
  font-weight: bold;
  color: #e74c3c; /* Màu đỏ cho thứ hạng */
}

.title {
  font-size: 1.1rem;
  flex: 1;
  padding-left: 10px;
}

.rating {
  font-size: 1rem;
  color: #f1c40f; /* Màu vàng cho đánh giá */
}
</style>
