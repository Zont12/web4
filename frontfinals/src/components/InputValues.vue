<template>
  <div class="main-wrapper">
    <div class="container">
      <!-- Блок InputValues -->
      <div class="InputValues" id="form">
        <div class="Second_block">
          <!-- ЧЕКБОКСЫ X -->
          <label class="Top">Координата X (-2 ... 2):</label>
          <div id="X">
            <label
                v-for="xValue in xOptions"
                :key="xValue"
                class="checkbox-container"
            >
              <input
                  type="checkbox"
                  :value="xValue"
                  v-model="coorX"
                  @change="validateCoordinates('x')"
              />
              <span class="checkbox-label">{{ xValue }}</span>
            </label>
          </div>
          <span v-if="errors.x" class="error-popup">{{ errors.x }}</span>

          <!-- ПОЛЕ ВВОДА Y (ТЕКСТОВОЕ) -->
          <label class="Top">Координата Y (-3 ... 5):</label>
          <div id="Y">
            <input
                id="y-input"
                type="text"
                v-model="coorY"
                @input="validateCoordinates('y')"
            />
          </div>
          <span v-if="errors.y" class="error-popup">{{ errors.y }}</span>

          <!-- ЧЕКБОКСЫ R -->
          <label class="Top">Радиус R (-2 ... 2):</label>
          <div id="R">
            <label
                v-for="rValue in rOptions"
                :key="rValue"
                class="checkbox-container"
            >
              <input
                  type="checkbox"
                  :value="rValue"
                  v-model="coorR"
                  @change="validateRadius"
              />
              <span class="checkbox-label">{{ rValue }}</span>
            </label>
          </div>
          <span v-if="errors.r" class="error-popup">{{ errors.r }}</span>

          <!-- КНОПКИ -->
          <div class="button_table">
            <button class="control-btn" @click="updateCanvas">Обновить область</button>
            <button class="control-btn" @click="sendPoint">Отправить на сервер</button>
          </div>
        </div> <!-- .Second_block -->
      </div> <!-- #form.InputValues -->
    </div> <!-- .container -->
  </div> <!-- .main-wrapper -->
</template>

<script setup>
import { ref, defineEmits } from 'vue';

const emit = defineEmits(['update-canvas', 'point-sent']);

// Массивы чекбоксов X и R
const xOptions = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];
const rOptions = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];

// coorX/coorR — массивы, coorY — строка (текстовое поле)
const coorX = ref([]);
const coorY = ref('');
const coorR = ref([]);

const errors = ref({ x: '', y: '', r: '' });

// Проверка X или Y
const validateCoordinates = (axis) => {
  if (axis === 'x') {
    // Для X проверяем, что выбран хотя бы один чекбокс
    if (coorX.value.length === 0) {
      errors.value.x = 'Выберите хотя бы одно значение X.';
    } else {
      errors.value.x = '';
    }
  } else if (axis === 'y') {
    const valueY = parseFloat(coorY.value);
    // Значение от -3 до 5
    if (isNaN(valueY) || valueY < -3 || valueY > 5) {
      errors.value.y = 'Координата Y должна быть числом в диапазоне от -3 до 5.';
    } else {
      errors.value.y = '';
    }
  }
};

// Проверка R
const validateRadius = () => {
  // Аналогично X — хотя бы один чекбокс
  if (coorR.value.length === 0) {
    errors.value.r = 'Выберите хотя бы одно значение R.';
  } else {
    errors.value.r = '';
  }
};

// Кнопка "Обновить область" (Canvas)
const updateCanvas = () => {
  // Сначала убеждаемся, что нет ошибок
  if (!errors.value.x && !errors.value.y && !errors.value.r) {
    // Передаем массивы X, R и одно число Y
    emit('update-canvas', {
      x: coorX.value.map(Number),
      y: parseFloat(coorY.value),
      r: coorR.value.map(Number)
    });
  }
};

// Кнопка "Отправить" на сервер
const sendPoint = () => {
  // Снова проверяем
  validateCoordinates('x');
  validateCoordinates('y');
  validateRadius();

  // Если нет ошибок — отправляем
  if (!errors.value.x && !errors.value.y && !errors.value.r) {
    const point = {
      x: coorX.value.map(Number),
      y: parseFloat(coorY.value),
      r: coorR.value.map(Number)
    };
    emit('point-sent', point);

    // Очистим форму
    coorX.value = [];
    coorY.value = '';
    coorR.value = [];
    errors.value = { x: '', y: '', r: '' };
  }
};
</script>

<style scoped>
/*
  ----------------------
  Базовые стили фона
  ----------------------
*/
body {
  margin: 0;
  background-image: url("https://www.northerniowan.com/wp-content/uploads/2024/03/breaking-bad-1200x675.png");
  background-repeat: repeat;
  background-size: cover;
  background-position: center 100px;
}

/*
  Обёртки для адаптивности (как во втором примере)
*/
.main-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh; /* чтобы тянуться на всю высоту */
  justify-content: center; /* при желании можно убрать или настроить */
}


.container {
  display: flex;
  flex-direction: column;
  gap: 20px;
  width: 90%;
  max-width: 1200px; /* ограничиваем ширину контейнера */
  margin: 0 auto;
}

/*
  -------------------------
  Основной блок формы
  -------------------------
*/
.InputValues {
  width: 100%;
}

/*
  Второй блок (с фоном-доской)
  где расположены чекбоксы и кнопки
*/
.Second_block {
  margin-top: 20px;
  border: 5px solid black;
  background-image: url("https://img.freepik.com/premium-photo/old-green-board-texture-grunge-background_625816-828.jpg");
  background-size: contain;
  /* Высоту и ширину задаём адаптивно */
  width: 500px;
  padding: 20px;
  position: relative;
}

/* ---------------------------
   Стили для названий полей (X, Y, R)
   --------------------------- */
.Top {
  color: white;
  font-family: 'Courier New', monospace;
  text-align: center;
  display: block;
  margin-bottom: 4px;
}

/* X, Y, R */
#X,
#Y,
#R {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 10px 0;
  color: white;
  font-family: 'Courier New', monospace;
}

/* Чекбоксы */
.checkbox-container {
  display: flex;
  align-items: center;
  gap: 5px;
  margin: 0 6px;
}

input[type="checkbox"] {
  appearance: none;
  width: 14px;
  height: 14px;
  background-color: darkgreen;
  border: 1px solid white;
  cursor: pointer;
}

input[type="checkbox"]:checked {
  position: relative;
}

input[type="checkbox"]:checked::before {
  content: "✔";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 10px;
  color: white;
}

/* Подписи у чекбоксов */
.checkbox-label {
  font-family: 'Courier New', monospace;
  color: white;
}

/* Поле ввода Y (текст) */
#y-input {
  background-color: darkgreen;
  color: white;
  border: 2px solid white;
  font-family: 'Courier New', monospace;
  font-size: 14px;
  outline: none;
  text-align: center;
}

/* Кнопки */
.button_table {
  margin: 0 auto;
  width: 100%;
  text-align: center;
}

.control-btn {
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

.control-btn:hover {
  background-color: limegreen;
  color: black;
}

/*
  --------------------------
  Ошибки (всплывающие подсказки)
  --------------------------
*/
.error-popup {
  position: absolute;
  background-color: green;
  font-family: 'Courier New', monospace;
  color: white;
  padding: 2px 5px;
  border-radius: 5px;
  font-size: 11px;
  white-space: nowrap;
  z-index: 1000;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.3);
  transform: translate(-50%, -110%);
  margin-top: -40px;
  margin-left: 230px;
}
.error-popup::after {
  opacity: 0.8;
  content: '';
  position: absolute;
  border-width: 5px;
  border-style: solid;
  border-color: green transparent transparent transparent;
  top: 100%;
  left: 50%;
}


/* Убираем слишком узкие/странные размеры в промежуточном диапазоне */
@media (min-width: 889px) and (max-width: 1136px) {
  .container {
    flex-direction: row;
    flex-wrap: wrap;
  }
  /* Делаем все блоки на 100%, чтобы ровно «вписывались» друг под друга */
  .InputValues, .Table, .Canvas {
    width: 100%;
  }
  .Second_block {
    width: 100%;
    margin: 0 auto;
  }
}

/* На больших экранах ставим .Table шире,
   а оставшиеся блоки сужаем, чтобы суммарно было ~100% */
@media (min-width: 1137px) {
  .container {
    display: flex;
    flex-direction: row;
    justify-content: center; /* Центрируем контент */
    gap: 20px; /* Отступы между элементами */
    width: 100%;
    max-width: 1400px; /* Ограничиваем максимальную ширину */
  }

  /* Устанавливаем более адекватное соотношение размеров */
  .InputValues {
    flex: 1; /* Растягивается, но не доминирует */
    max-width: 350px;
  }
}

</style>
