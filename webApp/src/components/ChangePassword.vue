<template>
  <el-form
    :rules="rules"
    class="change-password-container"
    label-position="left"
    label-width="0px"
    v-loading="loading"
  >
    <h3 class="change_password_title">修改密码</h3>
    <el-form-item prop="username">
      <el-input
        type="text"
        v-model="changePasswordForm.username"
        auto-complete="off"
        placeholder="用户名"
      ></el-input>
    </el-form-item>
    <el-form-item prop="oldPassword">
      <el-input
        type="password"
        v-model="changePasswordForm.oldPassword"
        auto-complete="off"
        placeholder="旧密码"
      ></el-input>
    </el-form-item>
    <el-form-item prop="newPassword">
      <el-input
        type="password"
        v-model="changePasswordForm.newPassword"
        auto-complete="off"
        placeholder="新密码"
      ></el-input>
    </el-form-item>
    <el-form-item style="width: 100%">
      <el-button
        type="primary"
        @click.native.prevent="changePassword"
        style="width: 100%"
        >修改密码</el-button
      >
    </el-form-item>
  </el-form>
</template>

<script>
import axios from "axios";
import sha256 from "crypto-js/sha256";

export default {
  data() {
    return {
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" }
        ],
        oldPassword: [
          { required: true, message: "请输入旧密码", trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: "请输入新密码", trigger: "blur" }
        ]
      },
      changePasswordForm: {
        username: "",
        oldPassword: "",
        newPassword: ""
      },
      loading: false
    };
  },
  methods: {
    changePassword() {
      this.loading = true;
      const hash1 = sha256(
        this.changePasswordForm.username + this.changePasswordForm.oldPassword
      ).toString();
      const newHash1 = sha256(
        this.changePasswordForm.username + this.changePasswordForm.newPassword
      ).toString();
      axios
        .post("http://localhost:8084/user/change-password", {
          username: this.changePasswordForm.username,
          hash1: hash1,
          newHash1: newHash1
        })
        .then(response => {
          this.loading = false;
          if (response.data === "密码修改成功") {
            alert("密码修改成功，请登录");
            this.$router.replace({ path: "/" });
          } else {
            alert("密码修改失败：" + response.data.msg);
          }
        })
        .catch(error => {
          this.loading = false;
          alert("修改密码失败：" + error.response.data.message);
        });
    }
  }
};
</script>

<style>
.change-password-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.change_password_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>
