<template>
  <nav class="navigation">
    <ul>
      <li><router-link to="/the-loai">Thể Loại</router-link></li>
      <li><router-link to="/quoc-gia">Quốc Gia</router-link></li>
      <li><router-link to="/nam-phat-hanh">Năm Phát Hành</router-link></li>
      <li><router-link to="/xep-hang">Xếp Hạng</router-link></li>
    </ul>

    <!-- Thanh tìm kiếm -->
    <div class="search-bar">
      <input 
        type="text" 
        v-model="searchQuery" 
        placeholder="Tìm kiếm theo tên hoặc ảnh..."
        @keyup.enter="handleSearch" 
      />
      <button @click="handleSearch">Tìm</button>

      <!-- Nút để chọn tệp ảnh -->
      <button @click="triggerFileInput">Tải ảnh</button>
      <input
        type="file"
        ref="fileInput" 
        @change="handleImageSearch" 
        accept="image/*" 
        style="display: none;" 
      />
    </div>
  </nav>
</template>

<script>
export default {
  name: 'NavigationComponent',
  data() {
    return {
      searchQuery: '', // Dữ liệu để tìm kiếm theo text
      searchImage: null // Dữ liệu để tìm kiếm theo ảnh
    };
  },
  methods: {
    // Hàm tìm kiếm dựa trên text
    handleSearch() {
      if (this.searchQuery.trim() !== '') {
        this.$router.push({ name: 'search-results', query: { q: this.searchQuery } });
      }
    },
    
    // Kích hoạt thẻ input file khi người dùng bấm nút "Tải ảnh"
    triggerFileInput() {
      this.$refs.fileInput.click();
    },

    // Hàm tìm kiếm dựa trên hình ảnh
    handleImageSearch(event) {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.searchImage = e.target.result;
          this.$router.push({ name: 'search-results', query: { img: this.searchImage } });
        };
        reader.readAsDataURL(file);
      }
    }
  }
};
</script>

<style scoped>
/* CSS như cũ */
.navigation {
  background-color: #111;
  padding: 10px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.navigation ul {
  list-style-type: none;
  display: flex;
  justify-content: space-between;
  margin-right: 20px;
}

.navigation li {
  margin: 0 15px;
}

.navigation a {
  color: white;
  text-decoration: none;
  font-weight: bold;
}

.navigation a:hover {
  color: #3498db;
}

/* CSS cho thanh tìm kiếm */
.search-bar {
  display: flex;
  align-items: center;
}

.search-bar input[type="text"] {
  padding: 5px;
  border: none;
  border-radius: 4px;
  margin-right: 10px;
}

.search-bar input[type="file"] {
  display: none; /* Ẩn phần input chọn file */
}

.search-bar button {
  background-color: #ffcc00;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
  border-radius: 4px;
  font-weight: bold;
}

.search-bar button:hover {
  background-color: #ff9900;
}

</style>
