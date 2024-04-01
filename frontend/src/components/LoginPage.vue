<template>
  <div class="login-container">
    <el-card class="login-card">
      <template v-slot:header>
        <h2>Login</h2>
      </template>
      <el-form
        ref="loginForm"
        :model="loginForm"
        :rules="loginRules"
        label-width="100px"
        class="demo-form-stacked"
      >
        <el-form-item label="Username" prop="username">
          <el-input v-model="loginForm.username"></el-input>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input type="password" show-password v-model="loginForm.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" :loading="loading"
            >Login</el-button
          >
        </el-form-item>
      </el-form>
      <div class="error-alert" v-if="errorMessage">
        <el-alert type="error" show-icon :closable="false">
          {{ errorMessage }}
        </el-alert>
      </div>
      <div class="register-link">
        <span>Don't have an account?</span>
        <router-link to="/register">Register</router-link>
      </div>
    </el-card>
  </div>
</template>
<script>
import { mapActions } from "vuex";
export default {
  data() {
    return {
      loginForm: {
        username: "",
        password: "",
      },
      loginRules: {
        username: [
          {
            required: true,
            message: "Please enter your username",
            trigger: "blur",
          },
        ],
        password: [
          {
            required: true,
            message: "Please enter your password",
            trigger: "blur",
          },
        ],
      },
      loading: false,
      errorMessage: "",
    };
  },
  methods: {
    ...mapActions("auth", ["login"]),
    async handleLogin() {
      try {
        this.errorMessage = "";
        this.$refs.loginForm.validate(async (valid) => {
          if (valid) {
            this.loading = true; // Set loading state to true
            // Dispatch login action from Vuex store
            this.login({
              username: this.loginForm.username,
              password: this.loginForm.password,
            })
              .then(() => {
                // Redirect to home page after successful login
                this.$router.push("/todos");
              })
              .catch((error) => {
                this.errorMessage = error.message ?? "Invalid login attempt.";
                console.log(error);
              })
              .finally(() => {
                this.loading = false; // Set loading state to false
              });
          } else {
            console.log("Invalid Form");
          }
        });
      } catch (error) {
        this.loading = false;
        console.error("An error occurred during login:", error);
        this.errorMessage = "An error occurred during login.";
      }
    },
  },
  created() {
    // Check if there is a valid authentication token in local storage
    const token = localStorage.getItem("token");
    if (token) {
      // Retrieve user data associated with the token
      const user = JSON.parse(localStorage.getItem("user"));
      // Set authentication state in Vuex store
      this.$store.commit("auth/SET_TOKEN", token);
      this.$store.commit("auth/SET_USER", user);
    }
  },
};
</script>


<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.login-card {
  width: 400px;
}

.login-card h2 {
  text-align: center;
}

.register-link {
  margin-top: 20px;
  text-align: center;
}

.register-link span {
  margin-right: 5px;
}

.error-alert {
  margin-top: 20px;
}
</style>
