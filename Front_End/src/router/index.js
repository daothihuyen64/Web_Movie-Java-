import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../pages/HomePage.vue';  // Import Home Page
import FavoriteMovies from '../pages/FavoriteMovies.vue';
// import SearchPage from '../pages/SearchPage.vue';
import RankComponent from '../components/RankComponent.vue';
import SearchResults from '../pages/SearchResults.vue';
import LoginPage from '../pages/LoginPage.vue';
import store from '../store/store.js';  
import UserSettings from '../pages/UserSettings.vue';
import SignupPage from '../pages/SignupPage.vue';
import IdGenreMoviesPage from '../pages/IdGenreMoviesPage.vue';
import IdCountryMoviesPage from '../pages/IdCountryMoviesPage.vue';
import IdReleaseYearMoviesPage from '../pages/IdReleaseYearMoviesPage.vue';
import InfoMoviePage from '../pages/InfoMoviePage.vue';

const routes = [
  { path: '/', name : 'home',component: HomePage },  // Định nghĩa route cho HomePage
  { path: '/signin', name : 'login',component: LoginPage },
  { path: '/signup', name : 'signup', component: SignupPage },
  { path: '/xep-hang', name : 'rank', component: RankComponent },
  { path: '/search', name: 'search-results', component: SearchResults },
  { path: '/settings', name: 'setting', component: UserSettings },
  { path: '/favouriteMovies', name: 'favouriteMovies', component: FavoriteMovies },
  { path: '/the-loai/:genreId', name: 'IdGenreMoviesPage', component: IdGenreMoviesPage, props: true},
  { path: '/quoc-gia/:countryId', name: 'IdCountryMoviesPage', component: IdCountryMoviesPage, props: true},
  { path: '/nam-phat-hanh/:releaseYearId', name: 'IdReleaseYearMoviesPage', component: IdReleaseYearMoviesPage, props: true},
  { path: '/movie/:id', name: 'InfoMoviePage', component: InfoMoviePage, props: true},
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// router.beforeEach((to, from, next) => {
//   const isAuthenticated = store.getters.isAuthenticated;
  
//   if (to.name !== 'login' && !isAuthenticated) {
//     next({ name: 'login' });
//   } else {
//     next();
//   }
// });
router.beforeEach((to, from, next) => {
  const isAuthenticated = store.getters.isAuthenticated;

  // Kiểm tra nếu route yêu cầu đăng nhập
  if (!isAuthenticated && to.name !== 'login' && to.name !== 'signup') {
    next({ name: 'login' }); // Nếu chưa đăng nhập, điều hướng đến trang đăng nhập
  } else {
    next(); // Nếu đã đăng nhập hoặc trang không yêu cầu đăng nhập, cho phép tiếp tục
  }
});

export default router;
