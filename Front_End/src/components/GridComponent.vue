<!-- GridComponent.vue -->
<template>
  <div class="grid-container">
    <slot /> <!-- Nơi để chứa các phần tử được truyền vào từ bên ngoài (như MovieCard) -->
  </div>
</template>

<script>
export default {
  name: 'GridComponent'
};
</script>

<style scoped>
.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(176px, 1fr)); /* Tự động tạo cột */
  grid-gap: 20px; /* Khoảng cách giữa các phần tử trong lưới */
  padding: 20px;
}
</style>
