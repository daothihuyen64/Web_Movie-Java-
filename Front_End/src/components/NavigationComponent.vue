<!-- NavigationComponent.vue -->
<template>
  <nav class="navigation">
    <ul>
      <li><router-link to="/the-loai">Thể Loại</router-link></li>
      <li><router-link to="/quoc-gia">Quốc Gia</router-link></li>
      <li><router-link to="/nam-phat-hanh">Năm Phát Hành</router-link></li>
      <li><router-link to="/xep-hang">Xếp Hạng</router-link></li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: 'NavigationComponent'
};
</script>

<style scoped>
.navigation {
  background-color: #111; /* Đặt màu nền tối */
  padding: 10px 0;
}

.navigation ul {
  list-style-type: none;
  display: flex;
  justify-content: space-around;
  padding: 0;
  margin: 0;
}

.navigation li {
  margin: 0 10px;
}

.navigation a {
  color: white;
  text-decoration: none;
  font-weight: bold;
}

.navigation a:hover {
  color: #3498db; /* Đổi màu khi hover */
}
</style>
