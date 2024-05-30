<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <li>
      <router-link to="/change-password">
        Change Password
      </router-link>
    </li>
    <li>
      <router-link @click.native="logout" to="/">
        Logout
      </router-link>
    </li>
  </div>
</template>

<script>
export default {
  name: "HelloWorld",
  data() {
    return {
      msg: "网络与系统安全实验"
    };
  },
  methods: {
    logout() {
      this.$router.push("/");
      localStorage.removeItem("Decrypted Verification Code");
      localStorage.removeItem("Encrypted Verification Code");
    }
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1,
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
