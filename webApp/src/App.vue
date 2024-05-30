<template>
  <div id="app">
    <img src="./assets/logo.png" />
    <router-view />
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      blogs: []
    };
  },
  mounted() {
    //   var _this=this
    //   const url='http://47.101.184.147:8080/getUser'
    //   axios.get(url).then(resp=>{
    //     this.blogs=resp.data;
    //     console.log(_this.blogs);
    //   })
  }
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
img {
  width: 400px; /* 你可以根据需要调整这个值 */
  height: auto; /* 保持图片的宽高比 */
}
</style>
