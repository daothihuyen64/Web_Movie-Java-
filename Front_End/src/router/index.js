import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '../pages/HomePage.vue';  // Import Home Page
import FavoritePage from '../pages/FavoritePage.vue';
import SearchPage from '../pages/SearchPage.vue';
import RankComponent from '../components/RankComponent.vue';

const routes = [
  { path: '/', component: HomePage },  // Định nghĩa route cho HomePage
  { path: '/login', component: SearchPage },
  { path: '/signup', component: FavoritePage },
  { path: '/xep-hang', component: RankComponent },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
