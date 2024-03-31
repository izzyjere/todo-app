<template>
  <div class="login-container">
    <el-card class="login-card">
      <template v-slot:header>
        <h2>Login</h2>
      </template>
      <el-form ref="loginForm" :model="loginForm" :rules="loginRules" label-width="100px" class="demo-form-stacked">
        <el-form-item label="Username" prop="username">
          <el-input v-model="loginForm.username"></el-input>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input type="password" v-model="loginForm.password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login">Login</el-button>
        </el-form-item>
      </el-form>
      <div class="register-link">
        <span>Don't have an account?</span>
        <router-link to="/register">Register</router-link>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loginRules: {
        username: [{ required: true, message: 'Please enter your username', trigger: 'blur' }],
        password: [{ required: true, message: 'Please enter your password', trigger: 'blur' }]
      }
    };
  },
  methods: {
    login() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          // Send login data to backend
          console.log('Login Successful');
        } else {
          console.log('Invalid Form');
          return false;
        }
      });
    }
  }
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
</style>
