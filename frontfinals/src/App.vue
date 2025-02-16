<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import Header from "@/components/Header.vue";
import InputValues from "@/components/InputValues.vue";
import Table from "@/components/Table.vue";
import Canvas from "@/components/Canvas.vue";

// Маршрутизатор
const router = useRouter();

// Состояния
const canvasData = ref({ x: 0, y: 0, r: 1 });
const pointsList = ref([]);

// Обработчики
const updateCanvasData = (data) => {
  canvasData.value = data;
};

const onPointSent = async (point) => {
  try {
    const response = await fetch('http://localhost:8080/backend-1.0-SNAPSHOT/api/point', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(point),
    });
    if (!response.ok) {
      throw new Error('Ошибка отправки точки.');
    }
    const data = await response.json();
    pointsList.value.push({ ...point, hit: data.hit });
  } catch (error) {
    console.error(error.message || 'Ошибка при отправке точки. Попробуйте ещё раз.');
  }
};

const handlePointClick = async (point) => {
  try {
    const response = await fetch('http://localhost:8080/backend-1.0-SNAPSHOT/api/point', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(point),
    });
    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || 'Ошибка отправки точки.');
    }
    const data = await response.json();
    pointsList.value.push({ ...point, hit: data.hit });
  } catch (error) {
    console.error(error.message || 'Ошибка при отправке точки. Попробуйте ещё раз.');
  }
};

const logout = () => {
  localStorage.removeItem('token');
  router.push('/');
};
</script>

<template>
  <Header />

  <main class="main-layout">
    <div class="top-row">
      <div class="left-col">
        <InputValues @update-canvas="updateCanvasData" @point-sent="onPointSent" />
      </div>
      <div class="right-col">
        <Canvas :data="canvasData" :points="pointsList" @point-clicked="handlePointClick" />
      </div>
    </div>

    <div class="button-row">
      <button @click="logout">Выйти</button>
    </div>

    <div class="table-row">
      <Table :results="pointsList" />
    </div>
  </main>
</template>

<style scoped>
.main-layout {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
}


.top-row {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  align-items: flex-start;
  gap: 40px;
  width: 100%;
}

.left-col,
.right-col {
  flex: 0 1 auto;
  box-sizing: border-box;
  min-width: 300px;
}

.right-col {
  margin-top: 380px;
  margin-left: 150px;
}

.button-row {
  display: flex;
  justify-content: center;
  order: 2;
  margin-top: -220px;
}
.button-row button {
  background-color: darkgreen;
  color: white;
  border: 2px solid white;
  padding: 10px 20px;
  margin: 5px;
  cursor: pointer;
  font-family: 'Courier New', monospace;
  font-size: 16px;
  border-radius: 2px;
  transition: background-color 0.3s ease;
}
.button-row button:hover {
  background-color: limegreen;
  color: black;
}


.table-row {
  order: 3;
  display: flex;
  justify-content: center;
  width: 100%;
}

@media (min-width: 390px) and (max-width: 1136px) {
  .main-layout {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
  }

  table {
    width: 90%;
    margin: 0 auto;
  }

  th, td {
    font-size: 12px;
    padding: 8px;
  }

  td {
    word-wrap: break-word;
    white-space: normal;
  }

  .right-col {
    margin-top: -260px;
  }

  .button-row {
    order: 3 !important;
    margin-top: 50px;
    margin-left: 150px;
  }

  .table-row {
    order: 4 !important;
    margin-left: 150px;
  }
}

</style>
