import Vue from "vue";
import Hello from "../components/Hello.vue";

document
  .querySelectorAll("[data-vue-component=pev2]")
  .forEach((element) => {
    new Vue(Hello).$mount(element);
  });