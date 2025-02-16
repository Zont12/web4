<template>
  <div class="home">
    <h2>Регистрация</h2>
    <form @submit.prevent="handleSubmit" class="auth-form">
      <div class="form-group">
        <label for="login">Логин:</label>
        <input type="text" id="new_login" v-model="login" required />
      </div>
      <div class="form-group">
        <label for="password">Пароль:</label>
        <input type="password" id="new_password" v-model="password" required />
      </div>
      <button type="submit" class="submit-button">Зарегистрироваться</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      login: '',
      password: ''
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
            login: this.login,
            password: this.password,
          }),
        });

        if (!response.ok) {
          const errorData = await response.json();
          throw new Error(errorData.message || 'Логин уже существует. Пожалуйста, выберите другой.');
        }

        const data = await response.json();
        console.log('Успех:', data);
      } catch (error) {
        console.error('Ошибка:', error);
      }
    },
  },
};
</script>

<style scoped>

@media (min-width: 889px) and (max-width: 1136px) {
  .home {
    width: 50%;
  }
  input {
    width: 95%;
  }
}

@media (max-width: 888px) {
  .home {
    width: 90%;
  }
  input {
    width: 100%;
  }
}
</style>