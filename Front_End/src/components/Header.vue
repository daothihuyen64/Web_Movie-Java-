<template>
  <header class="header">
    <!-- Logo của trang -->
    <div class="logo">
      <img src="@/assets/logo.png" alt="Logo" />
      <h1>BoBa</h1>
    </div>

    <!-- Thanh điều hướng -->
    <nav class="nav">
      <ul>
        <li>
          <router-link to="/">Home</router-link>
        </li>
        <li>
          <router-link to="/login">Đăng nhập</router-link>
        </li>
        <li>
          <router-link to="/signup">Đăng ký</router-link>
        </li>
        <!-- <li>
          <router-link to="/HomeHuyen">HomeHuyen</router-link>
        </li> -->
      </ul>
    </nav>
  </header>
</template>

<script>
export default {
  name: 'HeaderComponent',
};
</script>

<style scoped>
/* CSS cho Header */
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #000;/* #333;*/
  color: white;
}

.logo {
  display: flex;
  align-items: center;
}

.logo img {
  width: 50px;
  height: 50px;
  margin-right: 10px;
}

.nav ul {
  list-style-type: none;
  display: flex;
  gap: 20px;
  margin: 0;
  padding: 0;
}

.nav ul li {
  display: inline;
}

.nav ul li a {
  color: white;
  text-decoration: none;
  font-weight: bold;
}

.nav ul li a:hover {
  color: #ffcc00;
}
</style>
