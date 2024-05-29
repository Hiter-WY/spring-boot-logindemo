<template>
  <el-form
    :rules="rules"
    class="register-container"
    label-position="left"
    label-width="0px"
    v-loading="loading"
  >
    <h3 class="register_title">系统注册</h3>
    <el-form-item prop="username">
      <el-input
        type="text"
        v-model="registerForm.username"
        auto-complete="off"
        placeholder="账号"
      ></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input
        type="password"
        v-model="registerForm.password"
        auto-complete="off"
        placeholder="密码"
      ></el-input>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button
        type="primary"
        @click.native.prevent="register"
        style="width: 100%"
        >注册</el-button
      >
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="text" @click="goToLogin" style="width: 100%"
        >已有账号？登录</el-button
      >
    </el-form-item>
  </el-form>
</template>

<script>
import axios from "axios";
import sha256 from "crypto-js/sha256";
// import request from "../utils/request.js";

export default {
  data() {
    return {
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" }
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }]
      },
      registerForm: {
        username: "",
        password: ""
      },
      loading: false
    };
  },
  methods: {
    generateVerificationCode() {
      return Math.floor(100000 + Math.random() * 900000).toString();
    },
    register() {
      this.loading = true;
      // const verificationCode = this.generateVerificationCode();
      const hash1 = sha256(
        this.registerForm.username + this.registerForm.password
      ).toString();
      // const hash2 = sha256(hash1 + verificationCode).toString();
      axios
        .post(
          "http://localhost:8084/user/register",
          {
            username: this.registerForm.username,
            hash1: hash1
          },
          {
            headers: {
              "Content-Type": "application/json"
            }
          }
        )
        .then(response => {
          this.loading = false;
          if (response.data === "注册成功") {
            alert("注册成功");
            this.$router.replace({ path: "/" });
          } else {
            alert("注册失败：" + response.data.msg);
          }
        })
        .catch(() => {
          this.loading = false;
          alert("服务器错误");
        });
      // request
      //   .post("/register", {
      //     username: this.registerForm.username,
      //     hash1: hash1,
      //     verificationCode: verificationCode
      //   })
      //   .then(response => {
      //     this.loading = false;
      //     if (response.data.success) {
      //       alert("注册成功");
      //       this.$router.replace({ path: "/" });
      //     } else {
      //       alert("注册失败：" + response.data.msg);
      //     }
      //   })
      //   .catch(() => {
      //     this.loading = false;
      //     alert("服务器错误");
      //   });
    },
    goToLogin() {
      this.$router.replace({ path: "/" });
    }
  }
};
</script>

<style>
.register-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.register_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>
