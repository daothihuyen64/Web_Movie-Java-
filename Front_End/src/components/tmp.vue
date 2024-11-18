<template>
  <nav class="navigation">
    <ul>
      <li v-for="menu in dropdownMenus" :key="menu.key" class="dropdown" @click="toggleDropdown(menu.key)">
        <a href="#">{{ menu.label }}</a>
        <div :class="{'dropdown-menu': true, 'active': activeDropdown === menu.key}">
          <div class="grid-container">
            <div v-for="item in menu.data" :key="item.id" class="item">
              <router-link :to="{ name: menu.routeName, params: { [menu.paramKey]: item.id } }" @click="handleDropdownClick">{{ item[menu.displayKey] }}</router-link>
            </div>
          </div>
        </div>
      </li>
      <li><router-link to="/xep-hang">Xếp Hạng</router-link></li>
    </ul>

    <div class="search-bar">
      <input 
        type="text" 
        v-model="searchQuery" 
        placeholder="Nhập từ khóa..." 
        @keyup.enter="handleSearch"
        class="search-input"
      />
      <div class="search-dropdown">
        <button @click="toggleSearchDropdown" class="search-button">
          {{ searchType === 'movie' ? 'Tìm' : 'Tìm' }}
          <span class="dropdown-icon">▼</span>
        </button>
        <div v-if="showSearchDropdown" class="search-options">
          <div v-for="option in searchOptions" :key="option" @click="selectSearchOption(option)" class="search-option">
            Tìm theo {{ option === 'movie' ? 'Phim' : 'Diễn Viên' }}
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script>
import axios from '@/axios';
export default {
  name: 'NavigationComponent',
  data() {
    return {
      genres: [],
      countries: [],
      releaseYears: [],
      showSearchDropdown: false,
      searchQuery: '',
      searchType: 'movie',
      activeDropdown: null,
      dropdownMenus: [
        { key: 'genres', label: 'Thể Loại', data: [], routeName: 'IdGenreMoviesPage', paramKey: 'genreId', displayKey: 'genreName' },
        { key: 'countries', label: 'Quốc Gia', data: [], routeName: 'IdCountryMoviesPage', paramKey: 'countryId', displayKey: 'countryName' },
        { key: 'releaseYear', label: 'Năm phát hành', data: [], routeName: 'IdReleaseYearMoviesPage', paramKey: 'releaseYearId', displayKey: 'year' }
      ],
      searchOptions: ['movie', 'actor']
    };
  },
  created() {
    this.fetchDropdownData();
  },
  methods: {
    async fetchDropdownData() {
      try {
        const [genres, countries, releaseYears] = await Promise.all([
          axios.get('http://localhost:8080/api/genres'),
          axios.get('http://localhost:8080/api/countries'),
          axios.get('http://localhost:8080/api/releaseyear')
        ]);
        this.dropdownMenus[0].data = genres.data.data || [];
        this.dropdownMenus[1].data = countries.data.data || [];
        this.dropdownMenus[2].data = releaseYears.data.data || [];
      } catch (error) {
        console.error('Lỗi khi lấy thông tin dropdown', error);
      }
    },
    toggleDropdown(menu) {
      this.activeDropdown = this.activeDropdown === menu ? null : menu;
    },
    handleDropdownClick(event) {
      event.stopPropagation();
      this.activeDropdown = null;
    },
    toggleSearchDropdown() {
      this.showSearchDropdown = !this.showSearchDropdown;
    },
    selectSearchOption(option) {
      this.searchType = option;
      this.showSearchDropdown = false;
      this.handleSearch();
    },
    handleSearch() {
      if (!this.searchQuery.trim()) {
        alert('Vui lòng nhập từ khóa tìm kiếm');
        return;
      }
      this.$router.push({ name: 'search-results', query: { type: this.searchType, q: this.searchQuery } });
      this.searchQuery = '';
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
.search-bar {
  display: flex;
  align-items: center;
  gap: 10px;
  position: relative;
}

.search-input {
  padding: 6px 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  flex-grow: 1;
}

.search-dropdown {
  position: relative;
}

.search-button {
  padding: 6px 12px;
  background-color: #3498db;
  border: none;
  color: white;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  display: flex;
  align-items: center;
}

.search-button .dropdown-icon {
  margin-left: 5px;
}

.search-options {
  position: absolute;
  top: 100%;
  left: 0;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 4px;
  overflow: hidden;
  z-index: 100;
  width: 100%;
}

.search-option {
  padding: 6px 12px;
  cursor: pointer;
  background-color: #fff;
  transition: background-color 0.2s;
}

.search-option:hover {
  background-color: #f0f0f0;
}

</style>
