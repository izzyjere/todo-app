<template>
  <div class="register-container">
    <el-card class="register-card">
      <template v-slot:header>
        <h2>Register</h2>
      </template>
      <el-form
        ref="registrationForm"
        :model="registrationForm"
        :rules="registrationRules"
        label-width="100px">
        <el-form-item
          label="First Name"
          prop="firstName"
          class="form-item-label-top"
        >
          <el-input
            v-model="registrationForm.firstName"
            size="large"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="Last Name"
          prop="lastName"
          class="form-item-label-top"
        >
          <el-input v-model="registrationForm.lastName" size="large"></el-input>
        </el-form-item>
        <el-form-item
          label="Username"
          prop="username"
          class="form-item-label-top"
        >
          <el-input v-model="registrationForm.username" size="large"></el-input>
        </el-form-item>
        <el-form-item
          label="Password"
          prop="password"
          class="form-item-label-top"
        >
          <el-input
            type="password"
            v-model="registrationForm.password"
            size="large"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="Confirm Password"
          prop="confirmPassword"
          class="form-item-label-top"
        >
          <el-input
            type="password"
            v-model="registrationForm.confirmPassword"
            size="large"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="register" size="large"
            >Register</el-button
          >
        </el-form-item>
      </el-form>
      <div class="login-link">
        <router-link to="/login">Already have an account? Login</router-link>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
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
          console.log("Registration Successful");
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
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.register-card {
  width: 500px; /* Adjust the width as needed */
}

.register-card h2 {
  font-family: Arial, sans-serif;
  text-align: center;
}

.form-item-label-top .el-form-item__label {
  margin-bottom: 10px; /* Adjust the spacing between the label and input */
}

.form-item-label-top .el-form-item__content {
  margin-top: 0; /* Remove default margin */
}

.el-form-item__content {
  font-size: 16px; /* Adjust the font size as needed */
}
</style>
