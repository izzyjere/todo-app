<template>
  <div class="register-container">
    <el-card class="register-card">
      <template #header>
        <h2>Register</h2>
      </template>
      <el-form
        ref="registrationForm"
        :model="registrationForm"
        :rules="registrationRules"
        label-width="100px"
        labelPosition="left"
      >
        <el-form-item label="First Name" prop="firstName">
          <el-input
            v-model="registrationForm.firstName"
            size="large"
          ></el-input>
        </el-form-item>
        <el-form-item label="Last Name" prop="lastName">
          <el-input v-model="registrationForm.lastName" size="large"></el-input>
        </el-form-item>
        <el-form-item label="Username" prop="username">
          <el-input v-model="registrationForm.username" size="large"></el-input>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input
            type="password"
            v-model="registrationForm.password"
            size="large"
          ></el-input>
        </el-form-item>
        <el-form-item label="Confirm" prop="confirmPassword">
          <el-input
            type="password"
            v-model="registrationForm.confirmPassword"
            size="large"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="register"
            :loading="loading"
            size="large"
          >
            {{ loading ? "Submitting..." : "Register" }}
          </el-button>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="login-link">
          <router-link to="/login">Already have an account? Login</router-link>
        </div>
        <el-alert v-if="errorMessage" type="error" :closable="false" show-icon>
          {{ errorMessage }}
        </el-alert>
      </template>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      errorMessage: "",
      loading: false,
      registrationForm: {
        firstName: "",
        lastName: "",
        username: "",
        password: "",
        confirmPassword: "",
      },
      registrationRules: {
        firstName: [
          {
            required: true,
            message: "Please enter your first name",
            trigger: "blur",
          },
        ],
        lastName: [
          {
            required: true,
            message: "Please enter your last name",
            trigger: "blur",
          },
        ],
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
        confirmPassword: [
          {
            required: true,
            message: "Please confirm your password",
            trigger: "blur",
          },
          { validator: this.validatePasswordConfirm, trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    register() {
      this.$refs.registrationForm.validate((valid) => {
        if (valid) {
          // Send registration data to backend
          this.loading = true;
          axios
            .post("auth/signup", this.registrationForm)
            .then((response) => {
              if (response.data.succeeded) {
                console.log(
                  "User registered successfully:",
                  response.data.message
                );
                this.$router.push("/login");
              } else {
                this.errorMessage = response.data.message;
                console.error(
                  "Failed to register user:",
                  response.data.message
                );
              }
            })
            .catch((error) => {
              console.error("An error occurred during registration:", error);
              this.errorMessage = "An error occurred. Please try again later.";
            })
            .finally(() => {
              this.loading = false;
            });
        } else {
          console.log("Invalid Form");
          return false;
        }
      });
    },
    validatePasswordConfirm(rule, value, callback) {
      if (value === "") {
        callback(new Error("Please confirm your password"));
      } else if (value !== this.registrationForm.password) {
        callback(new Error("Passwords do not match"));
      } else {
        callback();
      }
    },
  },
};
</script>

<style scoped>
.login-link {
  margin-top: 20px;
  margin-bottom: 20px;
  text-align: center;
}

.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.register-card {
  width: 500px;
}

.register-card h2 {
  font-family: Arial, sans-serif;
  text-align: center;
}
.el-form-item__content {
  font-size: 16px;
}
</style>
