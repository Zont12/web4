<template>
  <div>
    <Header /> <!-- Используем общую шапку -->
    <div class="container">
      <div class="auth-section">
        <div class="auth-box">
          <h2>Авторизация</h2>
          <form @submit.prevent="handleLogin" class="auth-form">
            <div class="form-group">
              <label for="login">Логин:</label>
              <input type="text" id="login" v-model="login" required />
            </div>
            <div class="form-group">
              <label for="password">Пароль:</label>
              <input type="password" id="password" v-model="password" required />
            </div>
            <button type="submit" class="submit-button">Войти</button>
          </form>
        </div>
        <div class="auth-box">
          <h2>Регистрация</h2>
          <form @submit.prevent="handleSubmit" class="auth-form">
            <div class="form-group">
              <label for="new_login">Логин:</label>
              <input type="text" id="new_login" v-model="newLogin" required />
            </div>
            <div class="form-group">
              <label for="new_password">Пароль:</label>
              <input type="password" id="new_password" v-model="newPassword" required />
            </div>
            <button type="submit" class="submit-button">Зарегистрироваться</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/Header.vue"; // Импорт шапки

export default {
  components: { Header },
  data() {
    return {
      login: '',
      password: '',
      newLogin: '',
      newPassword: ''
    };
  },
  methods: {
    async handleSubmit() {
      try {
        const response = await fetch('http://localhost:8080/backend-1.0-SNAPSHOT/api/register', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            login: this.newLogin,
            password: this.newPassword,
          }),
        });

        if (!response.ok) {
          const errorData = await response.json();
          throw new Error(errorData.message || 'Логин уже существует. Пожалуйста, выберите другой.');
        }

        console.log('Регистрация успешна');
      } catch (error) {
        console.error('Ошибка:', error);
      }
    },
    async handleLogin() {
      console.log("Авторизация в процессе");
    }
  },
};
</script>

<style scoped>
html, body {
  height: 100%;
  width: 100%;
  margin: 0;
  padding: 0;
  background-color: #f9f9f9;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100vh;
  padding: 30px;
}

.auth-section {
  display: flex;
  gap: 20px;
  justify-content: center;
  align-items: center;
  width: 100%;
  max-width: 800px;
}

.auth-box {
  flex: 1;
  padding: 20px;
  border: 5px solid black;
  background-image: url("https://img.freepik.com/premium-photo/old-green-board-texture-grunge-background_625816-828.jpg");
  background-size: contain;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

h2 {
  text-align: center;
  color: white;
  font-family: 'Courier New', monospace;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  color: white;
  font-family: 'Courier New', monospace;
  margin-bottom: 5px;
}

input {
  width: 95%;
  padding: 5px;
  border: 2px solid white;
  background-color: darkgreen;
  color: white;
  font-family: 'Courier New', monospace;
  font-size: 14px;
  text-align: center;
  outline: none;
}

input:focus {
  border-color: limegreen;
}

.submit-button {
  width: 100%;
  padding: 10px;
  background-color: darkgreen;
  color: white;
  border: 2px solid white;
  font-family: 'Courier New', monospace;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  border-radius: 2px;
}

.submit-button:hover {
  background-color: limegreen;
  color: black;
}

@media (max-width: 768px) {
  .auth-section {
    flex-direction: column;
    width: 90%;
  }
}
</style>
