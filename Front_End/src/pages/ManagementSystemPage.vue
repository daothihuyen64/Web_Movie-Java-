<template>
  <div class="management-system-page">
    <button class="toggle-sidebar-btn" @click="toggleSidebar">
     {{ isSidebarOpen ? '<>' : '<>' }}
    </button>
    <!-- Sidebar Navigation -->
    <div class="sidebar" v-if="isSidebarOpen">
      <ul>
        <li 
          v-for="(item, index) in menuItems" 
          :key="index" 
          :class="{ active: activeMenuItem === item.name }"
          @click="selectMenuItem(item.name)"
        >
          {{ item.label }}
        </li>
      </ul>
    </div>

    <!-- Main Content Area -->
    <div class="content">
      <div v-if="activeMenuItem === 'packages'">
        <h2>Quản lý Gói dịch vụ</h2>
        <!-- Nội dung quản lý gói dịch vụ -->
      </div>
      <div v-else-if="activeMenuItem === 'users'">
        <h2>Quản lý Người dùng</h2>
        <!-- Nội dung quản lý người dùng -->
      </div>
      <div v-else-if="activeMenuItem === 'movies'">
        <h2>Quản lý Phim</h2>
        <!-- Nội dung quản lý phim -->
        <div class="movies-page">
          <!-- Bộ lọc -->
          <div class="filters">
            <select v-model="selectedCountry"  @change="applyFilters">
              <option value="">Nước</option>
              <option v-for="country in countries" :key="country" :value="country">
                {{ country.countryName }}
              </option>
            </select>

            <select v-model="selectedYear"  @change="applyFilters">
              <option value="">Năm phát hành</option>
              <option v-for="year in releaseYears" :key="year" :value="year">{{ year.year }}</option>
            </select>

            <select v-model="selectedGenre"  @change="applyFilters">
              <option value="">Thể loại</option>
              <option v-for="genre in genres" :key="genre" :value="genre">
                {{ genre.genreName }}
              </option>
            </select>
          </div>
          <!-- Nút Thêm Phim Mới -->
          <button @click="openAddMovieForm">Thêm Phim Mới</button>

          <!-- Form Thêm Phim Mới -->
          <div v-if="isAddMovieFormVisible" class="add-movie-form">
            <h3>Thêm Phim Mới</h3>
            <form @submit.prevent="addMovie">
              <label for="movieName">Tên Phim:</label>
              <input v-model="newMovie.movieName" type="text" id="movieName" required />

              <label for="poster">Poster:</label>
              <input v-model="newMovie.poster" type="url" id="poster" required />

              <label for="trailer">Trailer:</label>
              <input v-model="newMovie.trailer" type="url" id="trailer" required />

              <label for="description">Mô Tả:</label>
              <textarea v-model="newMovie.description" id="description" required></textarea>

              <label for="director">Đạo Diễn:</label>
              <input v-model="newMovie.director" type="text" id="director" required />

              <label for="totalEpisodes">Tổng Số Tập:</label>
              <input v-model="newMovie.totalEpisodes" type="number" id="totalEpisodes" required />

              <label for="status">Trạng Thái:</label>
              <select v-model="newMovie.status" id="status" required>
                <option value="1">Đang chiếu</option>
                <option value="0">Ngừng chiếu</option>
              </select>

              <label for="Country">Quốc Gia:</label>
              <select v-model="newMovie.country" id="=Country" required>
                <option v-for="country in countries" :key="country.id" :value="country.id">
                  {{ country.countryName }}
                </option>
              </select>

              <label for="ReleaseYear">Năm Phát Hành:</label>
              <select v-model="newMovie.release_year" id="ReleaseYear" required>
                <option v-for="year in releaseYears" :key="year.id" :value="year.id">
                  {{ year.year }}
                </option>
              </select>

              <label for="Genre">Thể Loại:</label>
              <select v-model="newMovie.genre" id="Genre" required>
                <option v-for="genre in genres" :key="genre.id" :value="genre.id">
                  {{ genre.genreName }}
                </option>
              </select>

              <div class="button-group">
                <button type="submit">Thêm Phim</button>
                <button type="button" @click="closeAddMovieForm">Quay Lại</button>
              </div>
            </form>
          </div>

          <!-- Danh sách phim -->
          <div v-if="!isAddMovieFormVisible  && !isEditMovieFormVisible" class="movies-list">
            <div class="movie-item" v-for="movie in filteredMovies" :key="movie.id">
              <img :src="movie.poster" alt="Poster" class="movie-poster" />
              <div class="movie-info">
                <h3 class="movie-name">{{ movie.movieName }}</h3>
                <p class="movie-status">Trạng thái: {{ movie.status === 1 ? 'Đang chiếu' : 'Ngừng chiếu' }}</p>
                <!-- Nút chỉnh sửa phim -->
                <button @click="openEditMovieForm(movie.id)">Chỉnh sửa</button>
              </div>
            </div>
          </div>
          <!-- Form chỉnh sửa phim -->
          <div v-if="isEditMovieFormVisible" class="edit-movie-form">
            <h3>Chỉnh sửa thông tin phim</h3>
            <form @submit.prevent="updateMovie">

              <label for="editMovieNamw">MovieName:</label>
              <input v-model="currentMovie.movieName" type="text" id="editMovieName" required />

              <label for="editPoster">Poster:</label>
              <input v-model="currentMovie.poster" type="url" id="editPoster" required />

              <label for="editTrailer">Trailer:</label>
              <input v-model="currentMovie.trailer" type="url" id="editTrailer" required />

              <label for="editDescription">Mô Tả:</label>
              <textarea v-model="currentMovie.description" id="editDescription" required></textarea>

              <label for="editDirector">Đạo Diễn:</label>
              <input v-model="currentMovie.director" type="text" id="editDirector" required />

              <label for="editTotalEpisodes">Tổng Số Tập:</label>
              <input v-model="currentMovie.totalEpisodes" type="number" id="editTotalEpisodes" required />

              <label for="editStatus">Trạng Thái:</label>
              <select v-model="currentMovie.status" id="editStatus" required>
                <option value="1">Đang chiếu</option>
                <option value="0">Ngừng chiếu</option>
              </select>

              <label for="editCountry">Quốc Gia:</label>
              <select v-model="currentMovie.country.id" id="editCountry" required>
                <option v-for="country in countries" :key="country.id" :value="country.id">
                  {{ country.countryName }}
                </option>
              </select>

              <label for="editReleaseYear">Năm Phát Hành:</label>
              <select v-model="currentMovie.release_year.id" id="editReleaseYear" required>
                <option v-for="year in releaseYears" :key="year.id" :value="year.id">
                  {{ year.year }}
                </option>
              </select>

              <label for="editGenre">Thể Loại:</label>
              <select v-model="currentMovie.genre.id" id="editGenre" required>
                <option v-for="genre in genres" :key="genre.id" :value="genre.id">
                  {{ genre.genreName }}
                </option>
              </select>

              <div class="button-group">
                <button type="submit">Cập Nhật</button>
                <button type="button" @click="closeEditMovieForm">Hủy</button>
              </div>
            </form>
          </div>
        </div>
      </div>
      <div v-else-if="activeMenuItem === 'actors'">
        <h2>Quản lý Diễn viên</h2>
        <!-- Nội dung quản lý diễn viên -->
      </div>
    </div>
  </div>
</template>

<script>
import axios from '@/axios';
export default {
  name: 'ManagementSystemPage',
  data() {
    return {
      // Các mục trong menu
      menuItems: [
        { name: 'packages', label: 'Gói dịch vụ' },
        { name: 'users', label: 'Người dùng' },
        { name: 'movies', label: 'Phim' },
        { name: 'actors', label: 'Diễn viên' },
      ],
      // Mục đang được chọn
      activeMenuItem: 'packages',
      isSidebarOpen: true,
      // Dữ liệu từ API
      allMovies: [],

      // Danh sách sau khi lọc
      filteredMovies: [],

      // Dữ liệu bộ lọc
      countries: [],
      releaseYears: [],
      genres: [],

      // Giá trị bộ lọc
      selectedCountry: "",
      selectedYear: "",
      selectedGenre: "",
      // Các giá trị để thêm phim mới
      newMovie: {
        movieName: "",
        poster: "",
        trailer: "",
        description: "",
        director: "",
        totalEpisodes: 0,
        status: 1,
        country: { id: 0 },
        release_year: { id: 0 },
        genre: { id: 0 }
      },
      isAddMovieFormVisible: false, // Hiển thị form thêm phim mới
      isEditMovieFormVisible: false,
      currentMovie: null,
    };
  },
  async created() {

  },

  methods: {
    async selectMenuItem(item) {
      this.activeMenuItem = item; // Cập nhật mục đang chọn
      // Gọi hàm reset tương ứng với item
      if (item === 'movies') {
        await this.resetMenuItemMovie();
      } else if (item === 'users') {
        this.resetMenuItemUser();
      } else if (item === 'packages') {
        this.resetMenuItemPackage();
      } else if (item === 'actors') {
        this.resetMenuItemActor();
      }
    },
    toggleSidebar() {
      this.isSidebarOpen = !this.isSidebarOpen; // Đóng/mở sidebar
    },
    async fetchMovies() {
      try {
        const response = await axios.get('http://localhost:8080/movies/all');
        if (response.status === 200 && response.data.success) {
          this.allMovies = response.data.data; // Lưu danh sách phim

          // Gán danh sách mặc định
          this.filteredMovies = this.allMovies;
        }
      } catch (error) {
        console.error('Lỗi khi gọi API lấy danh sách phim:', error);
      }
    },
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
    applyFilters() {
      this.filteredMovies = this.allMovies.filter((movie) => {
        return (
          (!this.selectedCountry || movie.country.countryName === this.selectedCountry.countryName) &&
          (!this.selectedYear || movie.release_year.year === Number(this.selectedYear.year)) &&
          (!this.selectedGenre || movie.genre.genreName === this.selectedGenre.genreName)
        );
      });
    },
    openAddMovieForm() {
      this.isAddMovieFormVisible = true;
    },
    async addMovie() {
      try {
        const response = await axios.post('http://localhost:8080/movies/add', {
          movieName: this.newMovie.movieName,
          poster: this.newMovie.poster,
          trailer: this.newMovie.trailer,
          description: this.newMovie.description,
          director: this.newMovie.director,
          totalEpisodes: this.newMovie.totalEpisodes,
          status: this.newMovie.status,
          country: { id: this.newMovie.country },
          release_year: { id: this.newMovie.release_year },
          genre: { id: this.newMovie.genre }
        });

        if (response.status === 200 && response.data.success) {
          alert('Thêm phim thành công!');
          await this.fetchMovies();
          this.applyFilters();
          this.isAddMovieFormVisible = false; // Ẩn form sau khi thêm phim
          this.resetNewMovieForm();
        } else {
          alert('Có lỗi xảy ra khi thêm phim!');
        }
      } catch (error) {
        console.error('Lỗi khi gọi API thêm phim:', error);
        alert('Có lỗi xảy ra, vui lòng thử lại!');
      }
    },
    async resetMenuItemMovie() {
      this.selectedCountry="";
      this.selectedYear="";
      this.selectedGenre="";
      this.isAddMovieFormVisible = false; // Hiển thị form thêm phim mới
      this.isEditMovieFormVisible = false;
      this.currentMovie = null;
      this.filteredMovies = [];
      await Promise.all([
        this.fetchMovies(),
        this.fetchGenres(),
        this.fetchCountries(),
        this.fetchReleaseYears()
      ]);
    
    },
    resetMenuItemUser() {
      // Reset dữ liệu cho menu quản lý người dùng
      console.log('Reset thông tin cho người dùng');
    },

    resetMenuItemPackage() {
      // Reset dữ liệu cho menu quản lý gói dịch vụ
      console.log('Reset thông tin cho gói dịch vụ');
    },

    resetMenuItemActor() {
      // Reset dữ liệu cho menu quản lý diễn viên
      console.log('Reset thông tin cho diễn viên');
    },
    resetNewMovieForm() {
      this.newMovie = {
        movieName: "",
        poster: "",
        trailer: "",
        description: "",
        director: "",
        totalEpisodes: 0,
        status: 1,
        country: { id: 0 },
        release_year: { id: 0 },
        genre: { id: 0 }
      };
    },
    async openEditMovieForm(imdb_id) {
      try {
        const response = await axios.get(`http://localhost:8080/movies/${imdb_id}`);
        if (response.status === 200 && response.data.success) {
          this.currentMovie = response.data.data;
          this.isEditMovieFormVisible = true;
        } else {
          alert('Không lấy được thông tin phim!');
        }
      } catch (error) {
        console.error('Lỗi khi lấy thông tin phim:', error);
      }
    },
    async updateMovie() {
      if (!this.currentMovie) return;

      try {
        const response = await axios.put(
          `http://localhost:8080/movies/update/${this.currentMovie.id}`,
          {
            movieName : this.currentMovie.movieName,
            poster: this.currentMovie.poster,
            trailer: this.currentMovie.trailer,
            description: this.currentMovie.description,
            director: this.currentMovie.director,
            totalEpisodes: this.currentMovie.totalEpisodes,
            status: this.currentMovie.status,
            country: { id: this.currentMovie.country.id },
            release_year: { id: this.currentMovie.release_year.id },
            genre: { id: this.currentMovie.genre.id },
          }
        );

        if (response.status === 200 && response.data.success) {
          alert('Cập nhật phim thành công!');
          this.isEditMovieFormVisible = false; // Ẩn form sau khi cập nhật
          await this.fetchMovies(); // Tải lại danh sách phim
          this.applyFilters();
        } else {
          alert('Cập nhật phim thất bại!');
        }
      } catch (error) {
        console.error('Lỗi khi cập nhật phim:', error);
        alert('Có lỗi xảy ra, vui lòng thử lại!');
      }
    },
    closeEditMovieForm() {
      this.isEditMovieFormVisible = false; // Ẩn form chỉnh sửa
      this.currentMovie = null; // Reset dữ liệu phim
    },
    closeAddMovieForm() {
      this.isAddMovieFormVisible = false;
      this.resetNewMovieForm();
    },

  },
};
</script>

<style scoped>
.management-system-page {
  display: flex;
}

.toggle-sidebar-btn {
  position: fixed;
  z-index: 1000;
  padding: 4px 10px;
  font-size: 15px; 
  background-color: #808080;
  color: white;
  border: none;
  border-radius: 3px;
  cursor: pointer;
}

.sidebar {
  width: 200px;
  background-color: #808080;
  padding: 10px;
  border-right: 1px solid  #808080;
}

.sidebar ul {
  list-style-type: none;
  padding: 0;
}

.sidebar li {
  padding: 10px;
  font-size: 16px;
  color: #333;
  cursor: pointer;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.sidebar li.active {
  color: #e9ecef;
  font-weight: bold;
  border-left: 3px solid #e9ecef;
  padding-left: 7px;
}

.sidebar li:hover {
  background-color: #e9ecef;
}

.content {
  flex: 1;
  padding: 20px;
}

.content-full {
  margin-left: 0; /* Khi sidebar bị đóng, phần nội dung sẽ tràn ra hết chiều rộng */
}

.movies-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 20px;
  background-color: #333;
}

.movie-item {
  display: flex;
  align-items: center;
  gap: 20px;
  background-color: #444;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.movie-poster {
  width: 100px;
  height: 150px;
  object-fit: cover;
  border-radius: 5px;
}

.movie-info {
  color: #fff;
}

.movie-name {
  margin: 0;
  font-size: 18px;
  font-weight: bold;
}

.movie-status {
  margin: 5px 0 0;
  font-size: 14px;
  color: #ccc;
}

/* Container cho toàn bộ form thêm phim */
.add-movie-form {
  width: 60%;
  margin: 20px auto;
  padding: 20px;
  background-color: #f4f4f4;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

/* Tiêu đề của form */
.add-movie-form h3 {
  text-align: center;
  font-size: 1.5em;
  color: #333;
  margin-bottom: 20px;
}

/* Các label cho các trường nhập liệu */
.add-movie-form label {
  display: block;
  font-size: 1em;
  color: #555;
  margin-bottom: 5px;
  font-weight: bold;
}

/* Các trường nhập liệu (input, select, textarea) */
.add-movie-form input,
.add-movie-form select,
.add-movie-form textarea {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1em;
  box-sizing: border-box;
}

/* Các input khi có lỗi */
.add-movie-form input:invalid,
.add-movie-form select:invalid,
.add-movie-form textarea:invalid {
  border-color: red;
}

/* Nút submit */
.button-group {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.button-group button {
  padding: 10px 20px;
  font-size: 1em;
  cursor: pointer;
  border: none;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.button-group button[type="submit"] {
  background-color: #4caf50;
  color: white;
}

.button-group button[type="submit"]:hover {
  background-color: #45a049;
}

.button-group button[type="button"] {
  background-color: #f44336;
  color: white;
}

.button-group button[type="button"]:hover {
  background-color: #d32f2f;
}
.edit-movie-form {
  max-width: 600px;
  margin: 0 auto; /* Center the form */
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.edit-movie-form h3 {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}

.edit-movie-form label {
  display: block;
  font-weight: bold;
  color: #555;
  margin-bottom: 8px;
}

.edit-movie-form input,
.edit-movie-form select,
.edit-movie-form textarea {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
  color: #333;
}

.edit-movie-form textarea {
  resize: vertical; /* Allow vertical resizing */
}

.edit-movie-form .button-group {
  display: flex;
  justify-content: space-between;
}

.edit-movie-form button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.edit-movie-form button[type="submit"] {
  background-color: #4caf50;
  color: white;
  transition: background-color 0.3s ease;
}

.edit-movie-form button[type="submit"]:hover {
  background-color: #45a049;
}

.edit-movie-form button[type="button"] {
  background-color: #f44336;
  color: white;
  transition: background-color 0.3s ease;
}

.edit-movie-form button[type="button"]:hover {
  background-color: #e53935;
}
/* Đảm bảo form có responsive */
@media (max-width: 768px) {
  .add-movie-form {
    width: 90%;
  }
}

</style>
