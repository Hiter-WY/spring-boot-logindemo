<template>
  <el-form
    :rules="rules"
    class="login-container"
    label-position="left"
    label-width="0px"
    v-loading="loading"
  >
    <h3 class="login_title">系统登录</h3>
    <el-form-item prop="username">
      <el-input
        type="text"
        v-model="loginForm.username"
        auto-complete="off"
        placeholder="账号"
      ></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input
        type="password"
        v-model="loginForm.password"
        auto-complete="off"
        placeholder="密码"
      ></el-input>
    </el-form-item>
    <el-checkbox class="login_remember" v-model="checked" label-position="left"
      >记住密码</el-checkbox
    >
    <el-form-item style="width: 100%">
      <el-button
        type="primary"
        @click.native.prevent="login"
        style="width: 100%"
        >登录</el-button
      >
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button type="text" @click="goToRegister" style="width: 100%"
        >没有账号？注册</el-button
      >
    </el-form-item>
  </el-form>
</template>

<script>
import axios from "axios";
import CryptoJS from "crypto-js";

export default {
  data() {
    return {
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" }
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }]
      },
      checked: true,
      loginForm: {
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
    decryptAES(encryptedData, key) {
      const keyHash = CryptoJS.SHA256(key)
        .toString(CryptoJS.enc.Hex)
        .substring(0, 32);
      const decrypted = CryptoJS.AES.decrypt(
        encryptedData,
        CryptoJS.enc.Hex.parse(keyHash),
        {
          mode: CryptoJS.mode.ECB,
          padding: CryptoJS.pad.Pkcs7
        }
      );
      return CryptoJS.enc.Utf8.stringify(decrypted);
    },
    login() {
      this.loading = true;
      const verificationCode = this.generateVerificationCode();
      const hash1 = CryptoJS.SHA256(
        this.loginForm.username + this.loginForm.password
      ).toString();
      const hash2 = CryptoJS.SHA256(hash1 + verificationCode).toString();
      console.log(`hash1: ${hash1}`);
      console.log(`hash2: ${hash2}`);
      axios
        .post("http://localhost:8084/user/login", {
          username: this.loginForm.username,
          hash2: hash2,
          verificationCode: verificationCode
        })
        .then(response => {
          this.loading = false;
          if (response.data.message === "登录成功") {
            alert("登录成功");
            const encryptedCode = response.data.encryptedCode;
            localStorage.setItem("Encrypted Verification Code", encryptedCode); // 存储加密后的代码

            // 解密encryptedCode
            const decryptedCode = this.decryptAES(encryptedCode, hash1);
            // console.log("Decrypted Verification Code:", decryptedCode);
            localStorage.setItem("Decrypted Verification Code", decryptedCode); // 存储解密后的代码
            this.$router.replace({ path: "/HelloWorld" });
          } else {
            alert("登录失败：" + response.data);
          }
        })
        .catch(() => {
          this.loading = false;
          alert("服务器错误");
        });
    },
    goToRegister() {
      this.$router.replace({ path: "/register" });
    }
  }
};
</script>

<style>
.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}

.login_remember {
  margin: 0px 0px 35px 0px;
  text-align: left;
}
</style>
