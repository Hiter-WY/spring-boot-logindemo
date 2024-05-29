import Vue from "vue";
import Router from "vue-router";
import HelloWorld from "@/components/HelloWorld";
import Login from "@/components/Login";
import Register from "@/components/RegisterView";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "登录",
      hidden: true,
      component: Login
    },
    {
      path: "/register",
      name: "注册",
      hidden: true,
      component: Register
    },
    {
      path: "/HelloWorld",
      name: "HelloWorld",
      component: HelloWorld
    }
  ]
});
