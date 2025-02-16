import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index.js' // Импорт маршрутизатора

const app = createApp(App)
app.use(router) // Использование маршрутизатора
app.mount('#app')
