<template>
  <div>
    <h1>Kết quả tìm kiếm</h1>

    <!-- Kiểm tra xem người dùng đang tìm kiếm theo văn bản hay hình ảnh -->
    <div v-if="searchQuery">
      <p>Kết quả tìm kiếm cho: "{{ searchQuery }}"</p>
      <!-- Logic hiển thị kết quả tìm kiếm theo văn bản -->
      <ul>
        <li v-for="(result, index) in searchResults" :key="index">
          {{ result }}
        </li>
      </ul>
    </div>

    <div v-if="searchImage">
      <p>Tìm kiếm dựa trên hình ảnh</p>
      <img :src="searchImage" alt="Tìm kiếm theo hình ảnh" />
      <!-- Logic xử lý kết quả tìm kiếm theo hình ảnh -->
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchQuery: '',
      searchImage: '',
      searchResults: [] // Mảng chứa kết quả tìm kiếm theo văn bản
    };
  },
  created() {
    // Lấy các tham số từ query URL
    this.searchQuery = this.$route.query.q || '';
    this.searchImage = this.$route.query.img || '';

    if (this.searchQuery) {
      // Gọi API hoặc thực hiện tìm kiếm dựa trên searchQuery
      this.performTextSearch();
    }

    if (this.searchImage) {
      // Gọi API hoặc thực hiện tìm kiếm dựa trên hình ảnh
      this.performImageSearch();
    }
  },
  methods: {
    performTextSearch() {
      // Giả sử bạn có một API để tìm kiếm hoặc logic lọc cục bộ
      // Ví dụ tìm kiếm trong mảng tĩnh:
      const allMovies = ['Inception', 'Matrix', 'Avengers']; // Dữ liệu mẫu
      this.searchResults = allMovies.filter(movie =>
        movie.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
    performImageSearch() {
      // Xử lý tìm kiếm dựa trên hình ảnh
      // Có thể sử dụng API hoặc thực hiện logic tìm kiếm bằng hình ảnh
      console.log('Tìm kiếm hình ảnh:', this.searchImage);
    }
  }
};
</script>
