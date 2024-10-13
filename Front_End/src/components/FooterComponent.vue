<template>
  <footer class="footer">
    <div class="footer__content">
      <div class="footer__links">
        <a href="/home" class="footer__link">Home</a>
        <a href="/about" class="footer__link">About Us</a>
        <a href="/contact" class="footer__link">Contact</a>
        <a href="/terms" class="footer__link">Terms of Service</a>
        <a href="/privacy" class="footer__link">Privacy Policy</a>
      </div>

      <div class="footer__copyright">
        <p>&copy; 2024 MovieApp. All rights reserved.</p>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: "FooterComponent",
};
</script>

<style scoped>
.footer {
  background-color: #2c3e50;
  color: white;
  padding: 20px 0;
  text-align: center;
}

.footer__content {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.footer__links {
  margin-bottom: 10px;
}

.footer__link {
  color: white;
  margin: 0 10px;
  text-decoration: none;
  font-size: 1rem;
}

.footer__link:hover {
  text-decoration: underline;
}

.footer__copyright {
  font-size: 0.9rem;
}
</style>
