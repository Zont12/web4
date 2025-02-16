<script setup>
import { defineProps } from 'vue';

defineProps({
  results: {
    type: Array,
    default: () => []
  }
});
</script>

<template>
  <table>
    <thead>
    <tr>
      <th>Координаты X</th>
      <th>Координата Y</th>
      <th>Радиус (R)</th>
      <th>Результат попадания</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="(point, index) in results" :key="index">
      <td>{{ point.x }}</td>
      <td>{{ point.y }}</td>
      <td>{{ point.r }}</td>
      <td>{{ point.hit ? 'Попал' : 'Не попал' }}</td>
    </tr>
    </tbody>
  </table>
</template>

<style scoped>
table {
  width: 100%;
  margin-top:20px;
  border-radius: 15px;
  border-collapse: collapse;
  width: 850px

}

th, td {
  padding: 20px;
  text-align: center;
  border: 1px solid green;
}

th {
  background-image: url("https://7themes.su/_ph/40/548422915.jpg");
  color: white;
  font-size: 21px;

}


/* Tablet styles */
@media (min-width: 889px) and (max-width: 1136px) {
  table {
    width: 90%;
    margin: 5px auto; /* Center the table */
  }
  th, td {
    font-size: 16px; /* Adjusted font size for better readability */
    padding: 8px;
  }
}

/* Mobile styles */
@media (max-width: 888px) {
  table {
    width: 100%;
  }
  th, td {
    font-size: 8px; /* Increased font size for mobile */
    padding: 6px;
  }
  td {
    word-wrap: break-word;
    white-space: normal;
  }
}
</style>