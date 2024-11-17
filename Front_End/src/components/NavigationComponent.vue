<template>
  <nav class="navigation">
    <ul>
      <!-- Dropdown cho Thể Loại -->
      <li class="dropdown" @click="toggleDropdown('genres')">
        <a href="#">Thể Loại</a>
        <div :class="{'dropdown-menu': true, 'active': activeDropdown === 'genres'}">
          <div class="grid-container">
            <div v-for="genre in genres" :key="genre.id" class="item">
              <router-link :to="{ name: 'IdGenreMoviesPage', params: { genreId: genre.id } }" @click="handleGenreClick">{{ genre.genreName }}</router-link>
            </div>
          </div>
        </div>
      </li>
      
      <!-- Dropdown cho Quốc Gia -->
      <li class="dropdown" @click="toggleDropdown('countries')">
        <a href="#">Quốc Gia</a>
        <div :class="{'dropdown-menu': true, 'active': activeDropdown === 'countries'}">
          <div class="grid-container">
            <div v-for="country in countries" :key="country.id" class="item">
              <router-link :to="{ name: 'IdCountryMoviesPage', params: { countryId: country.id } }" @click="handleCountryClick">{{ country.countryName }}</router-link>
            </div>
          </div>
        </div>
      </li>
      
      <!-- Dropdown cho Năm phát hành -->
      <li class="dropdown" @click="toggleDropdown('releaseYear')">
        <a href="#">Năm phát hành</a>
        <div :class="{'dropdown-menu': true, 'active': activeDropdown === 'releaseYear'}">
          <div class="grid-container">
            <div v-for="releaseYear in releaseYears" :key="releaseYear.id" class="item">
              <router-link :to="{ name: 'IdReleaseYearMoviesPage', params: { releaseYearId: releaseYear.id } }" @click="handleReleaseYearClick">{{ releaseYear.year }}</router-link>
            </div>
          </div>
        </div>
      </li>
      <li><router-link to="/xep-hang">Xếp Hạng</router-link></li>
    </ul>

    <!-- Thanh tìm kiếm -->
    <div class="search-bar">
       <!-- Dropdown chọn loại tìm kiếm -->
      <select v-model="searchType" class="search-type">
        <option value="movie">Tìm theo Phim</option>
        <option value="actor">Tìm theo Diễn Viên</option>
      </select>
      <input 
        type="text" 
        v-model="searchQuery" 
        placeholder="Tìm kiếm theo tên hoặc ảnh..."
        @keyup.enter="handleSearch" 
      />
      <button @click="handleSearch">Tìm</button>
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
import axios from '@/axios';
export default {
  name: 'NavigationComponent',
  data() {
    return {
      releaseYears : [],
      genres: [],
      countries: [],
      activeDropdown: null, // Xác định dropdown nào đang mở
      searchQuery: '',
      searchImage: null,
      searchType: 'movie',
    };
  },

  mounted() {
    document.addEventListener('click', this.closeDropdownOnClickOutside);
  },
  beforeUnmount() {
    document.removeEventListener('click', this.closeDropdownOnClickOutside);
  },

  created() {
    this.fetchGenres();
    this.fetchCountries();
    this.fetchReleaseYears();
  },
  methods: {
    async fetchGenres() {
      try {
        const response = await axios.get(`http://localhost:8080/api/genres`);
        if (response.status === 200 && response.data.success) {
          this.genres = response.data.data;
        }
      } catch (error) {
        console.error('Lỗi khi lấy thông tin thể loại', error);
      }
    },
    async fetchCountries() {
      try {
        const response = await axios.get(`http://localhost:8080/api/countries`);
        if (response.status === 200 && response.data.success) {
          this.countries = response.data.data;
        }
      } catch (error) {
        console.error('Lỗi khi lấy thông tin quốc gia', error);
      }
    },
    async fetchReleaseYears() {
      try {
        const response = await axios.get(`http://localhost:8080/api/releaseyear`);
        if (response.status === 200 && response.data.success) {
          this.releaseYears = response.data.data;
        }
      } catch (error) {
        console.error('Lỗi khi lấy thông tin năm phát hành', error);
      }
    },
    toggleDropdown(menu) {
      this.activeDropdown = this.activeDropdown === menu ? null : menu;
    },
    closeDropdownOnClickOutside(event) {
      if (this.activeDropdown && !this.$el.contains(event.target)) {
        this.activeDropdown = null;
      }
    },
    handleGenreClick(event) {
      event.stopPropagation(); // Ngăn chặn việc đóng dropdown
      this.activeDropdown = null; // Có thể đóng dropdown sau khi chọn
    },
    handleCountryClick(event) {
      event.stopPropagation(); // Ngăn chặn việc đóng dropdown
      this.activeDropdown = null; // Có thể đóng dropdown sau khi chọn
    },
    handleReleaseYearClick(event) {
      event.stopPropagation(); // Ngăn chặn việc đóng dropdown
      this.activeDropdown = null; // Có thể đóng dropdown sau khi chọn
    },
    handleSearch() {
    if (!this.searchQuery.trim()) {
      alert('Vui lòng nhập từ khóa tìm kiếm');
      return;
    }
    // Chuyển hướng sang SearchResult với các tham số query
    this.$router.push({
      name: 'search-results',
      query: {
        type: this.searchType,
        q: this.searchQuery,
      },
    });
  },
    triggerFileInput() {
      this.$refs.fileInput.click();
    },
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

.dropdown {
  position: relative;
}

.dropdown-menu {
  display: none;
  position: absolute;
  background-color: #333;
  padding: 10px;
  border-radius: 8px;
  min-width: 700px;
  z-index: 1000;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  margin-top: 10px;
}

.dropdown-menu.active {
  display: block;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 10px;
  white-space: nowrap;
}

.item {
  background-color: #333;
  padding: 8px;
  border-radius: 5px;
}

.item a {
  color: white;
  text-decoration: none;
}

.item a:hover {
  color: #3498db;
  background-color: #555;
}
</style>
