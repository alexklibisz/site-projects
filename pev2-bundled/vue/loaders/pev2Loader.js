import Vue from "vue"
import Pev2 from "../components/Pev2.vue"

document
  .querySelectorAll("[data-vue-component=pev2]")
  .forEach((el) => {
    // Maybe there is a better way to pass this data through?
    Pev2.data = {
        query: el.children[0].innerText,
        plan: el.children[1].innerText
    }
    new Vue(Pev2).$mount(el)
  })