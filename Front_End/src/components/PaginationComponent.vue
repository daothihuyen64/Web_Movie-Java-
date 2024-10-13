<!-- PaginationComponent.vue -->
<template>
  <div class="pagination">
    <button 
      v-for="page in totalPages" 
      :key="page" 
      @click="changePage(page)"
      :class="{ active: currentPage === page }">
      {{ page }}
    </button>
  </div>
</template>

<script>
export default {
  name: "PaginationComponent",
  props: {
    totalItems: {
      type: Number,
      required: true
    },
    itemsPerPage: {
      type: Number,
      default: 20
    },
    currentPage: {
      type: Number,
      default: 1
    }
  },
  computed: {
    totalPages() {
      return Math.ceil(this.totalItems / this.itemsPerPage);
    }
  },
  methods: {
    changePage(page) {
      this.$emit("page-changed", page); // Gửi sự kiện lên component cha khi trang thay đổi
    }
  }
};
</script>

<style scoped>
.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.pagination button {
  background: #1e1e1e;
  color: white;
  border: none;
  padding: 10px 20px;
  margin: 0 5px;
  cursor: pointer;
  transition: background 0.3s ease;
}

.pagination button.active {
  background: #f39c12;
}

.pagination button:hover {
  background: #333;
}
</style>
