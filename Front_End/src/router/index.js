import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../pages/HomePage.vue';  // Import Home Page
import FavoritePage from '../pages/FavoritePage.vue';
// import SearchPage from '../pages/SearchPage.vue';
import RankComponent from '../components/RankComponent.vue';
import SearchResults from '../pages/SearchResults.vue';
import LoginPage from '../pages/LoginPage.vue';
// import store from '../store/store.js';  

const routes = [
  { path: '/', name : 'home',component: HomePage },  // Định nghĩa route cho HomePage
  { path: '/login', name : 'login',component: LoginPage },
  { path: '/signup', name : 'signup', component: FavoritePage },
  { path: '/xep-hang', name : 'rank', component: RankComponent },
  { path: '/search', name: 'search-results', component: SearchResults },
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

export default router;
