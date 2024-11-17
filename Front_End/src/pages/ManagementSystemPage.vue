<template>
  <div class="management-system-page">
    <button class="toggle-sidebar-btn" @click="toggleSidebar">
     {{ isSidebarOpen ? '</>' : '</>' }}
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
        <PackageManagement />
      </div>
      <div v-else-if="activeMenuItem === 'users'">
        <!-- Nội dung quản lý người dùng -->
        <UserManagement />
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
                <button @click="openAddEpisodeForm(movie.id)">Thêm tập phim</button>
                <button @click="openAddActorForm(movie.id)">Thêm diễn viên</button>

                <!-- Form hiển thị URL dưới dạng box -->
                <div v-if="showEpisodeForm && currentEditingMovieId === movie.id" class="episode-modal">
                  <div class="episode-modal-content">
                    <button class="close-button" @click="closeEpisodeForm">X</button>
                    <div class="episode-group" v-for="episode in episodes" :key="episode.episodeNumber">
                      <label :for="'episodeUrl' + episode.episodeNumber">Tập {{ episode.episodeNumber }}</label>
                      <input 
                        v-model="episode.episodeUrl" 
                        :id="'episodeUrl' + episode.episodeNumber"  
                        :placeholder="'URL tập ' + episode.episodeNumber"
                        type="url"
                        @invalid="alert('Vui lòng nhập URL hợp lệ!')"
                      />
                      <button 
                        class="save-button" 
                        @click="handleEpisodeUrlChange(episode.id, episode.episodeNumber, episode.episodeUrl, movie.id)"
                  
                      >
                        Lưu
                      </button>
                    </div>
                  </div>
                </div>
                <div v-if="showAddActorModal" class="modal">
                  <div class="modal-content">
                    <h2>Quản lý diễn viên</h2>
                    <!-- Danh sách diễn viên hiện tại -->
                    <div class="existing-actors">
                      <h3>Danh sách diễn viên</h3>
                      <ul>
                        <li v-for="actor in existingActors" :key="actor.id">
                          {{ actor.nameActor }}
                        </li>
                      </ul>
                    </div>

                    <!-- Thêm diễn viên mới -->
                    <div class="add-new-actors">
                      <h3>Thêm diễn viên mới</h3>
                      <div v-for="(actor, index) in newActors" :key="index" class="new-actor">
                        <input 
                          type="text" 
                          v-model="newActors[index].nameActor" 
                          placeholder="Nhập tên diễn viên"
                        />
                        <button @click="removeActor(index)">Xóa</button>
                      </div>
                      <button @click="addNewActor">+</button>
                    </div>

                    <!-- Nút lưu -->
                    <button @click="saveActors" class="save">Lưu</button>
                    <button @click="closeAddActorForm" class="close">Đóng</button>
                  </div>
                </div>
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
        <ActorManagement />
        <!-- Nội dung quản lý diễn viên -->
      </div>
    </div>
  </div>
</template>

<script>
import axios from '@/axios';
import UserManagement from '../components/UserManagement.vue';
import ActorManagement from "../components/ActorManagement.vue";
import PackageManagement from "../components/PackageManagement.vue";

export default {
  name: 'ManagementSystemPage',
  components: {
    UserManagement,
    ActorManagement,
    PackageManagement,
  },
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

      episodes: [], // Danh sách các tập phim
      movie: {}, // Dữ liệu bộ phim, chứa thông tin về tổng số tập phim
      showEpisodeForm: false,
      currentEditingMovieId: null,

      showAddActorModal: false,
      movieId: null,
      existingActors: [],
      newActors: [{ nameActor: '' }], // Khởi tạo với 1 dòng trống
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


  async openAddEpisodeForm(imdb_id) {
    try {
      // Lấy thông tin về bộ phim và các tập phim đã có URL
      const movieResponse = await axios.get(`http://localhost:8080/movies/${imdb_id}`);
      const totalEpisodes = movieResponse.data.data.totalEpisodes; // Số tập phim tổng cộng
      const episodesResponse = await axios.get(`http://localhost:8080/episodes/movie/${imdb_id}`);
      const episodesWithUrl = episodesResponse.data.data;

      // Tạo mảng tất cả các tập phim, bao gồm cả các tập chưa có URL
      this.episodes = [];
      for (let i = 1; i <= totalEpisodes; i++) {
        const existingEpisode = episodesWithUrl.find(episode => episode.episodeNumber === i);
        this.episodes.push({
          id: existingEpisode ? existingEpisode.id : null,
          episodeNumber: i,
          episodeUrl: existingEpisode ? existingEpisode.episodeUrl : '',
          movieId: imdb_id
        });
      }
      this.showEpisodeForm = true;
      this.currentEditingMovieId = imdb_id;
    } catch (error) {
        console.error("Error fetching data:", error);
    }
   
  },
  async handleEpisodeUrlChange(episodeId, episodeNumber, newUrl, movieId) {
    const existingEpisode = this.episodes.find(episode => episode.episodeNumber === episodeNumber);
    
    if (existingEpisode && existingEpisode.id && this.checkEpisodeURL(existingEpisode.episodeUrl)) {
      await this.updateEpisodeUrl(episodeId, newUrl);
    } else {
      await this.addEpisodeUrl(movieId, episodeNumber, newUrl);
    }
  },
  async updateEpisodeUrl(episodeId, newUrl) {
    try {
      const response = await axios.put(`http://localhost:8080/episodes/update-url/${episodeId}`, {
        episodeUrl: newUrl
      });

      if (response.data.success) {
        alert(response.data.desc);
        this.openAddEpisodeForm();
      } 
    } catch (error) {
      console.error('Error updating episode URL:', error);
    }
  },
  async addEpisodeUrl(movieId, episodeNumber, episodeUrl) {
    try {
      const response = await axios.post('http://localhost:8080/episodes/add', {
        episodeNumber: episodeNumber,
        episodeUrl: episodeUrl,
        movieId: movieId
      });

      if (response.data.success) {
        alert(response.data.desc);
        this.openAddEpisodeForm();
      }
      else{
        alert(response.data.desc);
      }
    } catch (error) {
      console.error('Error adding episode URL:', error);
    }
  },
  closeEpisodeForm() {
    this.showEpisodeForm = false;
    this.currentEditingMovieId = null;
  },
  checkEpisodeURL(episodeUrl){
     if (!episodeUrl || episodeUrl.trim() === '') {
      // Nếu URL trống, hiển thị thông báo lỗi cho tập phim này
      alert('Vui lòng nhập URL!');
      return false;
    } else {
      // Kiểm tra định dạng của URL
      const urlPattern = /^(https?|ftp):\/\/[^\s/$.?#].[^\s]*$/i;
      if (!urlPattern.test(episodeUrl)) {
        alert('Nhập đúng định dạng URL!');
        return false;
      }
    }
    return true;

  },

  // Mở modal thêm diễn viên
    openAddActorForm(movieId) {
      this.movieId = movieId;
      this.showAddActorModal = true;
      this.fetchExistingActors(movieId);
    },

    // Đóng modal
    closeAddActorForm() {
      this.showAddActorModal = false;
      this.existingActors = [];
      this.newActors = [{ nameActor: '' }];
    },

    // Lấy danh sách diễn viên hiện tại
    async fetchExistingActors(movieId) {
      try {
        const response = await axios.get(`http://localhost:8080/movies/${movieId}/actors`);
        if (response.data.success) {
          this.existingActors = response.data.data;
        } else {
          alert(response.data.desc || 'Không thể lấy danh sách diễn viên');
        }
      } catch (error) {
        console.error('Lỗi khi lấy danh sách diễn viên:', error);
      }
    },

    // Thêm một dòng để nhập diễn viên mới
    addNewActor() {
      this.newActors.push({ nameActor: '' });
    },

    // Xóa một dòng diễn viên mới
    removeActor(index) {
      this.newActors.splice(index, 1);
    },

    // Lưu danh sách diễn viên mới
    async saveActors() {
      try {
        const response = await axios.put(
          `http://localhost:8080/actors/update-actors/${this.movieId}`,
          this.newActors.filter(actor => actor.nameActor.trim()) // Loại bỏ các dòng trống
        );
        if (response.data.success) {
          alert(response.data.desc || 'Lưu thành công');
          this.existingActors = response.data.data;
          this.closeAddActorForm();
        } else {
          alert(response.data.desc || 'Không thể lưu danh sách diễn viên');
        }
      } catch (error) {
        console.error('Lỗi khi lưu danh sách diễn viên:', error);
      }
    },

  },
};
</script>

<style scoped>
.management-system-page {
  display: flex;
  position: relative; 
}

.toggle-sidebar-btn {
  position: absolute;
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

/* Modal Container */
.episode-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7); /* Làm nền tối hơn để nổi bật box */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

/* Modal Content Box */
.episode-modal-content {
  background-color: #ffffff;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
  width: 500px;
  max-height: 80%;
  overflow-y: auto;
  position: relative;
  text-align: center;
}

.close-button { 
  position: absolute;
  top: 10px;
  right: 10px;
  background: transparent;
  border: none;
  font-size: 24px;
  color: black;  /* Màu mặc định là đen */
  cursor: pointer;
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: none;
}

.close-button:hover {
  color: #ff4d4d;  /* Chuyển màu sang đỏ khi hover */
  transform: scale(1.1);
}


/* Header of Modal */
.episode-modal-content h2 {
  font-size: 24px;
  color: #333;
  margin-bottom: 20px;
}

/* Input Group */
.episode-modal-content .episode-group {
  margin-bottom: 20px; /* Khoảng cách giữa các tập */
  text-align: left;    /* Căn trái nhãn và input */
}

/* Labels */
.episode-modal-content label {
  display: block;
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 8px;
  color: #555;
}

/* Inputs */
.episode-modal-content input {
  width: 100%;
  padding: 10px 15px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 6px;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);
}

.episode-modal-content input:focus {
  border-color: #007bff;
  outline: none;
  box-shadow: 0px 0px 8px rgba(0, 123, 255, 0.5);
}

.save-button {
  background-color: #007bff;
  color: #ffffff;
  border: none;
  padding: 8px 12px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
  transition: background-color 0.2s ease;
}

.save-button:hover {
  background-color: #0056b3;
  transform: scale(1.05);
}

/* Modal tổng */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
  color:#333;
}

/* Nội dung modal */
.modal-content {
  background-color: white;
  border-radius: 10px;
  width: 90%;
  max-width: 600px;
  padding: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.25);
  animation: fadeIn 0.3s ease-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: scale(0.9);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

/* Tiêu đề */
.modal-content h2 {
  font-size: 24px;
  margin-bottom: 20px;
  text-align: center;
  color: #333;
}

/* Danh sách diễn viên hiện tại */
.existing-actors h3 {
  font-size: 18px;
  margin-bottom: 10px;
  color: #444;
}

.existing-actors ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.existing-actors li {
  padding: 8px;
  border-bottom: 1px solid #ddd;
}

.existing-actors li:last-child {
  border-bottom: none;
}

/* Thêm diễn viên mới */
.add-new-actors h3 {
  font-size: 18px;
  margin: 20px 0 10px;
  color: #444;
}

.new-actor {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.new-actor input {
  flex: 1;
  padding: 8px 12px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-right: 10px;
}

/* Nút hành động tổng quát */
.modal-content button {
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  font-size: 14px;
  margin: 10px 5px 0;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

/* Nút Lưu */
.modal-content button.save {
  background-color: #28a745; /* Xanh lá */
  color: white;
}

.modal-content button.save:hover {
  background-color: #218838; /* Xanh lá đậm hơn */
}

/* Nút Đóng */
.modal-content button.close {
  background-color: #6c757d; /* Xám */
  color: white;
}

.modal-content button.close:hover {
  background-color: #5a6268; /* Xám đậm hơn */
}

/* Nút Thêm (+) */
.add-new-actors button {
  background-color: #007bff; /* Xanh dương */
  color: white;
  padding: 8px 12px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.add-new-actors button:hover {
  background-color: #0056b3; /* Xanh dương đậm hơn */
}

/* Nút Xóa */
.new-actor button {
  background-color: #dc3545; /* Đỏ */
  color: white;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.new-actor button:hover {
  background-color: #c82333; /* Đỏ đậm hơn */
}



/* Đảm bảo form có responsive */
@media (max-width: 768px) {
  .add-movie-form {
    width: 90%;
  }
}

</style>
