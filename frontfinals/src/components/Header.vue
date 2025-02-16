<template>
  <header class="header">
    <div class="header-content">
      <span class="cap_header">Labolatorn 4</span>
      <span class="cap_header">Solodovnikov Danila P3207</span>
      <span class="cap_header">Variant 430446</span>
    </div>
  </header>
</template>

<style scoped>
/* Основные стили */
.header {
  width: 100%;
  position: fixed;
  top: 0;
  left: 0;
  background-image: url("https://7themes.su/_ph/40/548422915.jpg");
  background-size: cover;
  background-position: center;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px 0;
  z-index: 1000;
  border-bottom: 3px solid green;
}

.header-content {
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.cap_header {
  font-family: 'Courier New', monospace;
  font-size: 20px;
  color: white;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.8);
}

/* Адаптивность */
@media (min-width: 889px) and (max-width: 1136px) {
  .cap_header {
    font-size: 16px;
    margin-right: 0;


  }
}
</style>
