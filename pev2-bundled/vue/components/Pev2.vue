<template>
  <pev2 :plan-source="plan" :plan-query="query"></pev2>
</template>

<script>
import pev2 from "pev2";
export default {
  data: {
    plan: "",
    query: ""
  },
  components: {
    pev2
  }
};
</script>

<style></style>