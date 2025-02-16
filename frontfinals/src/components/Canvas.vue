<script setup>
import { ref, onMounted, onUnmounted, defineProps, defineEmits, watch } from 'vue';

const props = defineProps(['data', 'points']);
const emit = defineEmits(['point-clicked']);

const canvas = ref(null);
const backgroundCanvas = ref(null);
const ctx = ref(null);
const bgCtx = ref(null);

const fixedR = 100;
const scale = fixedR / 2;

function drawBackground() {
  bgCtx.value.fillStyle = "white";
  bgCtx.value.fillRect(0, 0, backgroundCanvas.value.width, backgroundCanvas.value.height);

  const img = new Image();
  img.src = "https://lifehacker.ru/wp-content/uploads/2016/02/cover_1455227228.jpg";
  img.onload = () => {
    bgCtx.value.drawImage(img, 0, 0, backgroundCanvas.value.width, backgroundCanvas.value.height);
  };
}

function drawAxes() {
  ctx.value.clearRect(0, 0, canvas.value.width, canvas.value.height);
  const width = canvas.value.width;
  const height = canvas.value.height;
  const centerX = width / 2;
  const centerY = height / 2;

  ctx.value.beginPath();
  ctx.value.strokeStyle = "black";
  ctx.value.lineWidth = 3;

  // Ось X
  ctx.value.moveTo(0, centerY);
  ctx.value.lineTo(width, centerY);

  // Ось Y
  ctx.value.moveTo(centerX, 0);
  ctx.value.lineTo(centerX, height);

  ctx.value.stroke();
  drawLabelsAndPoints();
}

function drawLabelsAndPoints() {
  const centerX = canvas.value.width / 2;
  const centerY = canvas.value.height / 2;

  ctx.value.fillStyle = "black";
  ctx.value.font = "bold 14px Courier New";

  // Метки на оси X
  drawPoint(centerX + fixedR, centerY, "R");
  drawPoint(centerX + fixedR / 2, centerY, "R/2");
  drawPoint(centerX - fixedR, centerY, "-R");
  drawPoint(centerX - fixedR / 2, centerY, "-R/2");

  // Метки на оси Y
  drawPoint(centerX, centerY - fixedR, "R");
  drawPoint(centerX, centerY - fixedR / 2, "R/2");
  drawPoint(centerX, centerY + fixedR, "-R");
  drawPoint(centerX, centerY + fixedR / 2, "-R/2");
}

function drawPoint(x, y, label) {
  ctx.value.beginPath();
  ctx.value.moveTo(x, y - 5);
  ctx.value.lineTo(x, y + 5);
  ctx.value.stroke();
  ctx.value.fillText(label, x + 5, y - 5);
}

function drawShapes() {
  const centerX = canvas.value.width / 2;
  const centerY = canvas.value.height / 2;

  ctx.value.beginPath();
  ctx.value.fillStyle = "rgba(0, 0, 255, 0.5)";

  // Прямоугольник (верхняя правая часть)
  ctx.value.fillRect(centerX, centerY - fixedR, fixedR / 2, fixedR);

  // Треугольник (нижняя правая часть)
  ctx.value.beginPath();
  ctx.value.moveTo(centerX, centerY);
  ctx.value.lineTo(centerX + fixedR / 2, centerY);
  ctx.value.lineTo(centerX, centerY + fixedR / 2);
  ctx.value.closePath();
  ctx.value.fill();

  // Четверть круга (нижняя левая часть)
  ctx.value.beginPath();
  ctx.value.moveTo(centerX, centerY);
  ctx.value.arc(centerX, centerY, fixedR / 2, 0.5 * Math.PI, Math.PI, false);
  ctx.value.closePath();
  ctx.value.fill();
}

function drawPoints() {
  props.points.forEach(point => {
    const centerX = canvas.value.width / 2;
    const centerY = canvas.value.height / 2;
    const scaledX = centerX + point.x * scale;
    const scaledY = centerY - point.y * scale;

    ctx.value.beginPath();
    ctx.value.arc(scaledX, scaledY, 3, 0, 2 * Math.PI);
    ctx.value.fillStyle = 'red';
    ctx.value.fill();
  });
}

function drawCanvas() {
  ctx.value.clearRect(0, 0, canvas.value.width, canvas.value.height);
  drawAxes();
  drawShapes();
  drawPoints();
}

onMounted(() => {
  bgCtx.value = backgroundCanvas.value.getContext('2d');
  ctx.value = canvas.value.getContext('2d');

  drawBackground();
  drawCanvas();

  canvas.value.addEventListener('click', handleCanvasClick);
});

onUnmounted(() => {
  canvas.value.removeEventListener('click', handleCanvasClick);
});

const handleCanvasClick = (event) => {
  const rect = canvas.value.getBoundingClientRect();
  const x = event.clientX - rect.left;
  const y = event.clientY - rect.top;
  const centerX = canvas.value.width / 2;
  const centerY = canvas.value.height / 2;
  const actualX = (x - centerX) / scale;
  const actualY = (centerY - y) / scale;
  emit('point-clicked', { x: actualX, y: actualY, r: fixedR });
};

watch(
    () => props.points,
    drawCanvas,
    { deep: true }
);
</script>

<template>
  <div class="canvas-container">
    <!-- width/height 400x400 — логические px для рисования;
         фактический размер через CSS (width: 100%, height: 100%). -->
    <canvas ref="backgroundCanvas" width="400" height="400"></canvas>
    <canvas ref="canvas" width="400" height="400"></canvas>
  </div>
</template>

<style scoped>
.canvas-container {
  position: relative;
  /* Сохраняем максимум 400px, но если места меньше, то впишется в 100% */
  max-width: 400px;
  /* Квадратные пропорции */
  aspect-ratio: 1 / 1;
  width: 100%;
  margin: 0 auto;
}

canvas {
  position: absolute;
  top: 0;
  left: 0;
  /* Распахиваем канву на все 100% контейнера */
  width: 100%;
  height: 100%;

  border-radius: 15px;
  border: 5px solid black;
}

canvas:first-child {
  z-index: 0; /* Фон */
}

canvas:last-child {
  z-index: 1; /* График */
}

/* Доп. медиазапросы под разные экраны:
   - можно подправлять толщину рамки, радиус и т. д. */

@media (max-width: 480px) {
  canvas {
    border: 3px solid black;
    border-radius: 10px;
  }
}

@media (min-width: 1440px) {
  /* Если очень большие экраны — можем чуть увеличить */
  .canvas-container {
    max-width: 500px; /* например, даём чуть больше 400px */
  }
}
</style>
