import Vue from "vue";
import Hello from "../components/Hello.vue";

// The following creates a new instance of the component and puts it anywhere
// a element has a data-vue-component="hello" attribute.
document
  .querySelectorAll("[data-vue-component=hello]")
  .forEach((element) => {
    new Vue(Hello).$mount(element);
  });